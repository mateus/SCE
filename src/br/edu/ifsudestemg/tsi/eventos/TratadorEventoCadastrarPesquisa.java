package br.edu.ifsudestemg.tsi.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import br.edu.ifsudestemg.tsi.gui.TelaCadastrarPesquisa;
import br.edu.ifsudestemg.tsi.persistencia.CandidatoBD;
import br.edu.ifsudestemg.tsi.pesquisa.Pesquisa;
import br.edu.ifsudestemg.tsi.pesquisa.Pesquisa.CandidatoPesquisa;

/**Tratador de eventos para a tela de cadastro de pesquisas
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see MouseAdapter
 * @see ActionListener
 * @see ItemListener
 *
 */
public class TratadorEventoCadastrarPesquisa extends MouseAdapter implements ActionListener,ItemListener{

	TelaCadastrarPesquisa gui;
	
	//Database
	private CandidatoBD candidatoBD = new CandidatoBD();
	private ResultSet resultado;	
	
	//Modelo para a Tabela
	private DefaultTableModel modeloTabela;
	
	//Pesquisa
	private Pesquisa pesquisa = new Pesquisa();
	private CandidatoPesquisa candPesquisa = new CandidatoPesquisa();
	
	/** Construtor default
	 * @param gui <code>TelaCadastrarPesquisa</code> com a referência da tela de cadastro de pesquisas
	 * 
	 * @see TelaCadastrarPesquisa
	 */
	public TratadorEventoCadastrarPesquisa(TelaCadastrarPesquisa gui){
		this.gui = gui;
	}
	
