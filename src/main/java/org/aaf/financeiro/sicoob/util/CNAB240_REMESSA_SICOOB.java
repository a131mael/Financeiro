package org.aaf.financeiro.sicoob.util;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.aaf.financeiro.model.Boleto;
import org.aaf.financeiro.model.Pagador;
import org.aaf.financeiro.util.CNAB240;
import org.aaf.financeiro.util.OfficeUtil;
import org.aaf.financeiro.util.constantes.CNAB240_SICOOB_REMESSA_CONSTANTS_ADONAI;
import org.aaf.financeiro.util.constantes.CNAB240_SICOOB_REMESSA_CONSTANTS_TEFAMEL;
import org.aaf.financeiro.util.constantes.ConstanteRemessa;


public class CNAB240_REMESSA_SICOOB {
	
	static ConstanteRemessa constant = null;
	static CNAB240 cnab240 = null;
	public CNAB240_REMESSA_SICOOB(int constante) {
		switch (constante) {
		case 1: //TEFAMEL
			constant =  new ConstanteRemessa(new CNAB240_SICOOB_REMESSA_CONSTANTS_TEFAMEL());
			break;

		case 2: //ADONAI
			constant =  new ConstanteRemessa(new CNAB240_SICOOB_REMESSA_CONSTANTS_ADONAI());
			break;

		default:
			break;
		}
		cnab240 = new CNAB240(constante);
	}
	
	
	public static byte[] geraRemessa(Pagador pagador,String sequencialArquivo){
		return geraRemessa(pagador,sequencialArquivo,false);
	}
	
	public static byte[] geraRemessa(Pagador pagador,String sequencialArquivo, boolean baixa){
		Date dataGeracaoRemessa = new Date();
		int sequencialSeguimento = 0;
		double valorTotal = 0;
		StringBuilder cnab = new StringBuilder();
		cnab.append(header(dataGeracaoRemessa, sequencialArquivo));
		cnab.append(OfficeUtil.quebraLinhaTXT);
		cnab.append(headerLote(1,1,dataGeracaoRemessa));//todo segundo parametro tem que incrementar caso o arquivo seja reenviado
		
		for(Boleto boleto : pagador.getBoletos()){
			valorTotal += boleto.getValorNominal();
			sequencialSeguimento++;
			cnab.append(OfficeUtil.quebraLinhaTXT);
			cnab.append(seguimentoP(1, sequencialSeguimento, montarNossoNumero(boleto.getNossoNumero()+"",cnab240).toString(), String.valueOf(boleto.getId()), boleto.getVencimento(), boleto.getValorNominal(), boleto.getEmissao(),  String.valueOf(boleto.getId()),baixa));		
			sequencialSeguimento++;
			cnab.append(OfficeUtil.quebraLinhaTXT);
			cnab.append(seguimentoQ(1, sequencialSeguimento, pagador.getCpfCNPJ().replace(".", "").replace("-", "").trim(), pagador.getNome(), pagador.getEndereco(), pagador.getBairro(), pagador.getCep(), pagador.getCidade(), pagador.getUF()));			
			sequencialSeguimento++;
			cnab.append(OfficeUtil.quebraLinhaTXT);
			cnab.append(seguimentoR(1, sequencialSeguimento, boleto.getVencimento()));
			sequencialSeguimento++;
			cnab.append(OfficeUtil.quebraLinhaTXT);	
			cnab.append(seguimentoS(1, sequencialSeguimento));
		}
		cnab.append(OfficeUtil.quebraLinhaTXT);
		cnab.append(traillerLote(1, (2+sequencialSeguimento), sequencialSeguimento/4, valorTotal, 0, 0, 0, 0));
		cnab.append(OfficeUtil.quebraLinhaTXT);
		cnab.append(traillerArquivo(1, sequencialSeguimento/4));
		
		String temp = System.getProperty("java.io.tmpdir");
		String caminho = temp + File.separator +pagador.getNossoNumero()+ ".txt";
		//String caminho2 =  "C:\\Users\\Abimael Fidencio\\Desktop\\cnb240.txt";
		OfficeUtil.criarTXT(caminho, cnab.toString());
		
		return OfficeUtil.pathToByteArray(caminho);
	}
	
	
	public static StringBuilder montarNossoNumero(String numeroTitulo, CNAB240 cnab240){
		StringBuilder sb = new StringBuilder();
		sb.append(OfficeUtil.preencherZeroEsquerda(numeroTitulo, 8)); 
		sb.append(cnab240.verificadorNossoNumero(numeroTitulo)); 
		sb.append("01"); //Parcela 01 = parcela unica
		sb.append("01"); //MODALIDADE - 01 = simples com registro
		sb.append("4"); //Tipo de formulario 4 = A$ sem envelopamento
		sb.append(OfficeUtil.espacos(5));
		return sb;
	}
	
