package org.aaf.financeiro.util.constantes;

public class ConstanteRemessa {

	public ConstanteRemessa(CNAB240_SICOOB_REMESSA_CONSTANTS_ADONAI remessa) {
		NUM_INSCRICAO_EMPRESA = remessa.NUM_INSCRICAO_EMPRESA;
		COD_CONTA_CORRENTE = remessa.COD_CONTA_CORRENTE;
		VER_CONTA_CORRENTE = remessa.VER_CONTA_CORRENTE;
		NOME_EMPRESA = remessa.NOME_EMPRESA;
	}

	public ConstanteRemessa(CNAB240_SICOOB_REMESSA_CONSTANTS_TEFAMEL remessa) {
		NUM_INSCRICAO_EMPRESA = remessa.NUM_INSCRICAO_EMPRESA;
		COD_CONTA_CORRENTE = remessa.COD_CONTA_CORRENTE;
		VER_CONTA_CORRENTE = remessa.VER_CONTA_CORRENTE;
		NOME_EMPRESA = remessa.NOME_EMPRESA;
	}

	//TODO HEADER
		public static  String COD_BANCO = "756"; //CODIGO DA SICOOB
		public static  String COD_COOPERATIVA = "3069"; //CODIGO DA SICOOB
		public static  String LOTE_SERVICO = "0000"; //LOTE DE SERVICO
		public static  String VER_COD_COOPERATIVA = "0"; //CODIGO DA SICOOB
		public static  String TP_REGISTRO = "0"; //TIPO DE REGISTRO
		public static  String TP_EMPRESA = "2"; //TIPO DE REGISTRO de empresa 2 = CNPJ
		public static  String NUM_INSCRICAO_EMPRESA =  "03660921000179"; //CNPJ da Tefamel
		public static  String COD_CONTA_CORRENTE = "77426"; //Codigo do beneficiario no banco
		public static  String VER_CONTA_CORRENTE = "0"; //Codigo do beneficiario no banco
		public static  String NOME_EMPRESA = "FAVO DE MEL TRANSPORTE ESCOLAR E TURISMO LTDA - M"; //CNPJ da Tefamel
		public static  String NOME_BANCO = "SICOOB"; //Codigo do beneficiario no banco
		public static  String COD_REMESSA__RETORNO = "1"; //Codigo do beneficiario no banco
		public static  String NUM_VERSAO_LAYOUT = "081"; //Codigo do beneficiario no banco
		public static  String DENSIDADE_GRAVAO_ARQUIVO = "00000"; //Codigo do beneficiario no banco
		
		//TODO SEGUIMENTO P
		public static  String TP_REGISTRO_SEGUIMENTO_P = "3"; 
		public static  String MOVIMENTO_REMESSA_ENTRADA_TITULO = "01";
		public static  String MOVIMENTO_REMESSA_BAIXA_TITULO = "02";
		public static  String SEGUIMENTO_P = "P"; 
		public static  String COD_CARTEIRA = "1";
		public static  String ESPECIE_TITULO = "04";
		public static  String ACEITE = "N";
		public static  String COD_JURO = "1";
		public static  String COD_DESCONTO = "1";
		public static  String COD_DESCONTO_2 = "0";
		public static  String COD_PROTESTO = "3";
		public static  String DIAS_P_PROTESTO = "00";
		public static  String DIAS_P_BAIXA = "180";
		public static  String VALORJUROSAODIA = "050";//50 centavos
		public static  String VALORDESCONTO = "2000";//20 reais
		
		//TODO SEGUIMENTO Q
		public static  String TP_REGISTRO_SEGUIMENTO_Q = "3";
		public static  String SEGUIMENTO_Q = "Q"; //LOTE DE SERVICO
			
		//TODO SEGUIMENTO R
		public static  String TP_REGISTRO_SEGUIMENTO_R = "3";
		public static  String SEGUIMENTO_R = "R"; //LOTE DE SERVICO
		public static  String COD_MULTA = "2";
		public static  String VALORMULTA = "200";//2 % de multa
		
		//TODO SEGUIMENTO S
		public static  String TP_REGISTRO_SEGUIMENTO_S = "3";
		public static  String SEGUIMENTO_S = "S"; //LOTE DE SERVICO
		public static  String COD_MOEDA = "09"; 
		public static  String TP_PAGADOR = "1"; 
		
			
		
		//TODO HEADER Lote
		public static  String TP_REGISTRO_HEADER_LOTE = "1"; //LOTE DE SERVICO
		public static  String TP_OPERACAO_HEADER_LOTE = "R"; //LOTE DE SERVICO
		public static  String TP_SERVICO_HEADER_LOTE = "01"; //LOTE DE SERVICO
		public static  String LAYOUT_LOTE = "040"; //LOTE DE SERVICO
		
		//TODO TRAILLER Lote
		public static  String TP_REGISTRO_LOTE = "5"; //LOTE DE SERVICO
		
		//TODO TRAILLER ARQUIVO
		public static  String LOTE = "9999"; //LOTE DE SERVICO
		public static  String TP_REGISTRO_TRAILLER = "9"; //TIPO DE REGISTRO
		public static String NUM_QUANTIDADE_CONTAS = "000000"; //TIPO DE REGISTRO

}