	public void actionPerformed(ActionEvent evento) {
	
		//Botão cadastrar
		if(evento.getSource() == gui.getCadastrar()){
			
			//Capturando hora do sistema
			GregorianCalendar calendario = new GregorianCalendar();  
			String dataAtual = "";

			int d = calendario.get(GregorianCalendar.DATE);  
			int m = calendario.get(GregorianCalendar.MONTH) + 1;  
			int a = calendario.get(GregorianCalendar.YEAR);  
			
			//Data Atual ----
			if(d < 10 && m < 10)
				dataAtual = ""+a+"0"+m+"0"+d;
			else
			if(d < 10)
				dataAtual = ""+a+""+m+"0"+d;
			else
			if(m < 10)
				dataAtual = ""+a+"0"+m+""+d;
				
			//Data Inicial ---
			String dataInicial,dataFinal;
			String dia,mes,ano;
			
			dia = gui.getDataInicial().getEditor().getText().substring(0, 2);
			mes = gui.getDataInicial().getEditor().getText().substring(3, 5);
			ano = gui.getDataInicial().getEditor().getText().substring(6, 10);
			dataInicial = ano + "" + mes + "" + dia;
					
			//Data final ----
			dia = gui.getDataFinal().getEditor().getText().substring(0, 2);
			mes = gui.getDataFinal().getEditor().getText().substring(3, 5);
			ano = gui.getDataFinal().getEditor().getText().substring(6, 10);
			dataFinal = ano + "" + mes + "" + dia;

			//Datas antes de 1995
			if( Integer.parseInt(dataFinal.substring(0, 4)) < 1995 || 
					Integer.parseInt(dataInicial.substring(0, 4)) < 1995){
				JOptionPane.showMessageDialog(null, "Anos antes de 1995 são inválidos","Erro",
						JOptionPane.ERROR_MESSAGE);
			}
			else
			//Data final > data atual
			if( Integer.parseInt(dataFinal) > Integer.parseInt(dataAtual)){
				JOptionPane.showMessageDialog(null, "Data Final não pode ser mair do que a Data Atual","Erro",
						JOptionPane.ERROR_MESSAGE);
			}
			else
			// Data final < data inicial
			if(Integer.parseInt(dataFinal) < Integer.parseInt(dataInicial)){
				JOptionPane.showMessageDialog(null, "Data Final não pode ser menor do que a Data Inicial" +
						"","Erro",
						JOptionPane.ERROR_MESSAGE);
			}
			else
			// Pesquisa duplicada
			if(pesquisa.pesquisaPesquisa(gui.getCargoCB().getItemAt(gui.getCargoCB().getSelectedIndex())
					, dataInicial, dataFinal)){
				JOptionPane.showMessageDialog(null, "Pesquisa com Cargo: " + gui.getCargoCB().getItemAt(gui.getCargoCB().getSelectedIndex()) +
						", Data Inicial: " + gui.getDataInicial().getEditor().getText()+ "\ne Data Final: "+ gui.getDataFinal().getEditor().getText() + " já cadastrada","Erro",
						JOptionPane.ERROR_MESSAGE);
			}
			// Insere no banco
			else{
				
				int op;
				op = JOptionPane.showConfirmDialog(null, "Têm certeza que deseja cadastrar esta pesquisa ?\n" +
						"Ao cadastrar uma pesquisa, o sistema de cadastro ,edição e exclusão\nde candidadtos serão finalizado." , "Atenção" , JOptionPane.YES_NO_OPTION);

				//Caso tenha clicado em SIM
				if(op != 1){	
					gui.getTabela().clearSelection(); // Retira foco de todos os campos da tabela.
					
					pesquisa.setCargo(gui.getCargoCB().getItemAt(gui.getCargoCB().getSelectedIndex()));
					pesquisa.setDataInicio(dataInicial);
					pesquisa.setDataFim(dataFinal);
					pesquisa.setBrancosNulos(gui.getVotosBrancoNuloField().getText());
					pesquisa.setNaoSouberam(gui.getNaoSoubeNaoRespondeuField().getText());
					pesquisa.setEntrevistados(gui.getEntrevistadosField().getText());
					pesquisa.setMunicipios(gui.getMunicipiosField().getText());

					pesquisa.insereBD(pesquisa);
					
					for(int i=0;i<gui.getTabela().getModel().getRowCount();i++){
						candPesquisa.setNumero(
								gui.getTabela().getValueAt(i, 0).toString().substring(
										gui.getTabela().getValueAt(i, 0).toString().indexOf("-")+1,
										gui.getTabela().getValueAt(i, 0).toString().length())
										);
						candPesquisa.setIntecaoVoto(gui.getTabela().getValueAt(i, 1).toString());
						
						int sequencia = pesquisa.consultaSequencia();
						
						pesquisa.insereCandidatoPesquisaBD(candPesquisa,sequencia);
						
						gui.dispose();
					}			
					
				}
			}
		}
		
		//Botão aplicar(Calcular)
		if(evento.getSource() == gui.getAplicar()){

			int contadorEntrevistados = 0;
			
			for(int i=0;i<gui.getTabela().getModel().getRowCount();i++){
				contadorEntrevistados += Integer.parseInt( 
						(String) gui.getTabela().getModel().getValueAt(
								i, gui.getTabela().getColumn("Intenções de Voto").getModelIndex())
						);	
			}
			
			if(!gui.getVotosBrancoNuloField().getText().isEmpty())
				contadorEntrevistados += Integer.parseInt(gui.getVotosBrancoNuloField().getText());
			if(!gui.getNaoSoubeNaoRespondeuField().getText().isEmpty())
				contadorEntrevistados += Integer.parseInt(gui.getNaoSoubeNaoRespondeuField().getText());

			
			gui.getEntrevistadosField().setText("" + contadorEntrevistados);
			
			//Libera botão cadastrar
			try{
				if(Integer.parseInt( gui.getMunicipiosField().getText() ) > 0
						&& Integer.parseInt( gui.getEntrevistadosField().getText()) > 0){
					gui.getCadastrar().setEnabled(true);
				}
				else
					gui.getCadastrar().setEnabled(false);
			}catch (Exception e) {
				gui.getCadastrar().setEnabled(false);
			}
		}
		
		//Botão Limpar
		if(evento.getSource() == gui.getLimpar()){
			
			if(gui.getCargoCB().getSelectedIndex() == 0){
				//Limpa tabela
				for(int i=0;i<gui.getTabela().getModel().getRowCount();i++){
					gui.getTabela().setValueAt("0", i, 1);
				}
			}
			else
				gui.getCargoCB().setSelectedIndex(0);
	
			gui.getDataInicial().setDate(new Date());
			gui.getDataFinal().setDate(new Date());
			
			gui.getVotosBrancoNuloField().setText("0");
			gui.getNaoSoubeNaoRespondeuField().setText("0");
			gui.getMunicipiosField().setText("0");
			gui.getEntrevistadosField().setText("");
		}
	}
	
