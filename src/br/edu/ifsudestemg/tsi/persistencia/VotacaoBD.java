package br.edu.ifsudestemg.tsi.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/** Gerencia votação fazendo requisições ao banco de dados do sistema
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see Database
 */
public class VotacaoBD extends Database{

	/**Construtor default
	 * 
	 */
	public VotacaoBD() {
		super();
	}
	
	/** Insere na tabela de banco de dados os cargos a serem votados no simulador de votação
	 * 
	 * @param cargosParaVotacao {@code ArrayList<String>} com cargos a serem votados
	 */
	public void insereCargosParaVotacao(ArrayList<String> cargosParaVotacao){
		abreConexao();
		
		gravar("TRUNCATE TABLE votacao_cargos CASCADE");
		
		for(int i = 0; i < cargosParaVotacao.size() ; i++)
			gravar(
					"INSERT INTO votacao_cargos " +
					"VALUES('"+cargosParaVotacao.get(i)+"'," +
					"( SELECT \"qtdDigitos\" " +
							"FROM cargo " +
							"WHERE nome " +
							"ILIKE '"+cargosParaVotacao.get(i)+"'));"
							);
		fechaConexao();
	}
	
	/** Consulta cargos na tabela <code>votacao_cargos</code> no banco de dados
	 * 
	 * @return Um <code>ResultSet</code> com os cargos a serem votados
	 * 
	 * @see ResultSet
	 */
	public ResultSet consultaCargosCadastrados(){
		abreConexao();
		ResultSet resultado = consultaTabela("SELECT * FROM votacao_cargos ORDER BY \"qtdDigitos\" DESC");
		fechaConexao();
		return resultado;
	}
	
	/** Consulta numero de partido na tabela "partido" no banco de dados
	 * 
	 * @param numero <code>String<String></code> com o número do partido
	 * 
	 * @return Uma <code>String</code> com o nome do partido
	 */
	public String consultaPartido(String numero){
		List<String> sigla = new ArrayList<String>(); 
		String[] siglaStr;
		String partido = null;
		
		abreConexao();
		
		ResultSet resultado = consultaTabela("SELECT sigla FROM partido WHERE numero = "+numero);
			
		try {
			while(resultado.next())
			{
				sigla.add( resultado.getString("sigla") ); // Adiciona na lista os nomes 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 		
		fechaConexao();
		
		if(sigla.size() > 0){
			siglaStr = (String[]) sigla.toArray (new String[0]);   // transfere para string
			partido = siglaStr[0];
			return partido;
		}
		return null;		
	}
	
	/** Consulta nome do candidato na tabela <code>nome</code> no banco de dados
	 * 
	 * @param numero <code>String<String></code> com o número do partido
	 * @param cargo <code>String<String></code> com o cargo do candidato
	 * 
	 * @return Uma <code>String</code> com o nome do candidato
	 */
	public String consultaNome(String numero,String cargo){
		List<String> nome = new ArrayList<String>(); 
		String[] nomeStr;
		String nomeCandidato = null;
		
		abreConexao();
		
		ResultSet resultado = consultaTabela("" +
				"SELECT nome " +
				"FROM candidato " +
				"WHERE numero = " + numero +
				" AND cargo ILIKE '"+cargo+"'");
			
		try {
			while(resultado.next())
			{
				nome.add( resultado.getString("nome") ); // Adiciona na lista os nomes 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 		
		fechaConexao();
		
		if(nome.size() > 0){
			nomeStr = (String[]) nome.toArray (new String[0]);   // transfere para string
			nomeCandidato = nomeStr[0];
			return nomeCandidato;
		}
		return null;		
	}
	
	/**Consulta imagem do candidato na tabela <code>diretorio</code> no banco de dados
	 * 
	 * @param numero <code>String<String></code> com o número do partido
	 * @param cargo <code>String<String></code> com o cargo do candidato
	 * 
	 * @return Uma <code>String</code> com o caminho da imagem do candidato
	 */
	public String consultaImagem(String numero,String cargo){
		List<String> diretorio = new ArrayList<String>(); 
		String[] diretorioStr;
		String diretorioCandidato = null;
		
		abreConexao();
		
		ResultSet resultado = consultaTabela("" +
				"SELECT diretorio " +
				"FROM candidato " +
				"WHERE numero = " + numero +
				" AND cargo ILIKE '"+cargo+"'");
			
		try {
			while(resultado.next())
			{
				diretorio.add( resultado.getString("diretorio") ); // Adiciona na lista os diretorios 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 		
		fechaConexao();
		
		if(diretorio.size() > 0){
			diretorioStr = (String[]) diretorio.toArray (new String[0]);   // transfere para string
			diretorioCandidato = diretorioStr[0];
			return diretorioCandidato;
		}
		return null;		
	}
	
	/** Insere voto na tabela <code>votacao_candidatos_votos</code> no banco de dados
	 * 
	 * @param nome <code>String<String></code> com o nome do candidato
	 * @param numero <code>String<String></code> com o número do partido
	 * @param cargo <code>String<String></code> com o cargo do candidato
	 */
	public void insereVoto(String nome,String numero,String cargo){
		abreConexao();
		gravar("INSERT INTO votacao_candidatos_votos " +
				"VALUES('"+nome+"', "+numero+", '"+cargo+"')");
		fechaConexao();
	}
}
