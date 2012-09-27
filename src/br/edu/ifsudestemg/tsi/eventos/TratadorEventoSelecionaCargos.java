package br.edu.ifsudestemg.tsi.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;

import br.edu.ifsudestemg.tsi.gui.TelaVotacao;
import br.edu.ifsudestemg.tsi.gui.TelaVotacaoSelecionaCargos;
import br.edu.ifsudestemg.tsi.persistencia.Database;

/**Tratador de eventos para a tela de selecionar cargos para votação
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see ActionListener
 *
 */
public class TratadorEventoSelecionaCargos implements ActionListener {

	TelaVotacaoSelecionaCargos gui;
	private Database database = new Database();


	/** Construtor default
	 * @param gui <code>TelaVotacaoSelecionaCargos</code> com a referência da tela de selecionar
	 * cargos para votação
	 * 
	 * @see TelaVotacaoSelecionaCargos
	 */
	public TratadorEventoSelecionaCargos(TelaVotacaoSelecionaCargos gui) {
		super();
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent evento) {

		//Botão >> 
		if(evento.getSource() == gui.getAdiciona()){

			//Modelo da lista de selecionados
			DefaultListModel<String> modeloLista = 
					(DefaultListModel<String>) gui.getCargosSelecionados().getModel();

			String selecionado = gui.getCargos().getSelectedValue(); // Pega o selecionado
			if(selecionado != null){
				if(!modeloLista.contains(selecionado))
					modeloLista.addElement(selecionado);
				((DefaultListModel<String>)gui.getCargos().getModel()).removeElement(selecionado); // Remove o selecionado

				//Carrega o array de desordenados
				ArrayList<String> cargosDesordenados = new ArrayList<String>();
				for(int i = 0; i < modeloLista.size(); i++)
					cargosDesordenados.add(modeloLista.getElementAt(i));

				//Ordena o array
				Collections.sort(cargosDesordenados);
				modeloLista.removeAllElements();

				//Adicionar o array ordenado
				for(int i=0; i < cargosDesordenados.size(); i++)
					modeloLista.addElement(cargosDesordenados.get(i));
				
				//Seleciona o ultimo
				if(gui.getCargos().getModel().getSize() > 0)
					gui.getCargos().setSelectedIndex(
							gui.getCargos().getModel().getSize()-1);
			}
			
			if(modeloLista.size() > 0)
				gui.getIniciar().setEnabled(true);
				else
					gui.getIniciar().setEnabled(false);
		}

		//Botão <<
		if(evento.getSource() == gui.getRemove()){

			//Modelo da lista de não selecionados
			DefaultListModel<String> modeloLista = 
					(DefaultListModel<String>) gui.getCargos().getModel();


			String selecionado = gui.getCargosSelecionados().getSelectedValue(); // Pega o selecionado
			if(selecionado != null){
				if(!modeloLista.contains(selecionado))
					modeloLista.addElement(selecionado);
				((DefaultListModel<String>)gui.getCargosSelecionados().getModel()).removeElement(selecionado); // Remove o selecionado

				//Carrega o array de desordenados
				ArrayList<String> cargosDesordenados = new ArrayList<String>();
				for(int i = 0; i < modeloLista.size(); i++)
					cargosDesordenados.add(modeloLista.getElementAt(i));

				//Ordena o array
				Collections.sort(cargosDesordenados);
				modeloLista.removeAllElements();

				//Adicionar o array ordenado
				for(int i=0; i < cargosDesordenados.size(); i++)
					modeloLista.addElement(cargosDesordenados.get(i));
				
				if(gui.getCargosSelecionados().getModel().getSize() > 0)
					gui.getCargosSelecionados().setSelectedIndex(
							gui.getCargosSelecionados().getModel().getSize()-1);
			}	
			
			if(gui.getCargosSelecionados().getModel().getSize() > 0)
				gui.getIniciar().setEnabled(true);
			else
				gui.getIniciar().setEnabled(false);
		}
		
		//Botão limpar
		if(evento.getSource() == gui.getLimpar()){
			//Modelo da lista de não selecionados
			DefaultListModel<String> modeloLista = 
					(DefaultListModel<String>) gui.getCargos().getModel();
			
			//Modelo da lista de selecionados
			DefaultListModel<String> modeloListaNaoSelecionados = 
					(DefaultListModel<String>) gui.getCargosSelecionados().getModel();
			
			//Carrega o array de itens
			ArrayList<String> cargos = new ArrayList<String>();
			for(int i = 0; i < modeloListaNaoSelecionados.size(); i++)
				cargos.add(modeloListaNaoSelecionados.getElementAt(i));
			modeloListaNaoSelecionados.removeAllElements();
			
			//Coloca no cargos não selecionados os itens
			for(int i=0; i < cargos.size(); i++)
				modeloLista.addElement(cargos.get(i));			
		}
		
		//Botão iniciar
		if(evento.getSource() == gui.getIniciar()){
			
			for(int i = 0; i<gui.getCargosSelecionados().getModel().getSize() ; i++){
				gui.getCargosParaVotacao().add(
						gui.getCargosSelecionados().getModel().getElementAt(i));
			}
			
			gui.dispose();
			// Limpa banco para iniciar nova apuração
			database.abreConexao();
			database.gravar("TRUNCATE TABLE votacao_candidatos_votos"); 
			database.fechaConexao();
			new TelaVotacao(gui.getCargosParaVotacao());
		}
	}
}
