package br.edu.ifsudestemg.tsi.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import br.edu.ifsudestemg.tsi.gui.TelaExcluirCargo;
import br.edu.ifsudestemg.tsi.persistencia.CargoBD;

/**Tratador de eventos para a tela de excluir cargo
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see ActionListener
 * @see KeyAdapter
 *
 */
public class TratadorEventoExcluirCargo extends KeyAdapter implements ActionListener {

	private TelaExcluirCargo gui;
	private CargoBD cargoBD = new CargoBD();

	/** Construtor default
	 * @param gui <code>TelaExcluirCargo</code> com a referência da tela de excluir cargo
	 * 
	 * @see TelaExcluirCargo
	 */
	public TratadorEventoExcluirCargo(TelaExcluirCargo gui){
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent evento) {

		if(evento.getSource() == gui.getExcluir()){
			if( !cargoBD.pesquisaPorNome(gui.getNomeCargoField().getText().toUpperCase()) )
			{
				JOptionPane.showMessageDialog(null, "Cargo não cadastrado","Erro",
						JOptionPane.ERROR_MESSAGE);
			}
			//Realiza gravação dos dados no banco de dados
			else  
			{
				int op;
				op = JOptionPane.showConfirmDialog(null, "Atenção: Caso haja candidatos cadastrados com este " +
						"\ncargo o seu registro será excluido. Tem certeza que deseja \nexcluir este cargo ?" , 
						"Atenção" , JOptionPane.YES_NO_OPTION);

				//Caso tenha clicado em SIM
				if(op != 1)
					cargoBD.removeCargoBD(gui.getNomeCargoField().getText());
				gui.dispose();
			}
		}

		//Botão limpar
		if(evento.getSource() == gui.getLimpar()){
			gui.getNomeCargoField().setText("");
			gui.getExcluir().setEnabled(false);
		}
	}

	//VALIDA CAMPOS PARA LIBERAR O BOTAO CADASTRAR----------
	public void keyReleased(KeyEvent evento) {
		if(!gui.getNomeCargoField().getText().isEmpty()){
			gui.getExcluir().setEnabled(true);
		}
		else
			gui.getExcluir().setEnabled(false);
	}


}