	public static String gerarRemessaCNAB_SICCOOB(){
		return "";
	}
	public static StringBuilder header(Date dataGeracaoArquivo,String sequencial){
		StringBuilder header = new StringBuilder();
		header.append(constant.COD_BANCO);// 01 ao 03
		header.append(constant.LOTE_SERVICO);// 04 ao 07
		header.append(constant.TP_REGISTRO); // 08 a 08
		header.append(OfficeUtil.espacos(9)); //CNAB FEBRABAM 09 ao 17
		header.append(constant.TP_EMPRESA);
		header.append(OfficeUtil.preencherZeroEsquerda(constant.NUM_INSCRICAO_EMPRESA,14)); //NUmero inscricao empresa
		header.append(OfficeUtil.espacos(20)); //Codigo do convenio
		header.append(OfficeUtil.preencherZeroEsquerda(constant.COD_COOPERATIVA,5));//PREFIO DA COOPERATIVA
		header.append(constant.VER_COD_COOPERATIVA);//DIGITO VERIFICADOR DO PREFIXO
		header.append(OfficeUtil.preencherZeroEsquerda(constant.COD_CONTA_CORRENTE, 12));
		header.append(constant.VER_CONTA_CORRENTE);
		header.append(OfficeUtil.preencherEspacosEsquerda("", 1)); //DIGITO VERIFICADOR CONTA CORRENTE
		header.append(OfficeUtil.limiteMaximo(OfficeUtil.preencherEspacosDireita(constant.NOME_EMPRESA, 29), 29));
		header.append(OfficeUtil.preencherEspacosDireita(constant.NOME_BANCO, 30));
		header.append(OfficeUtil.espacos(10)); //CNAB FEBRABAM
		header.append(constant.COD_REMESSA__RETORNO);
		header.append(OfficeUtil.retornaDataSomenteNumeros(dataGeracaoArquivo));
		header.append(OfficeUtil.retornaHoraSomenteNumeros(dataGeracaoArquivo));
		header.append(OfficeUtil.preencherZeroEsquerda(sequencial, 6));
		header.append(constant.NUM_VERSAO_LAYOUT);
		header.append(constant.DENSIDADE_GRAVAO_ARQUIVO);
		header.append(OfficeUtil.espacos(20));
		header.append(OfficeUtil.espacos(20));
		header.append(OfficeUtil.espacos(29));
		return header;
	}
	
