package br.edu.ifsudestemg.tsi.persistencia;

/**Manipula configurações gerais do programa
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 */
public class ConfiguracoesBD extends Database{

	/**Construtor default
	 * 
	 */
	public ConfiguracoesBD() {
		super();
	}

	/**Verifica se horário para votação está ativado
	 * 
	 * @return Um <code>boolean</code> com <code>true</code> caso horário esteja ativado e <code>false</code> caso contrário
	 */
	public boolean statusHoraAtivado(){
		abreConexao();
		if( consultaSQLGenerica("SELECT horarioativo FROM configuracoes").toString().equalsIgnoreCase("[true]")){
			fechaConexao();
			return true;
		}
		fechaConexao();
		return false;
	}
	
	/**Altera status de horário para votação
	 * 
	 * @param status <code>boolean</code> com status de horário de votação
	 */
	public void alteraStatus(boolean status){
		abreConexao();
		gravar("UPDATE configuracoes SET horarioativo = "+status+" WHERE horarioativo = "+!status);
		fechaConexao();
	}
}
