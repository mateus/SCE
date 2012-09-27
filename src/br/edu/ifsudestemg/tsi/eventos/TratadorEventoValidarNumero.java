package br.edu.ifsudestemg.tsi.eventos;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import br.edu.ifsudestemg.tsi.gui.TelaCadastrarCargo;
import br.edu.ifsudestemg.tsi.gui.TelaCadastrarPartido;
import br.edu.ifsudestemg.tsi.gui.TelaCadastrarPesquisa;
import br.edu.ifsudestemg.tsi.gui.TelaConsultar;
import br.edu.ifsudestemg.tsi.gui.TelaExcluirPartido;

/**Tratador de eventos para validação de valores numéricos
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see KeyAdapter
 *
 */
public class TratadorEventoValidarNumero extends KeyAdapter {

	TelaConsultar guiConsultar = null;
	TelaCadastrarPesquisa guiCadastrarPesquisa = null;
	TelaCadastrarCargo guiCadastrarCargo = null;
	TelaCadastrarPartido guiCadastrarPartido = null;
	TelaExcluirPartido guiExcluirPartido = null;

	/** Valida números da tela de consulta de candidatos
	 * 
	 * @param gui <code>TelaConsultar</code> com a referência da tela de consulta candidatos
	 * 
	 * @see TelaConsultar
	 */
	public TratadorEventoValidarNumero(TelaConsultar gui){
		this.guiConsultar = gui;
	}
	
	/** Valida números da tela de excluir partido
	 * 
	 * @param gui <code>TelaConsultar</code> com a referência da tela de excluir partido
	 * 
	 * @see TelaExcluirPartido
	 */
	public TratadorEventoValidarNumero(TelaExcluirPartido gui){
		this.guiExcluirPartido = gui;
	}

	/** Valida números da tela de consulta de cadastrar pesquisa
	 * 
	 * @param gui <code>TelaCadastrarPesquisa</code> com a referência da tela de cadastrar pesquisa
	 * 
	 * @see TelaCadastrarPesquisa
	 */
	public TratadorEventoValidarNumero(TelaCadastrarPesquisa gui){
		this.guiCadastrarPesquisa = gui;
	}
	
	/** Valida números da tela de consulta de cadastrar partido
	 * 
	 * @param gui <code>TelaCadastrarPesquisa</code> com a referência da tela de cadastrar partido
	 * 
	 * @see TelaCadastrarPartido
	 */
	public TratadorEventoValidarNumero(TelaCadastrarPartido gui){
		this.guiCadastrarPartido = gui;
	}

	/** Valida números da tela de consulta de cadastrar cargo
	 * 
	 * @param guiCadastrarCargo <code>TelaCadastrarCargo</code> com a referência da tela de cadastrar pesquisa
	 * 
	 * @see TelaCadastrarCargo
	 */
	public TratadorEventoValidarNumero(TelaCadastrarCargo guiCadastrarCargo) {
		super();
		this.guiCadastrarCargo = guiCadastrarCargo;
	}

	//Retirar letras do campo numérico 
	public void keyReleased(KeyEvent evento) {
		super.keyReleased(evento);

		//Tela consultar
		if(guiConsultar != null)
			if(evento.getSource() == guiConsultar.getNumeroField()){
				guiConsultar.getNumeroField().setText(guiConsultar.getNumeroField().getText().replaceAll("[^0-9]", ""));
			}

		//Tela Cadastrar Cargo
		if(guiCadastrarCargo != null)
			if(evento.getSource() == guiCadastrarCargo.getQtdDigitosField()){
				guiCadastrarCargo.getQtdDigitosField().setText(guiCadastrarCargo.getQtdDigitosField().getText().replaceAll("[^0-9]", ""));
			}
		
		//Tela Cadastrar Partido
		if(guiCadastrarPartido != null)
			if(evento.getSource() == guiCadastrarPartido.getNumeroPartidoField()){
				guiCadastrarPartido.getNumeroPartidoField().setText(guiCadastrarPartido.getNumeroPartidoField().getText().replaceAll("[^0-9]", ""));
			}
		
		//Tela Cadastrar Partido
		if(guiExcluirPartido != null)
			if(evento.getSource() == guiExcluirPartido.getNumeroField()){
				guiExcluirPartido.getNumeroField().setText(guiExcluirPartido.getNumeroField().getText().replaceAll("[^0-9]", ""));
			}

		//Tela Cadastrar Pesquisa
		if(guiCadastrarPesquisa != null){

			if(evento.getSource() == guiCadastrarPesquisa.getIntencoesVotosField()){
				guiCadastrarPesquisa.getIntencoesVotosField().setText(guiCadastrarPesquisa.getIntencoesVotosField().getText().replaceAll("[^0-9]", ""));
			}

			if(evento.getSource() == guiCadastrarPesquisa.getVotosBrancoNuloField()){
				guiCadastrarPesquisa.getVotosBrancoNuloField().setText(guiCadastrarPesquisa.getVotosBrancoNuloField().getText().replaceAll("[^0-9]", ""));
			}

			if(evento.getSource() == guiCadastrarPesquisa.getNaoSoubeNaoRespondeuField()){
				guiCadastrarPesquisa.getNaoSoubeNaoRespondeuField().setText(guiCadastrarPesquisa.getNaoSoubeNaoRespondeuField().getText().replaceAll("[^0-9]", ""));
			}

			if(evento.getSource() == guiCadastrarPesquisa.getMunicipiosField()){
				guiCadastrarPesquisa.getMunicipiosField().setText(guiCadastrarPesquisa.getMunicipiosField().getText().replaceAll("[^0-9]", ""));
			}
		
		}
	}
}