	public static StringBuilder headerLote(int sequencial, int sequencialRemessaRetorno, Date dataGravavao){
		StringBuilder header = new StringBuilder();
		
		header.append(constant.COD_BANCO);// 01 ao 03
		header.append(OfficeUtil.preencherZeroEsquerda(sequencial+"", 4));// 01 ao 03
		header.append(constant.TP_REGISTRO_HEADER_LOTE);// 01 ao 03
		header.append(constant.TP_OPERACAO_HEADER_LOTE);// 01 ao 03
		header.append(constant.TP_SERVICO_HEADER_LOTE);// 01 ao 03
		header.append(OfficeUtil.espacos(2));// 01 ao 03
		header.append(constant.LAYOUT_LOTE);// 01 ao 03
		header.append(OfficeUtil.espacos(1));// 01 ao 03
		header.append(constant.TP_EMPRESA);// 01 ao 03
		header.append(OfficeUtil.preencherZeroEsquerda(constant.NUM_INSCRICAO_EMPRESA,15));
		header.append(OfficeUtil.espacos(20));
		header.append(OfficeUtil.preencherZeroEsquerda(constant.COD_COOPERATIVA,5));//PREFIO DA COOPERATIVA
		header.append(constant.VER_COD_COOPERATIVA);//DIGITO VERIFICADOR DO PREFIXO
		header.append(OfficeUtil.preencherZeroEsquerda(constant.COD_CONTA_CORRENTE, 12));
		header.append(constant.VER_CONTA_CORRENTE);
		header.append(OfficeUtil.preencherEspacosEsquerda("", 1)); //DIGITO VERIFICADOR CONTA CORRENTE
		header.append(OfficeUtil.limiteMaximo(OfficeUtil.preencherEspacosDireita(constant.NOME_EMPRESA, 30), 29));
		header.append(OfficeUtil.espacos(40));// 01 ao 03 //TODO AQUI VAI O CONVENIO QUE PERGUNTEI
		header.append(OfficeUtil.espacos(40));// 01 ao 03 //TODO AQUI VAI O CONVENIO QUE PERGUNTEI
		header.append(OfficeUtil.preencherZeroEsquerda(sequencialRemessaRetorno+"", 8));// 01 ao 03 //TODO AQUI VAI O CONVENIO QUE PERGUNTEI
		header.append(OfficeUtil.retornaDataSomenteNumeros(dataGravavao));// 01 ao 03 //TODO AQUI VAI O CONVENIO QUE PERGUNTEI
		header.append(OfficeUtil.preencherZeroEsquerda("", 8));
		header.append(OfficeUtil.espacos(33));
		
		return header;
	}
	
	//TODO numeroDOcumento uso interno para identificar o titulo
		//TODO identificaca do titulo da empresa
		public static StringBuilder seguimentoP(int sequencialInternaLote, int sequencialregistro,String nossoNumero, 
												String numeroDocumento, Date dataVencimento,double valorTitulo, Date dataEmissao,
												String identificacaoTitulo){

			return seguimentoP(sequencialInternaLote, sequencialregistro, nossoNumero, numeroDocumento, dataVencimento, valorTitulo, dataEmissao, identificacaoTitulo,false);
		}
	
