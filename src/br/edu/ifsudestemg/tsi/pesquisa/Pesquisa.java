package br.edu.ifsudestemg.tsi.pesquisa;

import java.util.List;

import br.edu.ifsudestemg.tsi.persistencia.PesquisaBD;

/** Gerencia cadastro de pesquisas eleitoral
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see PesquisaBD
 */
public class Pesquisa extends PesquisaBD{

	private String idPesquisa;
	private String cargo;
	private String dataInicio;
	private String dataFim;
	private String brancosNulos;
	private String naoSouberam;
	private String municipios;
	private String entrevistados;
		
	/** Construtor default
	 * 
	 */
	public Pesquisa() {
		super();
	}

	/** Construtor Sobrecarregado passando todos os parâmetros para pesquisa
	 * 
	 * @param idPesquisa <code>String</code> com id da pesquisa
	 * @param cargo <code>String</code> com o cargo do candidato
	 * @param dataInicio <code>String</code> com data de inicio da pesquisa
	 * @param dataFim <code>String</code> com data de fim da pesquisa
	 * @param brancosNulos <code>String</code> com total de votos brancos e nulos da pesquisa
	 * @param naoSouberam <code>String</code> com total de votos indecisos da pesquisa
	 * @param municipios <code>String</code> com quantidade de municipios
	 * @param entrevistados <code>String</code> com quantidade de entrevistados
	 * @param intecoesVoto {@code List<CandidatoPesquisa>} com intenções de voto para cada candidato
	 * 
	 * @see CandidatoPesquisa
	 */
	public Pesquisa(String idPesquisa, String cargo, String dataInicio,
			String dataFim, String brancosNulos, String naoSouberam,
			String municipios, String entrevistados,
			List<CandidatoPesquisa> intecoesVoto) {
		super();
		this.idPesquisa = idPesquisa;
		this.cargo = cargo;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.brancosNulos = brancosNulos;
		this.naoSouberam = naoSouberam;
		this.municipios = municipios;
		this.entrevistados = entrevistados;

	}
	
	/**
	 * Retorna <code>String</code> id da pesquisa
	 * 
	 * @return Uma <code>String</code> com o id da pesquisa
	 */
	public String getIdPesquisa() {
		return idPesquisa;
	}

	/**
	 * Adiciona id a pesquisa
	 * 
	 * @param idPesquisa <code>String</code> com o id da pesquisa
	 */
	public void setIdPesquisa(String idPesquisa) {
		this.idPesquisa = idPesquisa;
	}
	
	/**
	 * Retorna <code>String</code> cargo do candidato
	 * 
	 * @return Uma <code>String</code> com o cargo do candidato
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * Adiciona cargo do candidato
	 * 
	 * @param cargo <code>String</code> com o cargo do candidato
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	/**
	 * Retorna <code>String</code> data de inicio da pesquisa
	 * 
	 * @return Uma <code>String</code> com a data de inicio da pesquisa
	 */
	public String getDataInicio() {
		return dataInicio;
	}

	/**
	 * Adiciona data de inicio da pesquisa
	 * 
	 * @param dataInicio <code>String</code> com a data de inicio da pesquisa
	 */
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * Retorna <code>String</code> data de término da pesquisa
	 * 
	 * @return Uma <code>String</code> com a data de término da pesquisa
	 */
	public String getDataFim() {
		return dataFim;
	}

	/**
	 * Adiciona data de término da pesquisa
	 * 
	 * @param dataFim <code>String</code> com a data de término da pesquisa
	 */
	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * Retorna <code>String</code> quantidade de votos brancos e nulos
	 * 
	 * @return Uma <code>String</code> com a quantidade de votos brancos e nulos
	 */
	public String getBrancosNulos() {
		return brancosNulos;
	}

	/**
	 * Adiciona quantidade de votos brancos e nulos
	 * 
	 * @param brancosNulos <code>String</code> com a quantidade de votos brancos e nulos
	 */
	public void setBrancosNulos(String brancosNulos) {
		this.brancosNulos = brancosNulos;
	}

	/**
	 * Retorna <code>String</code> quantidade de votos indecisos
	 * 
	 * @return Uma <code>String</code> com a quantidade de votos indecisos
	 */
	public String getNaoSouberam() {
		return naoSouberam;
	}

	/**
	 * Adiciona quantidade de votos indecisos
	 * 
	 * @param naoSouberam <code>String</code> com a quantidade de votos indecisos
	 */
	public void setNaoSouberam(String naoSouberam) {
		this.naoSouberam = naoSouberam;
	}

	/**
	 * Retorna <code>String</code> quantidade de municípios
	 * 
	 * @return Uma <code>String</code> com a quantidade de municípios
	 */
	public String getMunicipios() {
		return municipios;
	}

	/**
	 * Adiciona quantidade de municípios
	 * 
	 * @param municipios <code>String</code> com a quantidade de municípios
	 */
	public void setMunicipios(String municipios) {
		this.municipios = municipios;
	}

	/**
	 * Retorna <code>String</code> quantidade de entrevistados
	 * 
	 * @return Uma <code>String</code> com a quantidade de entrevistados
	 */
	public String getEntrevistados() {
		return entrevistados;
	}

	/**
	 * Adiciona quantidade de entrevistados
	 * 
	 * @param entrevistados <code>String</code> com a quantidade de entrevistados
	 */
	public void setEntrevistados(String entrevistados) {
		this.entrevistados = entrevistados;
	}

	//Classe para cada candidato contendo o número dele e sua inteção de voto
	/** Mantém o número de cada candidato e sua intenção de voto
	 * 
	 * @author Mateus Ferreira
	 * @author Vinicius Lehmann
	 *
	 */
	public static class CandidatoPesquisa{
		
		private String numero;
		private String intecaoVoto;
		
		/** Construtor default
		 * 
		 */
		public CandidatoPesquisa() {
			super();
		}

		/**Construtor Sobrecarregado passando todos os parâmetros para pesquisa de candidato
		 * 
		 * @param numero <code>String</code> com o número do candidato
		 * @param intecaoVoto <code>String</code> com sua intenção de voto
		 */
		public CandidatoPesquisa(String numero, String intecaoVoto) {
			super();
			this.numero = numero;
			this.intecaoVoto = intecaoVoto;
		}

		/**
		 * Retorna <code>String</code> número do candidato
		 * 
		 * @return Uma <code>String</code> com o número do candidato
		 */
		public String getNumero() {
			return numero;
		}

		/**
		 * Adiciona número do candidato
		 * 
		 * @param numero <code>String</code> com o numero do candidato
		 */
		public void setNumero(String numero) {
			this.numero = numero;
		}

		/**
		 * Retorna <code>String</code> intenção de voto do candidato
		 * 
		 * @return Uma <code>String</code> com a intenção de voto do candidato
		 */
		public String getIntecaoVoto() {
			return intecaoVoto;
		}

		/**
		 * Adiciona intenção de voto do candidato
		 * 
		 * @param intecaoVoto <code>String</code> com a intenção de voto do candidato
		 */
		public void setIntecaoVoto(String intecaoVoto) {
			this.intecaoVoto = intecaoVoto;
		}
	}
}
