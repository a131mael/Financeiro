package org.aaf.financeiro.util.constantes;

import java.io.File;

public class Constante {

	public Constante(CNB240_SICOOB_CONSTANTS_ADONAI adonai) {
		setCOD_BANCO(adonai.COD_BANCO);
		COD_MOEDA = adonai.COD_MOEDA;
		COD_CARTEIRA = adonai.COD_CARTEIRA;
		COD_AGENCIA = adonai.COD_AGENCIA;
		VER_CAMPO1 = adonai.VER_CAMPO1;
		// 1
		COD_MODALIDADE = adonai.COD_MODALIDADE;
		COD_BENEFICIARIO = adonai.COD_BENEFICIARIO;
		NOSSO_NUMERO = adonai.NOSSO_NUMERO;
		VER_CAMPO2 = adonai.VER_CAMPO2;
		// 2
		NUM_PARCELA = adonai.NUM_PARCELA;
		VER_CAMPO3 = adonai.VER_CAMPO3;

		VER_BOLETO = adonai.VER_BOLETO;
		// 2
		FATOR_VENCIMENTO = adonai.FATOR_VENCIMENTO;
		VALOR_BOLETO = adonai.VALOR_BOLETO;
		COD_COOPERATiVA = adonai.COD_COOPERATiVA;

		CNPJ = adonai.CNPJ;
		NOME = adonai.NOME;
		RUA = adonai.RUA;
		// Tefamel
		BAIRRO = adonai.BAIRRO;
		CIDADE = adonai.CIDADE;
		CEP = adonai.CEP;
		UF = adonai.UF;

		VALORJUROSAODIA = adonai.VALORJUROSAODIA;
		VALORDESCONTO = adonai.VALORDESCONTO;
		VALORMULTA = adonai.VALORMULTA;
	}

	public Constante(CNB240_SICOOB_CONSTANTS_TEFAMEL tefamel) {
		setCOD_BANCO(tefamel.COD_BANCO);
		COD_MOEDA = tefamel.COD_MOEDA;
		COD_CARTEIRA = tefamel.COD_CARTEIRA;
		COD_AGENCIA = tefamel.COD_AGENCIA;
		VER_CAMPO1 = tefamel.VER_CAMPO1;
		// 1
		COD_MODALIDADE = tefamel.COD_MODALIDADE;
		COD_BENEFICIARIO = tefamel.COD_BENEFICIARIO;
		NOSSO_NUMERO = tefamel.NOSSO_NUMERO;
		VER_CAMPO2 = tefamel.VER_CAMPO2;
		// 2
		NUM_PARCELA = tefamel.NUM_PARCELA;
		VER_CAMPO3 = tefamel.VER_CAMPO3;

		VER_BOLETO = tefamel.VER_BOLETO;
		// 2
		FATOR_VENCIMENTO = tefamel.FATOR_VENCIMENTO;
		VALOR_BOLETO = tefamel.VALOR_BOLETO;
		COD_COOPERATiVA = tefamel.COD_COOPERATiVA;

		CNPJ = tefamel.CNPJ;
		NOME = tefamel.NOME;
		RUA = tefamel.RUA;
		// Tefamel
		BAIRRO = tefamel.BAIRRO;
		CIDADE = tefamel.CIDADE;
		CEP = tefamel.CEP;
		UF = tefamel.UF;
		
		VALORJUROSAODIA = tefamel.VALORJUROSAODIA;
		VALORDESCONTO = tefamel.VALORDESCONTO;
		VALORMULTA = tefamel.VALORMULTA;
	}

	public static String getCOD_BANCO() {
		return COD_BANCO;
	}

	public static void setCOD_BANCO(String cOD_BANCO) {
		COD_BANCO = cOD_BANCO;
	}

	public static String getCOD_MOEDA() {
		return COD_MOEDA;
	}

	public static void setCOD_MOEDA(String cOD_MOEDA) {
		COD_MOEDA = cOD_MOEDA;
	}

	public static String getCOD_CARTEIRA() {
		return COD_CARTEIRA;
	}

	public static void setCOD_CARTEIRA(String cOD_CARTEIRA) {
		COD_CARTEIRA = cOD_CARTEIRA;
	}

