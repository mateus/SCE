package br.edu.ifsudestemg.tsi.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.JOptionPane;

import br.edu.ifsudestemg.tsi.gui.Interface;
import br.edu.ifsudestemg.tsi.gui.TelaConfiguracoes;
import br.edu.ifsudestemg.tsi.persistencia.ConfiguracoesBD;
import br.edu.ifsudestemg.tsi.persistencia.Database;

/**Tratador de eventos para a tela de configuração
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see MouseAdapter
 * @see ActionListener
 *
 */
public class TratadorEventoConfiguracao extends MouseAdapter implements ActionListener{

	private TelaConfiguracoes gui;
	private Database bd = new Database();
	private ConfiguracoesBD configuracoesBD = new ConfiguracoesBD();

	/** Construtor default
	 * @param gui <code>TelaConfiguracoes</code> com a referência da tela de configurações
	 * 
	 * @see TelaConfiguracoes
	 */
	public TratadorEventoConfiguracao(TelaConfiguracoes gui) {
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent evento){

		//Botao de limpar Banco de Dados
		if(evento.getSource() == gui.getLimparBaseDados()){
			int op;
			op = JOptionPane.showConfirmDialog(null, "Atenção: Deseja deletar todos os registros armazenados \nno " +
					"bando de dados ?" , "Atenção" , JOptionPane.YES_NO_OPTION);

			//Caso tenha clicado em SIM
			if(op != 1){	
				try{
					bd.abreConexao();
					bd.gravar("ALTER SEQUENCE seq_pesquisa RESTART WITH 1");
					bd.gravar("TRUNCATE TABLE candidato_pesquisa CASCADE");
					bd.gravar("TRUNCATE TABLE pesquisa CASCADE");
					bd.gravar("TRUNCATE TABLE candidato CASCADE");
					bd.gravar("TRUNCATE TABLE cargo CASCADE");
					bd.gravar("TRUNCATE TABLE partido CASCADE");
					bd.gravar("TRUNCATE TABLE votacao_cargos CASCADE");
					bd.gravar("TRUNCATE TABLE votacao_candidatos_votos CASCADE");
					bd.fechaConexao();
					JOptionPane.showMessageDialog(null, "Banco de Dados apagado com sucesso.","Sucesso",
							JOptionPane.INFORMATION_MESSAGE);
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro ao apagar o Banco de Dados.","Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		//Aplica alterações do horario do sistema
		if(evento.getSource() == gui.getAplicar()){
			if(gui.getAtivarHoraSistemaCB().getItemAt(gui.getAtivarHoraSistemaCB().getSelectedIndex()).equals("Ativado")){
				Interface.horarioAtivado = true;
				configuracoesBD.alteraStatus(true);
			}else{
				Interface.horarioAtivado = false;
				configuracoesBD.alteraStatus(false);
			}
		}
		
	}

}
