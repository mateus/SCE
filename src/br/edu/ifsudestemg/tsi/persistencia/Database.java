package br.edu.ifsudestemg.tsi.persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsudestemg.tsi.classes.Arquivo;

/**Gerencia conexão com banco de dados e implementa uma base para operações SQL
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 */
public class Database 
{
	//private Statement instrucaoSQL; // Instrucao SQL de consulta ao banco de dados.
	private Connection conn;
	private Statement instrucaoSQL;
	private ResultSet rs;
	private PreparedStatement  pStmt;
	private static final String JDBC_DRIVER = "org.postgresql.Driver";

	/**Abre uma conexão com o banco de dados
	 * @throws FileNotFoundException caso não encontre o arquivo
	 * @throws IOException caso erro de entra ou saida dos dados do arquivo
	 * 
	 */
	public void abreConexao()
	{
		try {
			Class.forName( JDBC_DRIVER ).newInstance();
			ArrayList<String> password = null;
			try {
				password = Arquivo.carregar("bdconfig.txt");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(!DriverManager.getConnection(
					"jdbc:postgresql://" + 
							password.get(0).substring(password.get(0).indexOf(":")+1, password.get(0).length()).trim() + 
							"/" + password.get(2).substring(password.get(2).indexOf(":")+1, password.get(2).length()).trim(),
							password.get(1).substring(password.get(1).indexOf(":")+1, password.get(1).length()).trim(), 
							password.get(3).substring(password.get(3).indexOf(":")+1, password.get(3).length()).trim()
					).isClosed())
			{
				conn = DriverManager.getConnection(
						"jdbc:postgresql://" + 
								password.get(0).substring(password.get(0).indexOf(":")+1, password.get(0).length()).trim() + 
								"/" + password.get(2).substring(password.get(2).indexOf(":")+1, password.get(2).length()).trim(),
								password.get(1).substring(password.get(1).indexOf(":")+1, password.get(1).length()).trim(), 
								password.get(3).substring(password.get(3).indexOf(":")+1, password.get(3).length()).trim());
			}

			instrucaoSQL  = conn.createStatement();
			System.out.print("\nCONEXÃO REALIZADA"); // Mensagem de LOG

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (InstantiationException e){

		} catch (IllegalAccessException e){

		}		
	}	

	/**Consulta genérica em tabelas do banco de dados
	 * 
	 * @param pSql <code>String</code> com query desejada
	 * 
	 * @return Um <code>ResultSet</code> com resultado da pesquisa especificada
	 */
	public ResultSet consultaTabela(String pSql)
	{
		System.out.print("\n"+pSql);
		try {
			rs = instrucaoSQL.executeQuery(pSql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/** Inserção genérica em tabelas do banco de dados
	 * 
	 * @param pSql <code>String</code> com query desejada
	 * 
	 * @return Um <code>PreparedStatement</code> com resultado da query especificada
	 */
	public PreparedStatement insereNaTabela(String pSql)
	{
		try {
			pStmt = conn.prepareStatement(pSql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pStmt;
	}

	/** Executa consultas no banco de dados a partir de campos tabela, chave e valor
	 * 
	 * @param tabela <code>String</code> com o nome da tabela a ser consultada
	 * @param chave <code>String</code> com a chave(campo) a ser consultada
	 * @param valor <code>String</code> com o valor a ser consultado
	 * 
	 * @return Um {@code List<Object>} com o resultado da consulta SQL
	 */
	public List<Object> consultaSQL(String tabela, String chave, String valor) {
		List<Object> tupla = new ArrayList<Object>();
		String consulta;
		int numeroColunasConsulta;
		ResultSet resultadoConsulta;
		ResultSetMetaData metadadosConsulta;

		// Cria a consulta.
		consulta = "SELECT * FROM " + tabela + " WHERE " + chave + " = '" + valor + "';";

		try { // Submete a consulta ao banco de dados.
			resultadoConsulta = instrucaoSQL.executeQuery(consulta);

			// Obtem os metadados que descrevem o resultado da consulta.
			metadadosConsulta = resultadoConsulta.getMetaData();

			// Obtem o numero de colunas da consulta.
			numeroColunasConsulta = metadadosConsulta.getColumnCount();

			// Obtem os valores da tupla da tabela resultante da consulta.
			while (resultadoConsulta.next()) {
				// Obtem os campos os campos da tupla.
				for (int c = 1; c <= numeroColunasConsulta; c++) {
					tupla.add(resultadoConsulta.getObject(c));
				}
			}

		} catch (SQLException e) {
			//ControleEstoque.finalizarPrograma();
			System.out.print("Erro.");
		}
		return tupla;
	} 

	/**Executa consulta apenas passando o comando a ser executado
	 * 
	 * @param comando <code>String</code> com o comando a ser executado
	 * 
	 * @return Um {@code List<Object>} com o resultado da consulta
	 */
	public List<Object> consultaSQLGenerica(String comando) {

		List<Object> tupla = new ArrayList<Object>();
		String consulta;
		int numeroColunasConsulta;
		ResultSet resultadoConsulta;
		ResultSetMetaData metadadosConsulta;

		// Cria a consulta.
		consulta = comando;

		try { // Submete a consulta ao banco de dados.
			resultadoConsulta = instrucaoSQL.executeQuery(consulta);

			// Obtem os metadados que descrevem o resultado da consulta.
			metadadosConsulta = resultadoConsulta.getMetaData();

			// Obtem o numero de colunas da consulta.
			numeroColunasConsulta = metadadosConsulta.getColumnCount();

			System.out.print("\n"+comando);// Mensagem de LOG
			// Obtem os valores da tupla da tabela resultante da consulta.
			while (resultadoConsulta.next()) {
				// Obtem os campos os campos da tupla.
				for (int c = 1; c <= numeroColunasConsulta; c++) {
					tupla.add(resultadoConsulta.getObject(c));
				}
			}

		} catch (SQLException e) {
			//ControleEstoque.finalizarPrograma();
		}
		return tupla;
	}

	/**Grava dados na tabela e atualiza os dados
	 * 
	 * @param instrucao <code>String</code> com instrução a ser executada no banco de dados
	 */
	public void gravar(String instrucao) {
		try {
			System.out.print("\n"+instrucao);// Mensagem de LOG		

			//Executa query
			instrucaoSQL.executeUpdate(instrucao);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 

	/**Fecha a conexão estabelecida anteriormente com o banco de dados
	 * 
	 */
	public void fechaConexao()
	{
		try {
			conn.close();
			System.out.print("\nCONEXÃO FECHADA");// Mensagem de LOG
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
