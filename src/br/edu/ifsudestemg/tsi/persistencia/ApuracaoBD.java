package br.edu.ifsudestemg.tsi.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**Gerencia apurações fazendo requisições ao banco de dados
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see Database
 *
 */
public class ApuracaoBD extends Database {

	/**Consulta cargos que foram cadastrados para simulação de voto
	 * 
	 * @return Um <code>ResultSet</code> com os cargos cadastrados para simulaçao de voto
	 */
	public ResultSet consultaCargo(){
		abreConexao();
		ResultSet resultado = consultaTabela("SELECT nome FROM votacao_cargos");
		fechaConexao();
		return resultado;
	}
	
	//Total de votos para numero
	/**Consulta quantidade votos para o candidato especificado
	 * 
	 * @param cargo <code>String</code> com nome do cargo
	 * @param numero <code>String</code> com número do candidato
	 * 
	 * @return Um <code>int</code> com quantidade de votos para o candidato encontrado na consulta
	 */
	public int consultaVotos(String cargo,String numero){
		int total = 0;
		List<String> count = new ArrayList<String>(); 
		String[] countsStr;
		abreConexao();
		
		ResultSet resultado = consultaTabela("" +
				"SELECT count(*) AS votos FROM votacao_candidatos_votos " +
				"WHERE cargo ILIKE '"+cargo+"' " +
				"AND numero = " + numero);
		try {
			while(resultado.next())
			{
				count.add( resultado.getString("votos") ); 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 
		
		countsStr = (String[]) count.toArray (new String[0]);   // transfere para string
		total = Integer.parseInt(countsStr[0]);
		fechaConexao();
		return total;
	}
	
	//Total de votantes
	/**Consulta número total de votantes para o cargo especificado na pesquisa
	 * 
	 * @param cargo <code>String</code> com nome do cargo
	 * 
	 * @return Um <code>int</code> com quantidade de votantes para o cargo especificado
	 */
	public int consultaNumeroEleitores(String cargo){
		int total = 0;
		List<String> count = new ArrayList<String>(); 
		String[] countsStr;
		abreConexao();
		
		ResultSet resultado = consultaTabela("" +
				"SELECT count(*) AS total FROM votacao_candidatos_votos " +
				"WHERE cargo ILIKE '"+cargo+"'");
		try {
			while(resultado.next())
			{
				count.add( resultado.getString("total") ); 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 
		
		countsStr = (String[]) count.toArray (new String[0]);   // transfere para string
		total = Integer.parseInt(countsStr[0]);
		fechaConexao();
		return total;
	}
	
	//Consulta os numeros e nomes dos candidatos da tabela pelo cargo
	/**Consulta os números e nomes dos candidatos a partir do cargo especificado
	 * 
	 * @param cargo <code>String</code> com nome do cargo
	 * 
	 * @return Um <code>ResultSet</code> com número e nome dos candidatos do cargo especificado
	 */
	public ResultSet consultaCandidatos(String cargo){
		abreConexao();
		ResultSet resultado = consultaTabela("" +
				"SELECT DISTINCT numero,nome " +
				"FROM votacao_candidatos_votos " +
				"WHERE cargo ILIKE '"+cargo+"' " +
				"AND nome <> 'BRANCO' " +
				"AND nome <> 'NULO' " +
				"ORDER BY numero");
		fechaConexao();
		return resultado;
	}
	
	//Total de Votos em branco
	/** Consulta total de votos em branco para o cargo especificado
	 * 
	 * @param cargo <code>String</code> com nome do cargo
	 * 
	 * @return Um <code>int</code> com quantidade de votos em branco para o cargo especificado
	 */
	public int consultaVotosBranco(String cargo){
		int total = 0;
		List<String> count = new ArrayList<String>(); 
		String[] countsStr;
		abreConexao();
		
		ResultSet resultado = consultaTabela("" +
				"SELECT count(*) AS votos_branco " +
				"FROM votacao_candidatos_votos " +
				"WHERE nome ILIKE 'BRANCO' " +
				"AND cargo ILIKE '"+cargo+"'");
		try {
			while(resultado.next())
			{
				count.add( resultado.getString("votos_branco") ); 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 
		
		countsStr = (String[]) count.toArray (new String[0]);   // transfere para string
		total = Integer.parseInt(countsStr[0]);
		fechaConexao();
		return total;
	}
	
	//Total de Votos em nulo
	/**Consulta total de votos nulo para o cargo especificado
	 * 
	 * @param cargo <code>String</code> com nome do cargo
	 * @return Um <code>int</code> com quantidade de votos nulo para o cargo especificado
	 */
	public int consultaVotosNulo(String cargo){
		int total = 0;
		List<String> count = new ArrayList<String>(); 
		String[] countsStr;
		abreConexao();
		
		ResultSet resultado = consultaTabela("" +
				"SELECT count(*) AS votos_nulo " +
				"FROM votacao_candidatos_votos " +
				"WHERE nome ILIKE 'NULO' " +
				"AND cargo ILIKE '"+cargo+"'");
		try {
			while(resultado.next())
			{
				count.add( resultado.getString("votos_nulo") ); 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 
		
		countsStr = (String[]) count.toArray (new String[0]);   // transfere para string
		total = Integer.parseInt(countsStr[0]);
		fechaConexao();
		return total;
	}
	
	/** Consulta total de eleitores presentes na ultima simulação
	 * 
	 * @return <code>int</code> com total de eleitores da simulação de votos
	 */
	public int consultaTotalEleitores(){
		int total = 0;
		List<String> count = new ArrayList<String>(); 
		String[] countsStr;
		abreConexao();
		
		ResultSet resultado = consultaTabela("" +
				"SELECT count(*) AS total " +
				"FROM votacao_candidatos_votos " +
				"WHERE cargo IN(SELECT DISTINCT cargo " +
				"FROM votacao_candidatos_votos " +
				"LIMIT 1)");
		try {
			while(resultado.next())
			{
				count.add( resultado.getString("total") ); 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 
		
		countsStr = (String[]) count.toArray (new String[0]);   // transfere para string
		total = Integer.parseInt(countsStr[0]);
		fechaConexao();
		return total;
	}
	
}
