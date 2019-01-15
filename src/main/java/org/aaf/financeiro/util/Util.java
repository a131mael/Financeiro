package org.aaf.financeiro.util;

public class Util {
	
	
	public static String removeCaracteresEspeciais(String texto) {
		texto = texto.replaceAll("[^aA-zZ-Z0-9 ]", "");
		return texto;
	}

}
