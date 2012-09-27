package br.edu.ifsudestemg.tsi.eventos;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.edu.ifsudestemg.tsi.gui.TelaApuracao;
import br.edu.ifsudestemg.tsi.persistencia.ApuracaoBD;

/** Tratador de eventos para a tela de Apuração de votos
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see ItemListener
 *
 */
public class TratadorEventoApuracao implements ItemListener {

	TelaApuracao gui;	

	/**Construtor default
	 * 
	 * @param gui <code>TelaApuracao</code> com a referência da tela de apuração
	 * 
	 * @see TelaApuracao
	 */
	public TratadorEventoApuracao(TelaApuracao gui) {
		super();
		this.gui = gui;
	}

	public void itemStateChanged(ItemEvent evento) {
		if(evento.getSource() == gui.getCargoCB() && evento.getStateChange() == ItemEvent.SELECTED){

			List<String> nomeNumeroLista = new ArrayList<String>();
			ApuracaoBD apuracaoBD = new ApuracaoBD();
			ResultSet resultadoCargo;
			int brancos;
			int nulos;

			brancos = apuracaoBD.consultaVotosBranco(gui.getCargoCB().getItemAt(gui.getCargoCB().getSelectedIndex()));
			nulos = apuracaoBD.consultaVotosNulo(gui.getCargoCB().getItemAt(gui.getCargoCB().getSelectedIndex()));

			//Captura o nome e numero de cada candidato
			resultadoCargo = apuracaoBD.consultaCandidatos(gui.getCargoCB().getItemAt(gui.getCargoCB().getSelectedIndex()));

			try {
				while(resultadoCargo.next())
				{
					nomeNumeroLista.add( resultadoCargo.getString("nome") + " - (" + resultadoCargo.getString("numero") + ")"); // Adiciona na lista os ids 
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
			} 

			for(int i = 0; i< nomeNumeroLista.size();i++){
				System.out.println(nomeNumeroLista.get(i));
			}

			gui.criaGrafico(gui,brancos,nulos,nomeNumeroLista);
		}
	}

}
