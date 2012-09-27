package br.edu.ifsudestemg.tsi.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.edu.ifsudestemg.tsi.gui.TelaRelatorioPesquisa;
import br.edu.ifsudestemg.tsi.pesquisa.Pesquisa;

/**Tratador de eventos para a tela de relatório de pesquisa
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see ActionListener
 *
 */
public class TratadorEventoRelatorioPesquisa implements ActionListener {

	TelaRelatorioPesquisa gui;

	//Pesquisa
	private Pesquisa pesquisa = new Pesquisa();
	private ResultSet resultado;

	/** Construtor default
	 * @param gui <code>TelaRelatorioPesquisa</code> com a referência da tela de relatório de pesquisa
	 * 
	 * @see TelaRelatorioPesquisa
	 */
	public TratadorEventoRelatorioPesquisa(TelaRelatorioPesquisa gui) {
		super();
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent evento) {

		//Botão exibir
		if(evento.getSource() == gui.getExibir()){
			List<ArrayList<String>> matriz = new ArrayList<ArrayList<String>>();
			ArrayList<String> mesInicio = new ArrayList<String>();
			ArrayList<String> anoInicio = new ArrayList<String>();
			ArrayList<String> brancosNulos = new ArrayList<String>();
			ArrayList<String> indecisos = new ArrayList<String>();
			ArrayList<String> entrevistados = new ArrayList<String>();
			ArrayList<String> municipios = new ArrayList<String>();

			List<String> res = new ArrayList<String>(); 
			String[] resStr;

			//Captura somente os IDs de pesquisa
			resultado = pesquisa.consultaEntreDatas(gui.getCargoCB().getItemAt(gui.getCargoCB().getSelectedIndex())
					, gui.getDataInicial().getEditor().getText(), 
					gui.getDataFinal().getEditor().getText());

			try {
				while(resultado.next())
				{
					res.add( resultado.getString("id") ); // Adiciona na lista os ids 
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
			} 

			resStr = (String[]) res.toArray (new String[0]);   // transfere para string

			//Para cara ID de pesquisa imprime o seus resultados
			for(int i=0;i<resStr.length;i++){
				//System.out.print("\n ID:"+resStr[i]);

				List<String> nome = new ArrayList<String>(); 
				List<String> intencao = new ArrayList<String>();
				String[] numeroStr;
				String[] intencaoStr;

				resultado = pesquisa.consultaCandidatoPesquisa(resStr[i]);
				try {
					while(resultado.next())
					{
						nome.add( resultado.getString("nome") );
						intencao.add( resultado.getString("intencaovoto") );
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
				} 

				numeroStr = (String[]) nome.toArray (new String[0]);   // transfere para string
				intencaoStr = (String[]) intencao.toArray (new String[0]);   // transfere para string

				matriz.add(new ArrayList<String>()); // Cria linha 1

				//Imprime resultado com nome intenção
				for(int k=0;k<numeroStr.length;k++){
					//System.out.print("\n Nome:"+numeroStr[k]+" Intencao:"+intencaoStr[k]);

					//Preencher matriz
					if(i == 0){ //Nome do candidato + inteção
						matriz.add(new ArrayList<String>());
						matriz.get(i).add( numeroStr[k] );
						matriz.get(i+1).add( intencaoStr[k] );
					}else{ // Somente inteção
						matriz.get(i+1).add( intencaoStr[k] );
					}					
				}				
			}


			//Preencher array de datas inicio
			List<String> resMes = new ArrayList<String>(); 
			List<String> resAno = new ArrayList<String>(); 
			String[] resMesStr;
			String[] resAnoStr;

			//Captura somente os meses e anos de pesquisa
			resultado = pesquisa.consultaDataInicioEntreDatas(gui.getCargoCB().getItemAt(gui.getCargoCB().getSelectedIndex())
					, gui.getDataInicial().getEditor().getText(), 
					gui.getDataFinal().getEditor().getText());

			try {
				while(resultado.next())
				{
					resMes.add( resultado.getString("mes") ); // Adiciona na lista 
					resAno.add( resultado.getString("ano") ); // Adiciona na lista 
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
			} 

			resMesStr = (String[]) resMes.toArray (new String[0]);   // transfere para string
			resAnoStr = (String[]) resAno.toArray (new String[0]);   // transfere para string

			for(int a=0;a<resMesStr.length;a++)
				mesInicio.add(resMesStr[a]);

			for(int a=0;a<resAnoStr.length;a++)
				anoInicio.add(resAnoStr[a]);

			//Preencher array de entrevistados e municípios
			List<String> resEntr = new ArrayList<String>(); 
			List<String> resMun = new ArrayList<String>(); 
			String[] resEntrStr;
			String[] resMunStr;

			//Captura somente os entrevistados e municipios de pesquisa
			resultado = pesquisa.consultaEntrMunEntreDatas(gui.getCargoCB().getItemAt(gui.getCargoCB().getSelectedIndex())
					, gui.getDataInicial().getEditor().getText(), 
					gui.getDataFinal().getEditor().getText());

			try {
				while(resultado.next())
				{
					resEntr.add( resultado.getString("entr") ); // Adiciona na lista 
					resMun.add( resultado.getString("mun") ); // Adiciona na lista 
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
			} 

			resEntrStr = (String[]) resEntr.toArray (new String[0]);   // transfere para string
			resMunStr = (String[]) resMun.toArray (new String[0]);   // transfere para string
			for(int a=0;a<resEntrStr.length;a++)
				entrevistados.add(resEntrStr[a]);

			for(int a=0;a<resMunStr.length;a++)
				municipios.add(resMunStr[a]);


			//Preencher array de brancos, nulos, indecisos
			List<String> resBrancosNulos = new ArrayList<String>(); 
			List<String> resIndecisos = new ArrayList<String>(); 
			String[] resBrancosNulosStr;
			String[] resIndecisosStr;

			resultado = pesquisa.consultaBrancosNulosIndecisos();

			try {
				while(resultado.next())
				{
					resBrancosNulos.add( resultado.getString("nulos_brancos") ); // Adiciona na lista 
					resIndecisos.add( resultado.getString("indecisos") ); // Adiciona na lista 
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
			} 

			resBrancosNulosStr = (String[]) resBrancosNulos.toArray (new String[0]);   // transfere para string
			resIndecisosStr = (String[]) resIndecisos.toArray (new String[0]);   // transfere para string

			for(int a=0;a<resBrancosNulosStr.length;a++)
				brancosNulos.add(resBrancosNulosStr[a]);

			for(int a=0;a<resIndecisosStr.length;a++)
				indecisos.add(resIndecisosStr[a]);

			//Repintar a tela
			gui.criaGrafico(gui,matriz,mesInicio,anoInicio,brancosNulos,indecisos, entrevistados, municipios);
			//gui.repaint();
		}
	}
}
