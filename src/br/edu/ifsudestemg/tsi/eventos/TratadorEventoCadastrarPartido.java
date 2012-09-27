package br.edu.ifsudestemg.tsi.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import br.edu.ifsudestemg.tsi.gui.TelaCadastrarPartido;
import br.edu.ifsudestemg.tsi.partido.Partido;

/**Tratador de eventos para a tela de cadastro de partidos
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see KeyAdapter
 * @see ActionListener
 *
 */
public class TratadorEventoCadastrarPartido extends KeyAdapter implements ActionListener {

	TelaCadastrarPartido gui;
	Partido partido = new Partido();

	/** Construtor default
	 * @param gui <code>TelaCadastrarPartido</code> com a referência da tela de cadastro de partidos
	 * 
	 * @see TelaCadastrarPartido
	 */
	public TratadorEventoCadastrarPartido(TelaCadastrarPartido gui) {
		super();
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		if( evento.getSource() == gui.getCadastrar()){
			partido.setSigla(gui.getSiglaPartidoField().getText().trim());
			partido.setNumero(Integer.parseInt( gui.getNumeroPartidoField().getText() ));
			
			//valida unicidade das chaves
			if( partido.getSigla().isEmpty()){
				JOptionPane.showMessageDialog(null, "Sigla deve ter pelomenos 1 caracter","Erro",
						JOptionPane.ERROR_MESSAGE);
			}
			else
			if( partido.pesquisaPartido(partido.getNumero()) )
			{
				JOptionPane.showMessageDialog(null, "Número do partido já cadastrado","Erro",
						JOptionPane.ERROR_MESSAGE);
			}
			else 
			if( partido.pesquisaPartido(partido.getSigla()) )
			{
				JOptionPane.showMessageDialog(null, "Sigla do partido já cadastrada","Erro",
						JOptionPane.ERROR_MESSAGE);
			}	
			//Realiza gravação dos dados no banco de dados
			else  
			{
				partido.inserePartidoBD(partido);
				gui.dispose();
			}
		}
		
		//Apaga todos os campos
		if(evento.getSource() == gui.getLimpar()){
			gui.getSiglaPartidoField().setText("");
			gui.getNumeroPartidoField().setText("");
			gui.getCadastrar().setEnabled(false);
		}
	}

	//VALIDA CAMPOS PARA LIBERAR O BOTAO CADASTRAR----------
	public void keyReleased(KeyEvent evento) {
		if(!gui.getSiglaPartidoField().getText().isEmpty() 
				&& gui.getNumeroPartidoField().getText().length() == 2){
			gui.getCadastrar().setEnabled(true);
		}
		else
			gui.getCadastrar().setEnabled(false);
	}

}
