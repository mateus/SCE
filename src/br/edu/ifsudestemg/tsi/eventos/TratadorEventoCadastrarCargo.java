package br.edu.ifsudestemg.tsi.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import br.edu.ifsudestemg.tsi.cargo.Cargo;
import br.edu.ifsudestemg.tsi.gui.TelaCadastrarCargo;

/**Tratador de eventos para a tela de cadastro de cargos
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see KeyAdapter
 * @see ActionListener
 *
 */
public class TratadorEventoCadastrarCargo extends KeyAdapter implements ActionListener{

	TelaCadastrarCargo gui;
	Cargo cargo = new Cargo();
	
	/** Construtor default
	 * @param gui <code>TelaCadastrarCargo</code> com a referência da tela de cadastro de cargos
	 * 
	 * @see TelaCadastrarCargo
	 */
	public TratadorEventoCadastrarCargo(TelaCadastrarCargo gui) {
		super();
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent evento) {
		if( evento.getSource() == gui.getCadastrar()){
			cargo.setNome(gui.getNomeCargoField().getText().trim());
			cargo.setQtdDigitos(Integer.parseInt( gui.getQtdDigitosField().getText() ));
			
			//valida unicidade da chave NUMERO 
			if( cargo.getNome().isEmpty()){
				JOptionPane.showMessageDialog(null, "O campo nome deve conter pelomenos 1 caracter","Erro",
						JOptionPane.ERROR_MESSAGE);
			}else
			if( cargo.pesquisaPorNome(cargo.getNome()) )
			{
				JOptionPane.showMessageDialog(null, "Cargo já cadastrado","Erro",
						JOptionPane.ERROR_MESSAGE);
			}
			//Realiza gravação dos dados no banco de dados
			else  
			{
				cargo.insereCargoBD(cargo);
				gui.dispose();
			}
		}
		
		//Apaga todos os campos
		if(evento.getSource() == gui.getLimpar()){
			gui.getNomeCargoField().setText("");
			gui.getQtdDigitosField().setText("");
			gui.getCadastrar().setEnabled(false);
		}
	}
	
	//VALIDA CAMPOS PARA LIBERAR O BOTAO CADASTRAR----------
	public void keyReleased(KeyEvent evento) {
		if(!gui.getNomeCargoField().getText().isEmpty() && !gui.getQtdDigitosField().getText().isEmpty()
				&& Integer.parseInt(gui.getQtdDigitosField().getText()) >= 2){
			gui.getCadastrar().setEnabled(true);
		}
		else
			gui.getCadastrar().setEnabled(false);
	}
	
}
