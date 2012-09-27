package br.edu.ifsudestemg.tsi.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.edu.ifsudestemg.tsi.cargo.Cargo;

/**Gerencia cargos fazendo requisições ao banco de dados
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see Database
 */
public class CargoBD extends Database{

	/**Construtor default
	 * 
	 */
	public CargoBD() {
		super();
	}
	
	/**Insere cargo à tabela <code>cargo</code> do banco de dados
	 * 
	 * @param cargo <code>Cargo</code> com objeto Cargo
	 * 
	 * @see Cargo
	 */
	public void insereCargoBD(Cargo cargo){
		abreConexao();
		gravar("INSERT INTO cargo VALUES('"+cargo.getNome().replace("'", "\\'").toUpperCase()+"','"+cargo.getQtdDigitos()+"')");
		fechaConexao();
	}
	
	/**Remove cargo a partir do nome do cargo
	 * 
	 * @param nome <code>String</code> com nome do cargo
	 */
	public void removeCargoBD(String nome){
		abreConexao();
		gravar("DELETE FROM cargo WHERE nome ILIKE '"+nome.toUpperCase().replace("'", "\\'")+"'");
		fechaConexao();
	}
	
	/**Pesquisa cargo a partir do nome do cargo
	 * 
	 * @param nome <code>String</code> com nome do cargo
	 * 
	 * @return Um <code>boolean</code> com <code>true</code> caso encontre o cargo e <code>false</code> caso contrário
	 */
	public boolean pesquisaPorNome(String nome){
		abreConexao();
		if( !consultaSQLGenerica("SELECT count(*) FROM cargo WHERE nome ILIKE '" + 
				nome.replace("'", "\\'")+ "';").toString().equalsIgnoreCase("[0]")){
			fechaConexao();
			return true;
		}
		fechaConexao();
		return false;
	}
	
	/**Verifica se existe algum cargo cadastrado
	 * 
	 * @return Um <code>boolean</code> com <code>true</code> caso encontre algum cargo e <code>false</code> caso contrário
	 */
	public boolean existeCargo(){
		abreConexao();
		if( !consultaSQLGenerica("SELECT count(*) FROM cargo;").toString().equalsIgnoreCase("[0]")){
			fechaConexao();
			return true;
		}
		fechaConexao();
		return false;
	}
	
	/**Consulta cargos na tabela <code>cargo</code> do banco de dados
	 * 
	 * @return Um <code>ResultSet</code> com os cargos cadastrados
	 */
	public ResultSet consultaCargo(){
		abreConexao();
		ResultSet resultado = consultaTabela("SELECT nome FROM cargo");
		fechaConexao();
		return resultado;
	}
		
	/**Consulta digitos do cargo especificado
	 * 
	 * @param cargo <code>String</code> com nome do cargo
	 * 
	 * @return Um <code>int</code> com a quantidade de dígitos
	 */
	public int consultaDigitos(String cargo){
		int qtdDigitos = 0;
		List<String> digitos = new ArrayList<String>(); 
		String[] digitosStr;
		abreConexao();
		
		ResultSet resultado = consultaTabela("SELECT \"qtdDigitos\" FROM cargo WHERE nome ILIKE '"+
					cargo.replace("'", "\\'").toUpperCase()+"'");
		try {
			while(resultado.next())
			{
				digitos.add( resultado.getString("qtdDigitos") ); // Adiciona na lista os nomes 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 
		
		digitosStr = (String[]) digitos.toArray (new String[0]);   // transfere para string
		qtdDigitos = Integer.parseInt(digitosStr[0]);
		fechaConexao();
		return qtdDigitos;
	}

}