	public static String getCOD_AGENCIA() {
		return COD_AGENCIA;
	}

	public static void setCOD_AGENCIA(String cOD_AGENCIA) {
		COD_AGENCIA = cOD_AGENCIA;
	}

	public static String getVER_CAMPO1() {
		return VER_CAMPO1;
	}

	public static void setVER_CAMPO1(String vER_CAMPO1) {
		VER_CAMPO1 = vER_CAMPO1;
	}

	public static String getCOD_MODALIDADE() {
		return COD_MODALIDADE;
	}

	public static void setCOD_MODALIDADE(String cOD_MODALIDADE) {
		COD_MODALIDADE = cOD_MODALIDADE;
	}

	public static String getCOD_BENEFICIARIO() {
		return COD_BENEFICIARIO;
	}

	public static void setCOD_BENEFICIARIO(String cOD_BENEFICIARIO) {
		COD_BENEFICIARIO = cOD_BENEFICIARIO;
	}

	// public static String LOCAL_ARMAZENAMENTO_REMESSA =
	// "C:\\Sicoobnet\\RetornoCNAB\\";
	// public static String LOCAL_ARMAZENAMENTO_REMESSA =
	// File.separator+"home"+File.separator+"ubuntu" + File.separator+"cnab" +
	// File.separator;
	public static String LOCAL_ARMAZENAMENTO_REMESSA = File.separator + "home" + File.separator + "ubuntu"
			+ File.separator + "Skyunix" + File.separator + "inbox" + File.separator;
	public static String LOCAL_ARMAZENAMENTO_EXTRATO = File.separator + "home" + File.separator + "ubuntu"
			+ File.separator + "extrato" + File.separator;
	/*
	 * public static String LOCAL_ARMAZENAMENTO_EXTRATO =
	 * "C:\\Sicoobnet\\RetornoCNAB\\extrato\\";
	 */
	public static String COD_BANCO = "756";
	public static String COD_MOEDA = "9";
	public static String COD_CARTEIRA = "1";
	public static String COD_AGENCIA = "3069";
	public static String VER_CAMPO1 = "8";
	public static String COD_MODALIDADE = "01";
	public static String COD_BENEFICIARIO = "1199870";
	public static String NOSSO_NUMERO = "10000"; // Nosso NUmero de boleto
													// começa em 10000 e eh
													// unico para cada
													// boleto
	public static String VER_CAMPO2 = "2"; // CODIGO VERIFICADOR DO CAMPO
											// 2

	// TODO CAMPO 3
	public static String NUM_PARCELA = "001"; // NUMERO DA PARCELA
	public static String VER_CAMPO3 = "7"; // CODIGO VERIFICADOR DO CAMPO
											// 2

	// TODO CAMPO L
	public static String VER_BOLETO = "8"; // CODIGO VERIFICADOR DO CAMPO
											// 2

	// TODO CAMPO 4
	public static String FATOR_VENCIMENTO = "001"; // NUMERO DA PARCELA
	public static String VALOR_BOLETO = "8"; // CODIGO VERIFICADOR DO
												// CAMPO 2

	public static String COD_COOPERATiVA = "4434"; // CODIGO DA
													// COOPERATIVA PARA
													// DV do nosso
													// numero

	public static String CNPJ = "03.660.921/0001-79"; // CNPJ da Tefamel
	public static String NOME = "FAVO DE MEL TRANSPORTE ESCOLAR E TURISMO LTDA - M"; // CNPJ
																						// da
																						// Tefamel
	public static String RUA = "Rua José Cosme Pamplona"; // CNPJ da
															// Tefamel
	public static String BAIRRO = "Bela Vista"; // CNPJ da Tefamel
	public static String CIDADE = "PALHOCA"; // CNPJ da Tefamel
	public static String CEP = "88132-700"; // CNPJ da Tefamel
	public static String UF = "SC"; // CNPJ da Tefamel

	public static  String VALORJUROSAODIA = "050";// 50 centavos
	public static  String VALORDESCONTO = "2000";// 20 reais
	public static  String VALORMULTA = "200";// 2 % de multa

}