	//TODO numeroDOcumento uso interno para identificar o titulo
	//TODO identificaca do titulo da empresa
	public static StringBuilder seguimentoP(int sequencialInternaLote, int sequencialregistro,String nossoNumero, 
											String numeroDocumento, Date dataVencimento,double valorTitulo, Date dataEmissao,
											String identificacaoTitulo, boolean baixa){
		StringBuilder header = new StringBuilder();
		header.append(constant.COD_BANCO);// 01 ao 03
		
		header.append(OfficeUtil.preencherZeroEsquerda(sequencialInternaLote+"", 4));// 01 ao 03
		
		header.append(constant.TP_REGISTRO_SEGUIMENTO_P);// 01 ao 03
		
		header.append(OfficeUtil.preencherZeroEsquerda(sequencialregistro+"", 5));// 01 ao 03
		
		header.append(constant.SEGUIMENTO_P);// 01 ao 03
		
		header.append(OfficeUtil.espacos(1));
		
		if(baixa){
			header.append(constant.MOVIMENTO_REMESSA_BAIXA_TITULO);
			
		}else{
			header.append(constant.MOVIMENTO_REMESSA_ENTRADA_TITULO);
		}
		
		header.append(OfficeUtil.preencherZeroEsquerda(constant.COD_COOPERATIVA,5));//PREFIO DA COOPERATIVA
		header.append(constant.VER_COD_COOPERATIVA);//DIGITO VERIFICADOR DO PREFIXO
		header.append(OfficeUtil.preencherZeroEsquerda(constant.COD_CONTA_CORRENTE, 12));
		header.append(constant.VER_CONTA_CORRENTE);
		header.append(OfficeUtil.espacos(1));
		header.append(OfficeUtil.preencherZeroEsquerda(nossoNumero, 20));
		header.append(constant.COD_CARTEIRA);
		header.append(OfficeUtil.preencherZeroEsquerda("", 1));
		header.append(OfficeUtil.preencherEspacosDireita("", 1));
		header.append("2"); //EMISSAO DO BOLETO INTERNAMENTE
		header.append("2"); //DISTRIBUICAO DO BOLETO INTERNAMENTE
		header.append(OfficeUtil.limiteMaximo(OfficeUtil.preencherEspacosDireita(numeroDocumento, 17), 14));
		header.append(OfficeUtil.retornaDataSomenteNumeros(dataVencimento));
		header.append(OfficeUtil.preencherZeroEsquerda(OfficeUtil.retornarValorFormatado(valorTitulo), 15));
		header.append(OfficeUtil.preencherZeroEsquerda("", 5));
		header.append(OfficeUtil.preencherEspacosEsquerda("", 1));
		header.append(constant.ESPECIE_TITULO);
		header.append(constant.ACEITE); 	
		header.append(OfficeUtil.retornaDataSomenteNumeros(dataEmissao));
		header.append(constant.COD_JURO);
		header.append(OfficeUtil.retornaDataSomenteNumeros(dataVencimento));
		header.append(OfficeUtil.preencherZeroEsquerda(constant.VALORJUROSAODIA, 15));
		header.append(constant.COD_DESCONTO);
		header.append(OfficeUtil.retornaDataSomenteNumeros(dataVencimento));
		header.append(OfficeUtil.preencherZeroEsquerda(constant.VALORDESCONTO, 15));
		header.append(OfficeUtil.preencherZeroEsquerda("0", 15));
		header.append(OfficeUtil.preencherZeroEsquerda("0", 15));
		header.append(OfficeUtil.limiteMaximo(OfficeUtil.preencherEspacosEsquerda(identificacaoTitulo, 25), 24));
		header.append(constant.COD_PROTESTO);
		header.append(constant.DIAS_P_PROTESTO);
		header.append("0"); //CODIGO DE BAIXA DEVOLUCAO INFORMADO PELA SICCOOB
		header.append(OfficeUtil.espacos(3)); //DIAS PARA BAIXA
		header.append(constant.COD_MOEDA); //MOEDA
		header.append(OfficeUtil.preencherZeroEsquerda("", 10)); //Numero contrato
		header.append(OfficeUtil.espacos(1)); //FEBRABAM
		return header;
	}

	
	
