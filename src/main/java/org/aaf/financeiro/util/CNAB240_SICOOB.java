package org.aaf.financeiro.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.gerador.GeradorDeDigito;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;

public class CNAB240_SICOOB {

	public static void main(String[] args) {
	        
	        /*Boleto[] boletos = {boletoDeJaneiro,boletoDeFevereiro,boletoDeMarco};  
	        GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);
	        gerador.geraPDF("boletos.pdf"); */

	}
	
	public byte[] getBoletoPDF(String numeroDoBoleto,String valor,String nomePagador,String ruaPagador,String cepPagador,String cidadePagador, String bairroPagador,String ufPagador,String cpfPagador,Date dataVencimento){
		Datas datas = getDatasStella(dataVencimento);
		Endereco enderecoPagador = getEnderecoStella(ruaPagador, bairroPagador, cepPagador, cidadePagador, ufPagador);
		Endereco enderecoBeneficiario = getEnderecoStella(CNB240_SICOOB_CONSTANTS.RUA_TEFAMEL, CNB240_SICOOB_CONSTANTS.BAIRRO_TEFAMEL, CNB240_SICOOB_CONSTANTS.CEP_TEFAMEL, CNB240_SICOOB_CONSTANTS.CIDADE_TEFAMEL, CNB240_SICOOB_CONSTANTS.UF_TEFAMEL);
		Beneficiario beneficiario = getBeneficiarioStella(CNB240_SICOOB_CONSTANTS.NOME_TEFAMEL, CNB240_SICOOB_CONSTANTS.COD_AGENCIA, "", CNB240_SICOOB_CONSTANTS.COD_BENEFICIARIO, CNB240_SICOOB_CONSTANTS.COD_BENEFICIARIO, CNB240_SICOOB_CONSTANTS.COD_CARTEIRA, CNB240_SICOOB_CONSTANTS.CNPJ_TEFAMEL, enderecoBeneficiario, numeroDoBoleto);
		Pagador pagador = getPagadorStella(nomePagador, cpfPagador, enderecoPagador);
		Banco banco = getBancoSicoobStella();
		Boleto boleto = getBoletoStella(banco, datas, beneficiario, pagador,valor,numeroDoBoleto );
		boleto.comEspecieDocumento("ME");
		
		GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);
        return gerador.geraPDF();
	}

	public Datas getDatasStella(Date vencimento) {
		Calendar dtCriacao = Calendar.getInstance();
		Calendar dtVencimento = Calendar.getInstance();
		dtVencimento.setTime(vencimento);
		Datas datas = Datas.novasDatas().comDocumento(dtCriacao).comProcessamento(dtCriacao)
				.comVencimento(dtVencimento);

		return datas;
	}

	public Endereco getEnderecoStella(String Logradouro, String Bairro, String cep, String cidade, String uf) {
		Endereco enderecoBeneficiario = Endereco.novoEndereco().comLogradouro(Logradouro).comBairro(Bairro).comCep(cep)
				.comCidade(cidade).comUf(uf);

		return enderecoBeneficiario;
	}

	// Quem emite o boleto
	public Beneficiario getBeneficiarioStella(String nome, String agencia,String digitoAgencia,String codigoBeneficiario,String digitoCodBeneficiario,String numConvenio,String carteira,Endereco endereco, String nossoNumero) {
		Beneficiario beneficiario = Beneficiario.novoBeneficiario().comNomeBeneficiario(nome)
				.comAgencia(agencia).comDigitoAgencia(agencia).comCodigoBeneficiario(codigoBeneficiario)
				.comDigitoCodigoBeneficiario(digitoCodBeneficiario).comNumeroConvenio(numConvenio).comCarteira(carteira)
				.comEndereco(endereco).comNossoNumero(nossoNumero);

		return beneficiario;
	}

	//Quem paga o boleto
	public Pagador getPagadorStella(String nome,String cpf,Endereco endereco){
        Pagador pagador = Pagador.novoPagador()  
                .comNome(nome)  
                .comDocumento(cpf)
                .comEndereco(endereco);
        return pagador;
	}

	public Banco getBancoSicoobStella(){
		Banco sicoob = new Banco() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1089834882486269286L;

			public String getNumeroFormatadoComDigito() {
				return CNB240_SICOOB_CONSTANTS.COD_BANCO;
			}
			
			public String getNumeroFormatado() {
				
				return CNB240_SICOOB_CONSTANTS.COD_BANCO;
			}
			
			public String getNossoNumeroFormatado(Beneficiario beneficiario) {
				
				return CNAB240.completaNossoNumero(beneficiario.getNossoNumero());
			}
			
			public String getNossoNumeroECodigoDocumento(Boleto boleto) {
				
				return CNAB240.completaNossoNumero(boleto.getBeneficiario().getNossoNumero())+"-"+CNAB240.verificadorNossoNumero(CNAB240.completaNossoNumero(boleto.getBeneficiario().getNossoNumero()));
			}
			
			public URL getImage() {
				URL imgSICOOB = null;
			
				try {
					imgSICOOB = new URL("/marca_sicoob.png");
					//imgSICOOB = new URL("/resources/marca_sicoob.png");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return imgSICOOB;
			}
			
			public GeradorDeDigito getGeradorDeDigito() {
				
				return getGeradorDeDigitoSicoobStella();
			}
			
			public String getCodigoBeneficiarioFormatado(Beneficiario beneficiario) {
				
				return CNB240_SICOOB_CONSTANTS.COD_BENEFICIARIO;
			}
			
			public String getCarteiraFormatado(Beneficiario beneficiario) {
				
				return CNB240_SICOOB_CONSTANTS.COD_CARTEIRA;
			}
			
			public String getAgenciaECodigoBeneficiario(Beneficiario beneficiario) {
				
				return CNB240_SICOOB_CONSTANTS.COD_AGENCIA+"/"+CNB240_SICOOB_CONSTANTS.COD_BENEFICIARIO;
			}
			
			public String geraCodigoDeBarrasPara(Boleto boleto) {
				
				return CNAB240.sequenciaCodigoBarras(boleto.getNumeroDoDocumento(), boleto.getDatas().getDocumento().getTime(), boleto.getValorFormatado());
			}
		};
		return sicoob;
	}
	
	public GeradorDeDigito getGeradorDeDigitoSicoobStella(){
		GeradorDeDigito g = new GeradorDeDigito() {
			
			public int geraDigitoModAceitandoRestoZero(String codigoDeBarras, int inicio, int fim, int numMod) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int geraDigitoMod11AceitandoRestoZero(String codigoDeBarras) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int geraDigitoMod11(String codigoDeBarras) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int geraDigitoMod10(String campo) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int geraDigitoMod(String codigoDeBarras, int inicio, int fim, int numMOD) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int geraDigitoBloco3(String bloco) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int geraDigitoBloco2(String bloco) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int geraDigitoBloco1(String bloco) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
		return g;
	}
	
	
	public Boleto getBoletoStella(Banco banco,Datas datas,Beneficiario beneficiario,Pagador pagador, String valor,String numeroBoleto){
		
		 Boleto boleto = Boleto.novoBoleto()  
	                .comBanco(banco)  
	                .comDatas(datas)  
	                .comBeneficiario(beneficiario)  
	                .comPagador(pagador)  
	                .comValorBoleto(valor)  
	                .comNumeroDoDocumento(numeroBoleto)  
	                .comInstrucoes("instrucao 1","instrução 2")  
	                .comLocaisDePagamento("PAGAVEL EM QUALQUER BANCO ATE VENCIMENTO", "local 2");
		 
		 return boleto;
	}
	
	
}
