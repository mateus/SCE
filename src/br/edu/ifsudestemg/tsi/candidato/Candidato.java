package br.edu.ifsudestemg.tsi.candidato;

import br.edu.ifsudestemg.tsi.persistencia.CandidatoBD;

/**
 * Manipula dados referentes a candidatos
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see CandidatoBD
 */
public class Candidato extends CandidatoBD{

	private String nome;
	private String partido;
	private String numero;
	private String cargo;
	private String diretorioImagem;
	
	/**
	 * Construtor Default 
	 */
	public Candidato() {
		super();
	}

	/**
	 * Construtor Sobrecarregado passando todos os parametros para o candidato
	 * 
	 * @param nome <code>String</code> com nome do candidato
	 * @param partido <code>String</code> com partido do candidato
	 * @param numero <code>String</code> com número do candidato
	 * @param cargo <code>String</code> com cargo do candidato
	 * @param diretorioImagem <code>String</code> com caminho da imagem no sistema 
	 */
	public Candidato(String nome, String partido, String numero, String cargo,
			String diretorioImagem) {
		super();
		this.nome = nome;
		this.partido = partido;
		this.numero = numero;
		this.cargo = cargo;
		this.diretorioImagem = diretorioImagem;
	}
	
	/**
	 * Retorna <code>String</code> nome do candidato
	 * 
	 * @return Uma <code>String</code> com o nome do candidato
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Adiciona nome ao candidato
	 * 
	 * @param nome <code>String</code> com o nome do candidato
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorna partido do candidato
	 * 
	 * @return Uma <code>String</code> com o partido do candidato
	 */
	public String getPartido() {
		return partido;
	}

	/**
	 * Adiciona partido ao candidato
	 * 
	 * @param partido <code>String</code> com o partido do candidato
	 */
	public void setPartido(String partido) {
		this.partido = partido;
	}

	/**
	 * Retorna cargo do candidato
	 * 
	 * @return Uma <code>String</code> com o cargo do candidato
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * Adiciona cargo ao candidato
	 * 
	 * @param cargo <code>String</code> com o cargo do candidato
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * Retorna número do candidato
	 * 
	 * @return  Uma <code>String</code> com o número do candidato
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * Adiciona número ao candidato
	 * 
	 * @param numero <code>String</code> com o número do candidato
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	/**
	 * Retorna o caminho onde está localizada a imagem do Candidato
	 * 
	 * @return Uma <code>String</code> com o caminho para a imagem do candidato
	 */
	public String getDiretorioImagem() {
		return diretorioImagem;
	}

	/**
	 * Adiciona o caminho da imagem do Candidato
	 * 
	 * @param diretorioImagem <code>String</code> com o caminho da imagem no sistema
	 */
	public void setDiretorioImagem(String diretorioImagem) {
		this.diretorioImagem = diretorioImagem;
	}	
}