	//TODO SEQUENCIAL DE REGISTRO deve continuar incrementano em relacao ao segmento P, se o segmento p foi 00001 esse deve ser 0002
	public static StringBuilder seguimentoQ(int sequencialInternaLote,int sequencialregistro,String cpfPagador,String nomePagador,
											String endereco,String bairro,String cep, String cidade,String UF){
		StringBuilder header = new StringBuilder();
		header.append(constant.COD_BANCO);// 01 ao 03
		header.append(OfficeUtil.preencherZeroEsquerda(sequencialInternaLote+"", 4));// 01 ao 03
		header.append(constant.TP_REGISTRO_SEGUIMENTO_Q);// 01 ao 03
		header.append(OfficeUtil.preencherZeroEsquerda(sequencialregistro+"", 5));// 01 ao 03
		header.append(constant.SEGUIMENTO_Q);
		header.append(OfficeUtil.espacos(1));
		header.append(constant.MOVIMENTO_REMESSA_ENTRADA_TITULO);
		header.append(constant.TP_PAGADOR);
		header.append(OfficeUtil.preencherZeroEsquerda(cpfPagador, 15));
		header.append(OfficeUtil.limiteMaximo(OfficeUtil.preencherEspacosDireita(nomePagador, 41), 39));
		header.append(OfficeUtil.limiteMaximo(OfficeUtil.preencherEspacosDireita(endereco, 41), 39));
		header.append(OfficeUtil.limiteMaximo(OfficeUtil.preencherEspacosDireita(bairro, 16), 14));
		header.append(OfficeUtil.limiteMaximo(OfficeUtil.preencherEspacosDireita(cep, 9), 7));
		header.append(OfficeUtil.limiteMaximo(OfficeUtil.preencherEspacosDireita(cidade, 16), 14));
		header.append(OfficeUtil.limiteMaximo(OfficeUtil.preencherEspacosDireita(UF, 3), 1));
		header.append(constant.TP_PAGADOR);
		header.append(OfficeUtil.preencherZeroEsquerda(cpfPagador, 15));
		header.append(OfficeUtil.limiteMaximo(OfficeUtil.preencherEspacosDireita(nomePagador, 41), 39));
		header.append(OfficeUtil.preencherZeroEsquerda("", 3));
		header.append(OfficeUtil.preencherEspacosEsquerda("", 20));
		header.append(OfficeUtil.preencherEspacosEsquerda("", 8));
			
		return header;
	}
	public static StringBuilder seguimentoR(int sequencialInternaLote,int sequencialregistro,Date dataVencimento){
		
		StringBuilder header = new StringBuilder();
		
		header.append(constant.COD_BANCO);
		header.append(OfficeUtil.preencherZeroEsquerda(sequencialInternaLote+"", 4));
		header.append(constant.TP_REGISTRO_SEGUIMENTO_R);
		header.append(OfficeUtil.preencherZeroEsquerda(sequencialregistro+"", 5));
		header.append(constant.SEGUIMENTO_R);
		header.append(OfficeUtil.espacos(1));
		header.append(constant.MOVIMENTO_REMESSA_ENTRADA_TITULO);
		header.append(constant.COD_DESCONTO_2);
		/*header.append(OfficeUtil.retornaDataSomenteNumeros(dataVencimento));*/
		header.append(OfficeUtil.preencherZeroEsquerda("", 8));
		header.append(OfficeUtil.preencherZeroEsquerda("", 15));
		header.append(constant.COD_DESCONTO_2);
		header.append(OfficeUtil.preencherZeroEsquerda("", 8));
		header.append(OfficeUtil.preencherZeroEsquerda("", 15));
		header.append(constant.COD_MULTA);
		header.append(OfficeUtil.retornaDataSomenteNumeros(dataVencimento));
		header.append(OfficeUtil.preencherZeroEsquerda(constant.VALORMULTA, 15));//MULTA DE 2%
		header.append(OfficeUtil.espacos(10));
		header.append(OfficeUtil.limiteMaximo(OfficeUtil.preencherEspacosDireita("", 40), 39));
		header.append(OfficeUtil.limiteMaximo(OfficeUtil.preencherEspacosDireita("", 40), 39));
		header.append(OfficeUtil.espacos(20));
		header.append(OfficeUtil.preencherZeroEsquerda("", 8));
		header.append(OfficeUtil.preencherZeroEsquerda("", 3));
		header.append(OfficeUtil.preencherZeroEsquerda("", 5));
		header.append(OfficeUtil.preencherEspacosEsquerda("", 1));
		header.append(OfficeUtil.preencherZeroEsquerda("", 12));
		header.append(OfficeUtil.preencherEspacosEsquerda("", 1));
		header.append(OfficeUtil.preencherEspacosEsquerda("", 1));
		header.append(OfficeUtil.preencherZeroEsquerda("", 1));
		header.append(OfficeUtil.espacos(9));
		
		return header;
	}
	
	public static StringBuilder seguimentoS(int sequencialInternaLote,int sequencialregistro){
		StringBuilder header = new StringBuilder();
		header.append(constant.COD_BANCO);
		header.append(OfficeUtil.preencherZeroEsquerda(sequencialInternaLote+"", 4));
		header.append(constant.TP_REGISTRO_SEGUIMENTO_S);
		header.append(OfficeUtil.preencherZeroEsquerda(sequencialregistro+"", 5));
		header.append(constant.SEGUIMENTO_S);
		header.append(OfficeUtil.espacos(1));
		header.append(constant.MOVIMENTO_REMESSA_ENTRADA_TITULO);
		header.append("3"); 
		header.append(OfficeUtil.preencherEspacosEsquerda("", 40)); //TODO CAMPO NAO UTILIZADO
		header.append(OfficeUtil.preencherEspacosEsquerda("", 40)); //TODO CAMPO NAO UTILIZADO
		header.append(OfficeUtil.preencherEspacosEsquerda("", 40)); //TODO CAMPO NAO UTILIZADO
		header.append(OfficeUtil.preencherEspacosEsquerda("", 40)); //TODO CAMPO NAO UTILIZADO
		header.append(OfficeUtil.preencherEspacosEsquerda("", 40)); //TODO CAMPO NAO UTILIZADO
		header.append(OfficeUtil.preencherEspacosEsquerda("", 22)); //TODO CAMPO NAO UTILIZADO
		
		return header;
	}
		
