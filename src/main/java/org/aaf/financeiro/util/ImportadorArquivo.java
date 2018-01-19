package org.aaf.financeiro.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.aaf.financeiro.model.Pagador;
import org.aaf.financeiro.sicoob.util.CNAB240_RETORNO_SICOOB;
import org.aaf.financeiro.util.constantes.Constante;

public class ImportadorArquivo {

	public List<Pagador> importarExtratoBancarioConciliacao(int escolarEscola){
		String importarDe = "";
		if(escolarEscola == 1){ //Tefamel
			importarDe = "tefamel";
		}else{
			importarDe = "escola";
		}
		List<Pagador> boletosImportados= CNAB240_RETORNO_SICOOB.importarExtratoCNAB240(Constante.LOCAL_ARMAZENAMENTO_EXTRATO+importarDe+File.separator+"importando" + File.separator);
		return boletosImportados;
	}
	
	public static void geraArquivoFisico(byte[] arquivo, String caminho){
		try {
			java.io.File file = new java.io.File(caminho);
			FileOutputStream in = null;
			in = new FileOutputStream(file);
			in.write(arquivo);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	
}
