package br.edu.ifsudestemg.tsi.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.edu.ifsudestemg.tsi.pesquisa.Pesquisa;
import br.edu.ifsudestemg.tsi.pesquisa.Pesquisa.CandidatoPesquisa;

/** Gerencia pesquisa eleitoral fazendo requisições ao banco de dados
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see Database
 */
public class PesquisaBD extends Database {

	/**Construtor default
	 * 
	 */
	public PesquisaBD() {
		super();
	}

	/**Insere pesquisa eleitoral cadastrada à tabela <code>pesquisa</code> do banco de dados
	 * 
	 * @param pesquisa <code>Pesquisa</code> com um objeto Pesquisa que contém campos do formúlario de cadastro de pesquisa eleitoral
	 * 
	 * @see Pesquisa
	 */
	public void insereBD(Pesquisa pesquisa){
		abreConexao();
		gravar("INSERT INTO pesquisa VALUES( " +
				"NEXTVAL('seq_pesquisa'),'"+pesquisa.getCargo()+"','"+pesquisa.getDataInicio()+"'," +
						"'"+pesquisa.getDataFim()+"',"+pesquisa.getBrancosNulos()+"," +
								pesquisa.getNaoSouberam()+","+pesquisa.getEntrevistados()+"," +
									pesquisa.getMunicipios()+" )");
		fechaConexao();
	}
	
	/**Insere candidato com sua intenção de voto à tabela <code>candidato_pesquisa</code> do banco de dados
	 * 
	 * @param candPesquisa <code>CandidatoPesquisa</code> com um objeto CandidatoPesquisa que contém campos do candidato associado à pesquisa eleioral cadastrada
	 * @param sequencia <code>int</code> com numero de sequencia para inserção no banco de dados
	 * 
	 * @see CandidatoPesquisa
	 */
	public void insereCandidatoPesquisaBD(CandidatoPesquisa candPesquisa,int sequencia){
		abreConexao();
		gravar("INSERT INTO candidato_pesquisa VALUES ( "+sequencia+"," +
				candPesquisa.getNumero()+","+candPesquisa.getIntecaoVoto()+")");
		fechaConexao();
	}
	
	/**Consulta no banco de dados se já foi cadastrada pesquisa com a mesma data e mesmo cargo
	 * 
	 * @param cargo <code>String</code> com o cargo do candidato
	 * @param dataInicio <code>String</code> com data de inicio da pesquisa
	 * @param dataFim <code>String</code> com data de fim da pesquisa
	 * @return Um <code>boolean</code> com <code>true</code> se houver pesquisa cadastrada com os mesmos parâmetros
	 */
	public boolean pesquisaPesquisa(String cargo,String dataInicio,String dataFim){
		abreConexao();
		if( !consultaSQLGenerica("SELECT count(*) FROM pesquisa WHERE cargo " +
				"ILIKE '"+cargo+"' AND " +
				"data_inicio = '"+dataInicio+"' " +
				"AND data_fim = '"+dataFim+"';").toString().equalsIgnoreCase("[0]")){
			fechaConexao();
			return true;
		}
		fechaConexao();
		return false;
	}
	
	/**Consulta pesquisas entre as datas indicadas nos parâmetros desta função
	 * 
	 * @param cargo <code>String</code> com o cargo do candidato
	 * @param dataInicio <code>String</code> com data de inicio da pesquisa
	 * @param dataFim <code>String</code> com data de fim da pesquisa
	 * 
	 * @return Um <code>ResultSet</code> com id's de pesquisas
	 * 
	 * @see ResultSet
	 */
	public ResultSet consultaEntreDatas(String cargo,String dataInicio,String dataFim){
		abreConexao();
		ResultSet resultado = consultaTabela(
				"SELECT id FROM pesquisa WHERE cargo ILIKE '"+cargo+"' " +
						"AND data_fim BETWEEN '"+dataInicio+"' AND '"+dataFim+"'" +
						" AND data_inicio BETWEEN '"+dataInicio+"' AND '"+dataFim+"' ORDER BY data_inicio");
		fechaConexao();
		return resultado;
	}
	
	/*
	 SELECT EXTRACT( MONTH FROM data_inicio) AS mes, EXTRACT( YEAR FROM data_inicio) AS ano 
	 FROM pesquisa 
	 WHERE cargo ILIKE 'PRESIDENTE' 
	 AND data_fim BETWEEN '02/12/2011' AND '21/06/2012' 
	 AND data_inicio BETWEEN '02/12/2011' AND '21/06/2012' 
	 ORDER BY data_inicio;
    */
	
