package br.edu.ifsudestemg.tsi.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.edu.ifsudestemg.tsi.partido.Partido;

/**Gerencia partidos fazendo requisições ao banco de dados
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see Database
 *
 */
public class PartidoBD extends Database {

	/**Construtor default
	 * 
	 */
	public PartidoBD() {
		super();
	}

	/**Insere partido à tabela <code>partido</code> do banco de dados
	 * 
	 * @param partido <code>Partido</code> com o objeto Partido que contém campos do formúlario de cadastro de partido
	 */
	public void inserePartidoBD(Partido partido){
		abreConexao();
		gravar("INSERT INTO partido VALUES('"+partido.getSigla().replace("'", "\\'").toUpperCase()+"','"+partido.getNumero()+"')");
		fechaConexao();
	}
	
	
	/**Remove partido da tabela <code>partido</code> do banco de dados a partir do número do partido
	 * 
	 * @param numero <code>int</code> com o número do partido
	 */
	public void removePartidoBD(int numero){
		abreConexao();
		gravar("DELETE FROM partido WHERE numero = "+numero);
		fechaConexao();
	}
	
	/**Remove partido da tabela <code>partido</code> do banco de dados a partir da sigla do partido
	 * 
	 * @param sigla <code>String</code> com a sigla do partido
	 */
	public void removePartidoBD(String sigla){
		abreConexao();
		gravar("DELETE FROM partido WHERE sigla ILIKE '"+ sigla.replace("'", "\\'")+ "';");
		fechaConexao();
	}
	
	/**Pesquisa partido na tabela <code>partido</code> a partir da sigla e número do partido
	 * 
	 * @param sigla <code>String</code> com a sigla do partido
	 * @param numero <code>int</code> com o número do partido
	 * 
	 * @return Um <code>boolean</code> com o valor se o partido consulta está ou não presente na tabela <code>partido</code> do banco de dados
	 */
	public boolean pesquisaPartido(String sigla,int numero){
		abreConexao();
		if( !consultaSQLGenerica("SELECT count(*) FROM partido WHERE sigla ILIKE '" + 
				sigla.toUpperCase().replace("'", "\\'")+ "' AND numero = "+numero+";").toString().equalsIgnoreCase("[0]")){
			fechaConexao();
			return true;
		}
		fechaConexao();
		return false;
	}
		
	/**Pesquisa partido na tabela <code>partido</code> a partir da sigla do partido
	 * 
	 * @param sigla <code>String</code> com a sigla do partido
	 * 
	 * @return Um <code>boolean</code> com o valor se o partido consulta está ou não presente na tabela <code>partido</code> do banco de dados
	 */
	public boolean pesquisaPartido(String sigla){
		abreConexao();
		if( !consultaSQLGenerica("SELECT count(*) FROM partido WHERE sigla ILIKE '" + 
				sigla.toUpperCase().replace("'", "\\'")+ "';").toString().equalsIgnoreCase("[0]")){
			fechaConexao();
			return true;
		}
		fechaConexao();
		return false;
	}
	
	/**Pesquisa partido na tabela <code>partido</code> a partir do número do partido
	 * 
	 * @param numero <code>int</code> com o número do partido
	 * 
	 * @return Um <code>boolean</code> com o valor se o partido consulta está ou não presente na tabela <code>partido</code> do banco de dados
	 */
	public boolean pesquisaPartido(int numero){
		abreConexao();
		if( !consultaSQLGenerica("SELECT count(*) FROM partido WHERE numero = '" + 
				numero+ "';").toString().equalsIgnoreCase("[0]")){
			fechaConexao();
			return true;
		}
		fechaConexao();
		return false;
	}
	
	/**Verifica se há algum partido registrado
	 * 
	 * @return Um <code>boolean</code> com <code>true</code> caso haja partidos e <code>false</code> caso contrário
	 */
	public boolean existePartido(){
		abreConexao();
		if( !consultaSQLGenerica("SELECT count(*) FROM partido;").toString().equalsIgnoreCase("[0]")){
			fechaConexao();
			return true;
		}
		fechaConexao();
		return false;
	}
	
	/**Consulta partidos na tabela <code>partido</code>
	 * 
	 * @return Um <code>ResultSet</code> com as siglas dos partidos
	 */
	public ResultSet consultaPartido(){
		abreConexao();
		ResultSet resultado = consultaTabela("SELECT sigla FROM partido");
		fechaConexao();
		return resultado;
	}
	
	/**Consulta legenda do partido especificado na tabela <code>partido</code>
	 * 
	 * @param partido <code>String</code> com a sigla do partido
	 * 
	 * @return Um <code>ResultSet</code> com números dos partidos
	 */
	public int consultaLegenda(String partido){
		int legenda = 0;
		List<String> numeros = new ArrayList<String>(); 
		String[] numerosStr;
		abreConexao();
		
		ResultSet resultado = consultaTabela("SELECT numero FROM partido WHERE sigla ILIKE '"+
					partido.replace("'", "\\'").toUpperCase()+"'");
		try {
			while(resultado.next())
			{
				numeros.add( resultado.getString("numero") ); // Adiciona na lista os nomes 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 
		numerosStr = (String[]) numeros.toArray (new String[0]);   // transfere para string
		legenda = Integer.parseInt(numerosStr[0]);
		fechaConexao();
		return legenda;
	}
}
