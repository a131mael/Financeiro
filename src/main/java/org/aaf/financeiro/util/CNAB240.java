package org.aaf.financeiro.util;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import org.aaf.financeiro.util.constantes.CNB240_SICOOB_CONSTANTS_ADONAI;
import org.aaf.financeiro.util.constantes.CNB240_SICOOB_CONSTANTS_TEFAMEL;
import org.aaf.financeiro.util.constantes.Constante;
import org.joda.time.DateTime;
import org.joda.time.Days;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.bancos.gerador.GeradorDeDigito;

public class CNAB240 {
	
	private static Constante constant = null;

	public CNAB240(int constante) {
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

	public static void main(String[] args) {

		System.out.println(gerarNumeroBoleto("57", new Date(), "22000"));

	}

	public static Banco getBancoSicoob() {
		Banco banco = new Banco() {

			public String getNumeroFormatadoComDigito() {
				// TODO Auto-generated method stub
				return null;
			}

			public String getNumeroFormatado() {
				// TODO Auto-generated method stub
				return null;
			}

			public String getNossoNumeroFormatado(Beneficiario arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			public String getNossoNumeroECodigoDocumento(Boleto arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			public URL getImage() {
				// TODO Auto-generated method stub
				return null;
			}

			public GeradorDeDigito getGeradorDeDigito() {
				// TODO Auto-generated method stub
				return null;
			}

			public String getCodigoBeneficiarioFormatado(Beneficiario arg0) {
				return arg0.getCodigoBeneficiario();
			}

			public String getCarteiraFormatado(Beneficiario arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			public String getAgenciaECodigoBeneficiario(Beneficiario arg0) {
				
				return constant.COD_AGENCIA+constant.COD_BENEFICIARIO;
			}

			public String geraCodigoDeBarrasPara(Boleto arg0) {
				String seq = sequenciaCodigoBarras(arg0.getNumeroDoDocumento(), arg0.getDatas().getVencimento().getTime(), arg0.getValorBoleto().toString());
				return seq;
			}
		};
		return banco;

	}

	public static String gerarNumeroBoleto(String numeroBoleto, Date dataVencimento, String valorBoleto) {
		StringBuilder campo1 = new StringBuilder();
		campo1.append(constant.COD_BANCO);
		campo1.append(constant.COD_MOEDA);
		campo1.append(constant.COD_CARTEIRA);
		campo1.append(constant.COD_AGENCIA);
		campo1.append(modulo10(campo1.toString()));

		StringBuilder campo2 = new StringBuilder();
		campo2.append(constant.COD_MODALIDADE);
		campo2.append(constant.COD_BENEFICIARIO);
		campo2.append(completaNossoNumero(numeroBoleto).substring(0, 1));
		campo2.append(modulo10(campo2.toString()));

		StringBuilder campo3 = new StringBuilder();
		campo3.append(completaNossoNumero(numeroBoleto).substring(1, completaNossoNumero(numeroBoleto).length()));
		campo3.append(verificadorNossoNumero(completaNossoNumero(numeroBoleto)));
		campo3.append(constant.NUM_PARCELA);
		campo3.append(modulo10(campo3.toString()));

		StringBuilder campo4 = new StringBuilder();
		campo4.append(fatorDeVencimento(dataVencimento));
		campo4.append(concatenarZerosAEsquerda(valorBoleto, 10));

		/*StringBuilder campoL = new StringBuilder();
		campoL.append(CNB240_SICOOB_CONSTANTS.COD_BANCO);
		campoL.append(CNB240_SICOOB_CONSTANTS.COD_MOEDA);
		campoL.append(CNB240_SICOOB_CONSTANTS.COD_CARTEIRA);
		campoL.append(CNB240_SICOOB_CONSTANTS.COD_AGENCIA);
		campoL.append(CNB240_SICOOB_CONSTANTS.COD_MODALIDADE);
		campoL.append(CNB240_SICOOB_CONSTANTS.COD_BENEFICIARIO);
		campoL.append(completaNossoNumero(numeroBoleto).substring(0, 1));
		campoL.append(completaNossoNumero(numeroBoleto).substring(1, completaNossoNumero(numeroBoleto).length()));
		campoL.append(verificadorNossoNumero(completaNossoNumero(numeroBoleto)));
		campoL.append(CNB240_SICOOB_CONSTANTS.NUM_PARCELA);
		campoL.append(fatorDeVencimento(dataVencimento));
		campoL.append(concatenarZerosAEsquerda(valorBoleto, 10));*/

		StringBuilder numeroBoletoFinal = new StringBuilder();
		numeroBoletoFinal.append(campo1.toString() + campo2.toString() + campo3.toString()
				+ modulo11(sequenciaCodigoBarrasSemDV(numeroBoleto, dataVencimento, valorBoleto)) + campo4);

		return numeroBoletoFinal.toString();

	}

	public static String sequenciaCodigoBarrasSemDV(String numeroBoleto, Date dataVencimento, String valorBoleto) {
		StringBuilder seq = new StringBuilder();
		seq.append(constant.COD_BANCO);
		seq.append(constant.COD_MOEDA);
		seq.append(fatorDeVencimento(dataVencimento));
		seq.append((concatenarZerosAEsquerda(valorBoleto, 10)));

		seq.append(constant.COD_CARTEIRA);
		seq.append(constant.COD_AGENCIA);
		seq.append(constant.COD_MODALIDADE);
		seq.append(constant.COD_BENEFICIARIO);
		seq.append(completaNossoNumero(numeroBoleto));
		seq.append(verificadorNossoNumero(completaNossoNumero(numeroBoleto)));

		seq.append(constant.NUM_PARCELA);

		return seq.toString();
	}
	
	public static String sequenciaCodigoBarras(String numeroBoleto, Date dataVencimento, String valorBoleto) {
		StringBuilder seq = new StringBuilder();
		seq.append(constant.COD_BANCO);
		seq.append(constant.COD_MOEDA);
		
		seq.append(fatorDeVencimento(dataVencimento));
		seq.append((concatenarZerosAEsquerda(valorBoleto, 10)));

		seq.append(constant.COD_CARTEIRA);
		seq.append(constant.COD_AGENCIA);
		seq.append(constant.COD_MODALIDADE);
		seq.append(constant.COD_BENEFICIARIO);
		seq.append(completaNossoNumero(numeroBoleto));
		seq.append(verificadorNossoNumero(completaNossoNumero(numeroBoleto)));

		seq.append(constant.NUM_PARCELA);

		int dv = modulo11(seq.toString());
		
		StringBuilder seqDV = new StringBuilder();
		seqDV.append(constant.COD_BANCO);
		seqDV.append(constant.COD_MOEDA);
		seqDV.append(dv);
		
		seqDV.append(fatorDeVencimento(dataVencimento));
		seqDV.append((concatenarZerosAEsquerda(valorBoleto, 10)));

		seqDV.append(constant.COD_CARTEIRA);
		seqDV.append(constant.COD_AGENCIA);
		seqDV.append(constant.COD_MODALIDADE);
		seqDV.append(constant.COD_BENEFICIARIO);
		seqDV.append(completaNossoNumero(numeroBoleto));
		seqDV.append(verificadorNossoNumero(completaNossoNumero(numeroBoleto)));

		seqDV.append(constant.NUM_PARCELA);
		
		return seqDV.toString();
	}

	public static int modulo10(String numero) {
		int soma = 0;
		boolean dobrado = true;

		for (int i = numero.length(); i >= 1; i--) {

			int numeroAux = Integer.valueOf(numero.substring(i - 1, i));
			if (dobrado) {
				numeroAux *= 2;
				dobrado = false;
			} else {
				dobrado = true;
			}

			if (numeroAux > 9) {
				int numeroSum1 = Integer.valueOf(String.valueOf(numeroAux).substring(0, 1));
				int numeroSum2 = Integer.valueOf(String.valueOf(numeroAux).substring(1, 2));
				soma += numeroSum1;
				soma += numeroSum2;
			} else {
				soma += numeroAux;
			}

		}

		return dezenaSuperior(soma) - soma;
	}

	public static int modulo11(String numero) {
		int soma = 0;
		int multiplicador = 2;

		for (int i = numero.length(); i >= 1; i--) {
			int numeroAux = Integer.valueOf(numero.substring(i - 1, i));
			soma += (numeroAux * multiplicador);
			/*
			 * System.out.println("multiplicador" + multiplicador);
			 * System.out.println("numero" + numeroAux);
			 * System.out.println("resultado" + numeroAux*multiplicador);
			 * System.out.println("soma" + soma);
			 * System.out.println("------------------------------------------");
			 */
			if (multiplicador == 9) {
				multiplicador = 2;
			}else{
				multiplicador++;
			}
		}

		int dv = soma % 11;

		dv = 11 - dv;
		if (dv == 1 || dv == 0 || dv > 9) {
			dv = 1;
		}
		return dv;
	}

	public static int dezenaSuperior(int valor) {
		int dezena = valor;
		while (dezena % 10 != 0) {
			dezena++;
		}
		return dezena;
	}

	// TODO tamanho do campo = 8 e inicia em 10000
	public static String completaNossoNumero(String valorAtual) {
		int contadorAtual = Integer.parseInt(valorAtual);
		int nossoNumero = contadorAtual /*+ 10000*/;
		StringBuilder nossoNumeroStr = new StringBuilder();
		nossoNumeroStr.append(nossoNumero);

		while (nossoNumeroStr.length() < 7) {
			nossoNumeroStr.insert(0, "0");
		}

		return nossoNumeroStr.toString();
	}

	public static long diferencaEntreDatas(Date data1, Date data2) {
		DateTime dt1 = new DateTime(data1.getTime());
		DateTime dt2 = new DateTime(data2.getTime());

		Days d = Days.daysBetween(dt2, dt1);
		int days = d.getDays();
		return days;
	}

	public static String fatorDeVencimento(Date data2) {
		Calendar dte = Calendar.getInstance();
		dte.set(2000, 06, 03); // TODO DATA FIXA DO BOLETO SERA MUDADO EM 2025
		DateTime dt1 = new DateTime(dte.getTime());

		return String.valueOf((diferencaEntreDatas(data2, dt1.toDate()) + 1001));
	}

	public static String verificadorNossoNumero(String numeroBoleto) {
		int soma = 0;
		String nossoNumero = concatenarZerosAEsquerda(constant.COD_AGENCIA, 4)
				+ concatenarZerosAEsquerda(constant.COD_BENEFICIARIO, 10)
				+ concatenarZerosAEsquerda(numeroBoleto, 7);
		int[] constant = { 3, 1, 9, 7, 3, 1, 9, 7, 3, 1, 9, 7, 3, 1, 9, 7, 3, 1, 9, 7, 3 };

		// String nossoNumeroConcat = concatenarZerosAEsquerda(nossoNumero, 21);

		for (int i = 0; i < constant.length; i++) {
			soma += constant[i] * Integer.valueOf((nossoNumero.substring(i, i + 1)));
		}

		int dv = Integer.valueOf(soma) % 11;

		if (dv == 1 || dv == 0) {
			dv = 0;
		} else {
			dv = 11 - dv;
		}
		return (String.valueOf(dv));
	}

	public static String concatenarZerosAEsquerda(String numero, int tamanho) {
		StringBuilder numeroFinal = new StringBuilder(numero);
		for (int i = numero.length(); i < tamanho; i++) {
			numeroFinal.insert(0, "0");
		}

		return numeroFinal.toString();
	}

}