	/**Consulta pesquisas entre as datas indicadas nos parâmetros desta função
	 * 
	 * @param cargo <code>String</code> com o cargo do candidato
	 * @param dataInicio <code>String</code> com data de inicio da pesquisa
	 * @param dataFim <code>String</code> com data de fim da pesquisa
	 * 
	 * @return Um <code>ResultSet</code> com mes e ano de pesquisas eleitorais
	 */
	public ResultSet consultaDataInicioEntreDatas(String cargo,String dataInicio,String dataFim){
		abreConexao();
		ResultSet resultado = consultaTabela(
				"SELECT EXTRACT( MONTH FROM data_inicio) AS mes, EXTRACT( YEAR FROM data_inicio) AS ano " +
				"FROM pesquisa WHERE cargo ILIKE '"+cargo+"' " +
				"AND data_fim BETWEEN '"+dataInicio+"' AND '"+dataFim+"'" +
				" AND data_inicio BETWEEN '"+dataInicio+"' AND '"+dataFim+"' " +
				"ORDER BY data_inicio");
		fechaConexao();
		return resultado;
	}
	
	/**Consulta Entrevistados e Municípios entre as datas indicadas nos parâmetros desta função
	 * 
	 * @param cargo <code>String</code> com o cargo do candidato
	 * @param dataInicio <code>String</code> com data de inicio da pesquisa
	 * @param dataFim <code>String</code> com data de fim da pesquisa
	 * 
	 * @return Um <code>ResultSet</code> com mes e ano de pesquisas eleitorais
	 */
	public ResultSet consultaEntrMunEntreDatas(String cargo,String dataInicio,String dataFim){
		abreConexao();
		ResultSet resultado = consultaTabela(
				"SELECT entrevistadas AS entr, municipios AS mun " +
				"FROM pesquisa WHERE cargo ILIKE '"+cargo+"' " +
				"AND data_fim BETWEEN '"+dataInicio+"' AND '"+dataFim+"'" +
				" AND data_inicio BETWEEN '"+dataInicio+"' AND '"+dataFim+"' " +
				"ORDER BY data_inicio");
		fechaConexao();
		return resultado;
	}
	
	/**Consulta candidato e sua intenção de votos para a exibição do gráfico de pesquisa eleitoral
	 * 
	 * @param idpesquisa <code>String</code> com o id de pesquisa eleitoral
	 * 
	 * @return Um <code>ResultSet</code> com nome e intenção de votos de cada candidato
	 */
	public ResultSet consultaCandidatoPesquisa(String idpesquisa){
		abreConexao();
		ResultSet resultado = consultaTabela("SELECT nome,intencaovoto FROM candidato_pesquisa INNER JOIN " +
				"candidato ON candidato.numero=candidato_pesquisa.numero WHERE idpesquisa = "+idpesquisa);
		fechaConexao();
		return resultado;
	}

	/**Consulta quantidade de votos brancos, nulos e indecisos da tabela <code>pesquisa</code> do banco de dados
	 * 
	 * @return Um <code>ResultSet</code> com quantidade de votos brancos, nulos e indecisos
	 */
	public ResultSet consultaBrancosNulosIndecisos(){
		abreConexao();
		ResultSet resultado = consultaTabela("SELECT nulos_brancos,indecisos FROM pesquisa ORDER BY data_inicio");
		fechaConexao();
		return resultado;
	}
	
	/**Consulta sequencia na tabela <code>pesquisa</code> do banco de dados
	 * 
	 * @return Um <code>int</code> com número da sequencia
	 */
	public int consultaSequencia(){
		int sequencia = 0;
		List<String> seqs = new ArrayList<String>(); 
		String[] seqStr;
		abreConexao();
		
		ResultSet resultado = consultaTabela("SELECT count(*) AS sequencia FROM pesquisa");
		try {
			while(resultado.next())
			{
				seqs.add( resultado.getString("sequencia") ); // Adiciona na lista os nomes 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 
		seqStr = (String[]) seqs.toArray (new String[0]);   // transfere para string
		sequencia = Integer.parseInt(seqStr[0]);
		fechaConexao();
		return sequencia;
	}
	
}
