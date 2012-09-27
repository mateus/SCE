package br.edu.ifsudestemg.tsi.partido;

import br.edu.ifsudestemg.tsi.persistencia.PartidoBD;

/**Manipula dados referente aos partidos
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see PartidoBD
 *
 */
public class Partido extends PartidoBD {

	private String sigla;
	private int numero;
	
	/**Construtor default
	 * 
	 */
	public Partido() {
		super();
	}

	/**Construtor Sobrecarregado passando todos os parametros para o partido
	 * 
	 * @param sigla <code>String</code> com sigla do partido
	 * @param numero <code>int</code> com número de legenda do partido
	 */
	public Partido(String sigla, int numero) {
		super();
		this.sigla = sigla;
		this.numero = numero;
	}

	/**Retorna <code>String</code> sigla do partido
	 * 
	 * @return Uma <code>String</code> com a sigla do partido
	 */
	public String getSigla() {
		return sigla;
	}

	/**Adiciona sigla ao partido
	 * 
	 * @param sigla <code>String</code> com o sigla do partido
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**Retorna <code>String</code> número do partido
	 * 
	 * @return Um <code>int</code> com o número do partido
	 */
	public int getNumero() {
		return numero;
	}

	/**Adiciona sigla ao partido
	 * 
	 * @param numero <code>int</code> com o número do partido
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
}