	//TODO SEQUENCIAL DO LOTE COMEÇA EM 0001 e vai incrementetando para cara lote
	//TODO os valores tem 2 casas decimais
	public static StringBuilder traillerLote(int sequencialLote, int qtadeRegistros, int qtidadeCobrancasSimples, 
			double valotTotalCobrancaSimples, int qtidadeCobrancaVinculada, double valorTotalCobrancaVinculada,
			int qtidadeCobrancaCaucionada, double valotTotalCobrancaDescontada){
		StringBuilder trailler = new StringBuilder();
		trailler.append(constant.COD_BANCO);// 01 ao 03
		trailler.append(OfficeUtil.preencherZeroEsquerda(sequencialLote+"", 4));// 01 ao 03
		trailler.append(constant.TP_REGISTRO_LOTE);// 01 ao 03
		trailler.append(OfficeUtil.espacos(9));
		trailler.append(OfficeUtil.preencherZeroEsquerda(qtadeRegistros+"", 6));// 01 ao 03
		trailler.append(OfficeUtil.preencherZeroEsquerda(qtidadeCobrancasSimples+"", 6));// 01 ao 03
		trailler.append(OfficeUtil.preencherZeroEsquerda(OfficeUtil.retornarValorFormatado(valotTotalCobrancaSimples)+"", 17));// 01 ao 03
		trailler.append(OfficeUtil.preencherZeroEsquerda(qtidadeCobrancaVinculada+"", 6));// 01 ao 03
		trailler.append(OfficeUtil.preencherZeroEsquerda(OfficeUtil.retornarValorFormatado(valorTotalCobrancaVinculada)+"", 17));// 01 ao 03
		trailler.append(OfficeUtil.preencherZeroEsquerda(qtidadeCobrancaCaucionada+"", 6));// 01 ao 03
		trailler.append(OfficeUtil.preencherZeroEsquerda("", 17));// quantidade de cobranca causionada
		trailler.append(OfficeUtil.preencherZeroEsquerda("", 6));// quantidade de cobrancas descontadas
		trailler.append(OfficeUtil.preencherZeroEsquerda(OfficeUtil.retornarValorFormatado(valotTotalCobrancaDescontada)+"", 17));// 01 ao 03
		trailler.append(OfficeUtil.espacos(8));
		trailler.append(OfficeUtil.espacos(117));

		return trailler;
	}
	
	public static StringBuilder traillerArquivo(int qtdadeLotes, int qtdadeRegistros){
		StringBuilder header = new StringBuilder();
		header.append(constant.COD_BANCO);// 01 ao 03
		header.append(constant.LOTE);// 01 ao 03
		header.append(constant.TP_REGISTRO_TRAILLER);// 01 ao 03
		header.append(OfficeUtil.espacos(9));// 01 ao 03
		header.append(OfficeUtil.preencherZeroEsquerda(qtdadeLotes+"", 6));// 01 ao 03
		header.append(OfficeUtil.preencherZeroEsquerda(qtdadeRegistros+"", 6));// 01 ao 03
		header.append(constant.NUM_QUANTIDADE_CONTAS);// 01 ao 03
		header.append(OfficeUtil.espacos(205));		
		
		return header;
	}


	public byte[] geraBaixa(Pagador pagador, String sequencialArquivo) {
		return geraRemessa(pagador, sequencialArquivo, true);
	}
}
