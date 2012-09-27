package br.edu.ifsudestemg.tsi.persistencia;

import java.sql.ResultSet;

import br.edu.ifsudestemg.tsi.candidato.Candidato;

/**Gerencia candidatos fazendo requisições ao banco de dados
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 */
public class CandidatoBD extends Database {

	/**Construtor default
	 * 
	 */
	public CandidatoBD() {
		super();
	}
	
	/**Insere candidato à tabela <code>candidato</code> do banco de dados
	 * 
	 * @param candidato <code>Candidato</code> com um objeto Candidato
	 * 
	 * @see Candidato
	 */
	public void insereBD(Candidato candidato){
		abreConexao();
		gravar("INSERT INTO candidato VALUES('"+candidato.getNome().replace("'", "\\'")+"',"+"'"+candidato.getPartido()+"',"+candidato.getNumero()+",'"
		+candidato.getCargo()+"','"+candidato.getDiretorioImagem()+"')");
		fechaConexao();
	}
	
	//UPDATE candidato SET nome='TESTE' WHERE numero = 13 
	/**Altera número e cargo de candidato já cadastrado
	 * 
	 * @param candidato <code>Candidato</code> com um objeto Candidato
	 * @param numeroAntigo <code>String</code> com número antigo do candidato
	 * @param cargoAntigo <code>String</code> com cargo antigo do candidato
	 */
	public void alteraBD(Candidato candidato,int numeroAntigo,String cargoAntigo){
		abreConexao();
		gravar("UPDATE candidato SET nome='"+candidato.getNome().replace("'", "\\'")+"'" +
				",partido='"+candidato.getPartido()+"',cargo='"+candidato.getCargo()+"',numero='"+candidato.getNumero()+
				"',diretorio='"+candidato.getDiretorioImagem()+"' " +
						"WHERE numero = "+numeroAntigo+" " +
								"AND cargo ILIKE '"+cargoAntigo+"'");
		fechaConexao();
	}
	
	/**Remove candidato da tabela <code>candidato</code> do banco de dados
	 * 
	 * @param numero <code>String</code> com número do candidato
	 * @param cargo <code>String</code> com cargo do candidato
	 */
	public void removeBD(String numero,String cargo){
		abreConexao();
		gravar("DELETE FROM candidato WHERE numero = "+numero+" AND cargo ILIKE '"+cargo+"'");
		fechaConexao();
	}
	
	/**Pesquisa candidato a partir do número especificado
	 * 
	 * @param numero <code>String</code> com número do candidato
	 * 
	 * @return Um <code>boolean</code> com <code>true</code> caso encontre candidato e <code>false</code> caso contrário
	 */
	public boolean pesquisaPorNumero(String numero){
		abreConexao();
		if( !consultaSQLGenerica("SELECT count(*) FROM candidato WHERE numero = '" + 
				numero+ "';").toString().equalsIgnoreCase("[0]")){
			fechaConexao();
			return true;
		}
		fechaConexao();
		return false;
	}
	
	/**Pesquisa candidato a partir do número e cargo especificados
	 * 
	 * @param numero <code>String</code> com número do candidato
	 * @param cargo <code>String</code> com cargo do candidato
	 * @return Um <code>boolean</code> com <code>true</code> caso encontre candidato e <code>false</code> caso contrário
	 */
	public boolean pesquisaPorNumeroCargo(String numero,String cargo){
		abreConexao();
		if( !consultaSQLGenerica("SELECT count(*) FROM candidato WHERE numero = '" + 
				numero+ "' AND cargo ILIKE '"+cargo+"';").toString().equalsIgnoreCase("[0]")){
			fechaConexao();
			return true;
		}
		fechaConexao();
		return false;
	}
	
	/**Consulta candidato a partir do nome
	 * 
	 * @param nome <code>String</code> com nome do candidato
	 * 
	 * @return Um <code>ResultSet</code> com candidatos que tenham o nome pesquisado
	 */
	public ResultSet consultaCandidatoNome(String nome){
		abreConexao();
		ResultSet resultado = consultaTabela("SELECT * FROM candidato WHERE nome ILIKE '%"+nome+"%' ORDER BY nome");
		fechaConexao();
		return resultado;
	}
	
	/**Consulta candidato a partir do número
	 * 
	 * @param numero <code>String</code> com número do candidato
	 * 
	 * @return Um <code>ResultSet</code> com candidatos que tenham o nome pesquisado
	 */
	public ResultSet consultaCandidatoNumero(String numero){
		abreConexao();
		ResultSet resultado = consultaTabela("SELECT * FROM candidato WHERE numero = "+numero);
		fechaConexao();
		return resultado;
	}
	
	/**Consulta candidato a partir do cargo
	 * 
	 * @param cargo <code>String</code> com cargo do candidato
	 * 
	 * @return Um <code>ResultSet</code> com candidatos que tenham o nome pesquisado
	 */
	public ResultSet consultaCandidatoCargo(String cargo){
		abreConexao();
		ResultSet resultado = consultaTabela("SELECT * FROM candidato WHERE cargo LIKE '"+cargo+"'");
		fechaConexao();
		return resultado;
	}
	
	/**Pesquisa imagem do candidato a partir de seu número
	 * 
	 * @param numero <code>String</code> com número do candidato
	 * 
	 * @return Um <code>ResultSet</code> com o caminho da imagem do candidato
	 */
	public ResultSet pesquisaImagemPeloNumero(String numero){
		abreConexao();
		ResultSet resultado = consultaTabela("SELECT diretorio FROM candidato WHERE numero = "+numero);
		fechaConexao();
		return resultado;
	}
	
}
