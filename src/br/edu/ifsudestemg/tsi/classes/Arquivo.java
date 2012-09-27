package br.edu.ifsudestemg.tsi.classes;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/** Gerencia acesso a arquivos em disco
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see FileWriter
 *
 */
public class Arquivo {

	/** Salva uma mensagem em um arquivo em disco
	 * 
	 * @param arquivo <code>String</code> com o nome do arquivo
	 * @param conteudo <code>String</code> com o conteúdo a ser salvo
	 * @param adicionar <code>boolean</code> para adicionar ou não
	 * @throws IOException caso erro de entra ou saida dos dados do arquivo
	 */
	public static void salvar(String arquivo, String conteudo, boolean adicionar)
			throws IOException {

		FileWriter fw = new FileWriter(arquivo, adicionar);

		fw.write(conteudo);
		fw.close();
	}

	/** Abre um arquivo do disco
	 * 
	 * @param arquivo <code>String</code> com o nome do arquivo a ser aberto
	 * @return {@code ArrayList<String>} com o conteúdo do arquivo
	 * @throws FileNotFoundException caso não encontre o arquivo
	 * @throws IOException caso erro de entra ou saida dos dados do arquivo
	 */
	public static ArrayList<String> carregar(String arquivo)
			throws FileNotFoundException, IOException {

		File file = new File(arquivo);

		if (! file.exists()) {
			return null;
		}

		BufferedReader br = new BufferedReader(new FileReader(arquivo));

		String linha;
		ArrayList<String> linhas = new ArrayList<String>();
		while( (linha = br.readLine()) != null ){
			linhas.add(linha);
		}
		
		
		br.close();
		return linhas;
	}
}