	//Retira valor 0 dos campos
	public void mousePressed(MouseEvent evento){
		
		if(evento.getSource() == gui.getVotosBrancoNuloField()){
			if(gui.getVotosBrancoNuloField().getText().equalsIgnoreCase("0"))
				gui.getVotosBrancoNuloField().setText("");
		}
		
		if(evento.getSource() == gui.getNaoSoubeNaoRespondeuField()){
			if(gui.getNaoSoubeNaoRespondeuField().getText().equalsIgnoreCase("0"))
				gui.getNaoSoubeNaoRespondeuField().setText("");
		}
		
		if(evento.getSource() == gui.getMunicipiosField()){
			if(gui.getMunicipiosField().getText().equalsIgnoreCase("0"))
				gui.getMunicipiosField().setText("");
		}
	
	}
	
	//Troca valores da tabela
	public void itemStateChanged(ItemEvent evento) {
		
		if(evento.getSource() == gui.getCargoCB() && evento.getStateChange() == ItemEvent.SELECTED){
			
			gui.getEntrevistadosField().setText("0");
			gui.getCadastrar().setEnabled(false);
			
			modeloTabela = (DefaultTableModel)(gui.getTabela().getModel());
			modeloTabela.setRowCount(0);

			resultado = candidatoBD.consultaCandidatoCargo(gui.getCargoCB().getItemAt(gui.getCargoCB().getSelectedIndex()));

			Object[] linha = new Object[4];
			try {
				while(resultado.next())
				{
					linha[0] = resultado.getString("nome")+" - "+resultado.getString("numero");
					linha[1] = "0";

					modeloTabela.addRow(linha);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
			} 
			gui.repaint(); // Atualiza tela
			
		}
	}
	
	//Ao retirar foco do campo
	
	/**Gerencia a perda de foco dos campos do formulário de pesquisa
	 * 
	 * @author Mateus Ferreira
	 * @author Vinicius Lehmann
	 * 
	 * @see FocusAdapter
	 * @see TelaCadastrarPesquisa
	 *
	 */
	public static class TratadorEventoCadastrarPesquisaPerdaFoco extends FocusAdapter{
		
		TelaCadastrarPesquisa gui;

		/** Construtor default
		 * @param gui <code>TelaCadastrarPesquisa</code> com a referência da tela de cadastrar pesquisa
		 * 
		 * @see TelaCadastrarPesquisa
		 */
		public TratadorEventoCadastrarPesquisaPerdaFoco(TelaCadastrarPesquisa gui){
			this.gui = gui;
		}

		public void focusLost(FocusEvent evento) {
			super.focusLost(evento);
			
			if(evento.getSource() == gui.getVotosBrancoNuloField()){
				if(gui.getVotosBrancoNuloField().getText().isEmpty())
					gui.getVotosBrancoNuloField().setText("0");
			}
			
			if(evento.getSource() == gui.getNaoSoubeNaoRespondeuField()){
				if(gui.getNaoSoubeNaoRespondeuField().getText().isEmpty())
					gui.getNaoSoubeNaoRespondeuField().setText("0");
			}
			
			if(evento.getSource() == gui.getMunicipiosField()){
				if(gui.getMunicipiosField().getText().isEmpty())
					gui.getMunicipiosField().setText("0");
			}
		}		
	}
	
	//Evento de keyReleased // Libera Botão Cadastrar
	
	/**Gerencia liberação do botão cadastrar da tela de cadastro de pesquisas
	 * 
	 * @author Mateus Ferreira
	 * @author Vinicius Lehmann
	 * 
	 * @see KeyAdapter
	 *
	 */
	public static class TratadorEventoCadastrarPesquisaKeyReleased extends KeyAdapter{
		
		TelaCadastrarPesquisa gui;

		/** Construtor default
		 * @param gui <code>TelaCadastrarPesquisa</code> com a referência da tela de cadastrar pesquisa
		 * 
		 * @see TelaCadastrarPesquisa
		 */
		public TratadorEventoCadastrarPesquisaKeyReleased(TelaCadastrarPesquisa gui){
			this.gui = gui;
		}

		public void keyReleased(KeyEvent evento) {
			super.keyReleased(evento);
			//Libera botão cadastrar
			try{
				if(Integer.parseInt( gui.getMunicipiosField().getText() ) > 0
						&& Integer.parseInt( gui.getEntrevistadosField().getText()) > 0){
					gui.getCadastrar().setEnabled(true);
				}
				else
					gui.getCadastrar().setEnabled(false);
			}catch (Exception e) {
				gui.getCadastrar().setEnabled(false);
			}
		}		
	}
}
