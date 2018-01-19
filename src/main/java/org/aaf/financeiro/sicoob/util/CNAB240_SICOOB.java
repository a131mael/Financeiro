package org.aaf.financeiro.sicoob.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.aaf.financeiro.util.CNAB240;
import org.aaf.financeiro.util.OfficeUtil;
import org.aaf.financeiro.util.constantes.CNAB240_SICOOB_REMESSA_CONSTANTS_TEFAMEL;
import org.aaf.financeiro.util.constantes.CNB240_SICOOB_CONSTANTS_ADONAI;
import org.aaf.financeiro.util.constantes.CNB240_SICOOB_CONSTANTS_TEFAMEL;
import org.aaf.financeiro.util.constantes.Constante;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.gerador.GeradorDeDigito;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;

public class CNAB240_SICOOB {

	private Constante constant = null;

	public CNAB240_SICOOB(int constante) {
		switch (constante) {
		case 1: //TEFAMEL
			constant =  new Constante(new CNB240_SICOOB_CONSTANTS_TEFAMEL());
			break;

		case 2: //ADONAI
			constant =  new Constante(new CNB240_SICOOB_CONSTANTS_ADONAI());
			break;

		default:
			break;
		}

	}

	public byte[] getBoletoPDF(String numeroDoBoleto, String valor, String nomePagador, String ruaPagador,
			String cepPagador, String cidadePagador, String bairroPagador, String ufPagador, String cpfPagador,
			Date dataVencimento) {
		Datas datas = getDatasStella(dataVencimento);
		Endereco enderecoPagador = getEnderecoStella(ruaPagador, bairroPagador, cepPagador, cidadePagador, ufPagador);
		Endereco enderecoBeneficiario = getEnderecoStella(constant.RUA, constant.BAIRRO,constant.CEP, constant.CIDADE,constant.UF);
		Beneficiario beneficiario = getBeneficiarioStella(constant.NOME,constant.getCOD_AGENCIA(), "", constant.getCOD_BENEFICIARIO(),
				constant.getCOD_BENEFICIARIO(), constant.getCOD_CARTEIRA(),constant.CNPJ, enderecoBeneficiario, numeroDoBoleto);
		Pagador pagador = getPagadorStella(nomePagador, cpfPagador, enderecoPagador);
		Banco banco = getBancoSicoobStella();
		Boleto boleto = getBoletoStella(banco, datas, beneficiario, pagador, valor, numeroDoBoleto);

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
	public Beneficiario getBeneficiarioStella(String nome, String agencia, String digitoAgencia,
			String codigoBeneficiario, String digitoCodBeneficiario, String numConvenio, String carteira,
			Endereco endereco, String nossoNumero) {
		Beneficiario beneficiario = Beneficiario.novoBeneficiario().comNomeBeneficiario(nome).comAgencia(agencia)
				.comDigitoAgencia(agencia).comCodigoBeneficiario(codigoBeneficiario)
				.comDigitoCodigoBeneficiario(digitoCodBeneficiario).comNumeroConvenio(numConvenio).comCarteira(carteira)
				.comEndereco(endereco).comNossoNumero(nossoNumero)
				.comDocumento(constant.CNPJ);

		return beneficiario;
	}

	// Quem paga o boleto
	public Pagador getPagadorStella(String nome, String cpf, Endereco endereco) {
		Pagador pagador = Pagador.novoPagador().comNome(nome).comDocumento(cpf).comEndereco(endereco);
		return pagador;
	}

	public Banco getBancoSicoobStella() {
		Banco sicoob = new Banco() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1089834882486269286L;

			public String getNumeroFormatadoComDigito() {
				return constant.getCOD_BANCO();
			}

			public String getNumeroFormatado() {

				return constant.getCOD_BANCO();
			}

			public String getNossoNumeroFormatado(Beneficiario beneficiario) {

				return beneficiario.getNossoNumero();
			}

			public String getNossoNumeroECodigoDocumento(Boleto boleto) {

				return boleto.getBeneficiario().getNossoNumero() + "-"
						+ CNAB240.verificadorNossoNumero(boleto.getBeneficiario().getNossoNumero()) ;
			}

			public URL getImage() {
				URL imgSICOOB = null;

				imgSICOOB = CNAB240_SICOOB.class.getResource("/marca_sicoob.png");
				return imgSICOOB;
			}

			public GeradorDeDigito getGeradorDeDigito() {

				return getGeradorDeDigitoSicoobStella();
			}

			public String getCodigoBeneficiarioFormatado(Beneficiario beneficiario) {

				return constant.getCOD_BENEFICIARIO();
			}

			public String getCarteiraFormatado(Beneficiario beneficiario) {

				return constant.getCOD_CARTEIRA();
			}

			public String getAgenciaECodigoBeneficiario(Beneficiario beneficiario) {

				return constant.getCOD_AGENCIA() + "/"
						+ constant.getCOD_BENEFICIARIO();
			}

			public String geraCodigoDeBarrasPara(Boleto boleto) {

				return CNAB240.sequenciaCodigoBarras(boleto.getNumeroDoDocumento(),
						boleto.getDatas().getVencimento().getTime(), boleto.getValorFormatado());
			}
		};
		return sicoob;
	}

	public GeradorDeDigito getGeradorDeDigitoSicoobStella() {
		GeradorDeDigito g = new GeradorDeDigito() {

			public int geraDigitoModAceitandoRestoZero(String codigoDeBarras, int inicio, int fim, int numMod) {
				return CNAB240.modulo11(codigoDeBarras);
			}

			public int geraDigitoMod11AceitandoRestoZero(String codigoDeBarras) {
				return CNAB240.modulo11(codigoDeBarras);
			}

			public int geraDigitoMod11(String codigoDeBarras) {
				return CNAB240.modulo11(codigoDeBarras);
			}

			public int geraDigitoMod10(String campo) {
				return CNAB240.modulo10(campo);
			}

			public int geraDigitoMod(String codigoDeBarras, int inicio, int fim, int numMOD) {
				return CNAB240.modulo11(codigoDeBarras);
			}

			public int geraDigitoBloco3(String bloco) {
				return CNAB240.modulo10(bloco);
			}

			public int geraDigitoBloco2(String bloco) {
				return CNAB240.modulo10(bloco);
			}

			public int geraDigitoBloco1(String bloco) {

				return CNAB240.modulo10(bloco);
			}
		};
		return g;
	}

	public Boleto getBoletoStella(Banco banco, Datas datas, Beneficiario beneficiario, Pagador pagador, String valor, String numeroBoleto) {

		Boleto boleto = Boleto.novoBoleto().comBanco(banco).comDatas(datas).comBeneficiario(beneficiario)
				.comPagador(pagador).comValorBoleto(valor).comNumeroDoDocumento(numeroBoleto).comEspecieDocumento("ME")
				.comLocaisDePagamento("PAGAVEL EM QUALQUER BANCO ATE VENCIMENTO", "local 2");

		return boleto;
	}
	
	public Boleto getBoletoStella(Banco banco, Datas datas, Beneficiario beneficiario, Pagador pagador, String valor, String numeroBoleto, String numeroDocumento) {

		Boleto boleto = Boleto.novoBoleto()
						.comBanco(banco)
						.comDatas(datas)
						.comBeneficiario(beneficiario)
						.comPagador(pagador)
						.comValorBoleto(valor)
						.comNumeroDoDocumento(numeroBoleto)
						.comEspecieDocumento("ME")
						.comLocaisDePagamento("PAGAVEL EM QUALQUER BANCO ATE VENCIMENTO", "local 2");

		return boleto;
	}

	public byte[] getBoletoPDF(org.aaf.financeiro.model.Pagador pagador) {

		List<Boleto> boletos = new ArrayList<Boleto>();
		Endereco enderecoBeneficiario = getEnderecoStella(constant.RUA,	constant.BAIRRO, constant.CEP,constant.CIDADE, constant.UF);
		String instrucao1 = "Desconto de R$"
				+ OfficeUtil.retornarComVirgula(CNAB240_SICOOB_REMESSA_CONSTANTS_TEFAMEL.VALORDESCONTO)
				+ " ate o vencimento.";
		String instrucao2 = "Multa de "
				+ OfficeUtil.retornarComVirgula(CNAB240_SICOOB_REMESSA_CONSTANTS_TEFAMEL.VALORMULTA)
				+ "% apos o vencimento.";
		String instrucao3 = "Juros de R$"
				+ OfficeUtil.retornarComVirgula(CNAB240_SICOOB_REMESSA_CONSTANTS_TEFAMEL.VALORJUROSAODIA)
				+ "  ao dia, apos o vencimento.";
		/* String instrucao4 = "Protestar 40 dias após o vencimento."; */

		for (org.aaf.financeiro.model.Boleto boletoModel : pagador.getBoletos()) {
			Datas datas = getDatasStella(boletoModel.getVencimento());
			Endereco enderecoPagador = getEnderecoStella(pagador.getEndereco(), pagador.getBairro(), pagador.getCep(),
					pagador.getCidade(), pagador.getUF());

			Beneficiario beneficiario = getBeneficiarioStella(constant.NOME,
					constant.getCOD_AGENCIA(), "", constant.getCOD_BENEFICIARIO(),
					constant.getCOD_BENEFICIARIO(), constant.getCOD_CARTEIRA(),
					constant.CNPJ, enderecoBeneficiario,
					boletoModel.getNossoNumero() + "");

			Pagador pagadorStella = getPagadorStella(pagador.getNome(), pagador.getCpfCNPJ(), enderecoPagador);

			Banco banco = getBancoSicoobStella();
//TODO xx certo
			Boleto boleto = getBoletoStella(banco, datas, beneficiario, pagadorStella,
					boletoModel.getValorNominal() + "", boletoModel.getNossoNumero() + "");

			boleto.comInstrucoes(instrucao1, instrucao2, instrucao3);
			boletos.add(boleto);
		}

		GeradorDeBoleto gerador = new GeradorDeBoleto(boletos);
		return gerador.geraPDF();

	}

}
