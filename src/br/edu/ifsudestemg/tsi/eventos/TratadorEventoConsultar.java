package br.edu.ifsudestemg.tsi.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import br.edu.ifsudestemg.tsi.gui.TelaConsultar;
import br.edu.ifsudestemg.tsi.gui.TelaDadosCandidato;
import br.edu.ifsudestemg.tsi.gui.TelaEditarCadastro;
import br.edu.ifsudestemg.tsi.persistencia.CandidatoBD;

/**Tratador de eventos para a tela de consultas de candidatos
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see MouseAdapter
 * @see ActionListener
 *
 */
public class TratadorEventoConsultar extends MouseAdapter implements ActionListener,KeyListener {

	//Icones e Imagens
	private ImageIcon imagemCandidato;

	private TelaConsultar gui;
	
	//Database
	private CandidatoBD candidatoBD = new CandidatoBD();
	private ResultSet resultado;	

	//Modelo para a Tabela
	private DefaultTableModel modeloTabela;

	/** Construtor default
	 * @param gui <code>TelaConsultar</code> com a referência da tela de consultas de candidatos
	 * 
	 * @see TelaConsultar
	 */
	public TratadorEventoConsultar(TelaConsultar gui){
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent evento) {

		//Verifica se o botao pesquisar foi clicado e se o campo nome está vazio
		if(evento.getSource() == gui.getPesquisar() && !gui.getNomeCandidatoField().getText().isEmpty()){

			modeloTabela = (DefaultTableModel)(gui.getTabela().getModel());
			modeloTabela.setRowCount(0);

			resultado = candidatoBD.consultaCandidatoNome(gui.getNomeCandidatoField().getText().replace("'", "\\'"));

			Object[] linha = new Object[4];
			try {
				while(resultado.next())
				{
					linha[0] = resultado.getString("nome");
					linha[1] = resultado.getString("cargo");
					linha[2] = resultado.getString("partido");
					linha[3] = resultado.getString("numero");
					modeloTabela.addRow(linha);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
			} 
			gui.repaint(); // Atualiza tela
		}else
			//Se campo numero está vazio
			if(evento.getSource() == gui.getPesquisar() && !gui.getNumeroField().getText().isEmpty()){

				modeloTabela = (DefaultTableModel)(gui.getTabela().getModel());
				modeloTabela.setRowCount(0);

				resultado = candidatoBD.consultaCandidatoNumero(
						gui.getNumeroField().getText().replace("'", "\\'"));

				Object[] linha = new Object[4];
				try {
					while(resultado.next())
					{
						linha[0] = resultado.getString("nome");
						linha[1] = resultado.getString("cargo");
						linha[2] = resultado.getString("partido");
						linha[3] = resultado.getString("numero");
						modeloTabela.addRow(linha);
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
				}
				gui.repaint(); // Atualiza tela 
			}

		//Se campo numero está vazio COM ENTER
		if(!gui.getNumeroField().getText().isEmpty()){

			modeloTabela = (DefaultTableModel)(gui.getTabela().getModel());
			modeloTabela.setRowCount(0);

			resultado = candidatoBD.consultaCandidatoNumero(
					gui.getNumeroField().getText().replace("'", "\\'"));

			Object[] linha = new Object[4];
			try {
				while(resultado.next())
				{
					linha[0] = resultado.getString("nome");
					linha[1] = resultado.getString("cargo");
					linha[2] = resultado.getString("partido");
					linha[3] = resultado.getString("numero");
					modeloTabela.addRow(linha);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
			}
			gui.repaint(); // Atualiza tela 
		}
	}

	public void mouseClicked(MouseEvent evento) {
		//Ação de selecionar uma linha para consultar
		if(evento.getSource() == gui.getTabela() && !gui.isAlterar() && !gui.isExcluir()){

			try {
				int linhaSelecionada = gui.getTabela().getSelectedRow();
				if(gui.getTabela().getValueAt(linhaSelecionada, 3)!=null)
				{
					// valida aspas simples
					resultado = candidatoBD.consultaCandidatoNumero(
							gui.getTabela().getValueAt(linhaSelecionada, 3).toString().replace("'", "\\'"));

					while(resultado.next())
					{
						String diretorio = resultado.getString("diretorio");
						String nome = resultado.getString("nome");
						String cargo = resultado.getString("cargo");
						String partido = resultado.getString("partido");
						String numero = resultado.getString("numero");

						//Adicionando ao objeto imagem a imagem selecionada / Colocando imagem em escala (120x140)
						imagemCandidato = new ImageIcon (diretorio); 
						imagemCandidato.setImage(imagemCandidato.getImage().getScaledInstance(120, 140, 100));

						//Abrir tela dos dados do candidato selecionado 
						new TelaDadosCandidato(diretorio, nome, cargo, partido, numero);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		
		//Ação de selecionar uma linha para editar
		if(evento.getSource() == gui.getTabela() && gui.isAlterar() && !gui.isExcluir()){

			try {
				int linhaSelecionada = gui.getTabela().getSelectedRow();
				if(gui.getTabela().getValueAt(linhaSelecionada, 3)!=null)
				{
					// valida aspas simples
					resultado = candidatoBD.consultaCandidatoNumero(
							gui.getTabela().getValueAt(linhaSelecionada, 3).toString().replace("'", "\\'"));

					while(resultado.next())
					{
						String diretorio = resultado.getString("diretorio");
						String nome = resultado.getString("nome");
						String cargo = resultado.getString("cargo");
						String partido = resultado.getString("partido");
						String numero = resultado.getString("numero");
						
						new TelaEditarCadastro(nome, cargo, partido, numero, diretorio);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		
		//Ação de selecionar uma linha para excluir
		if(evento.getSource() == gui.getTabela() && !gui.isAlterar() && gui.isExcluir()){
			int linhaSelecionada = gui.getTabela().getSelectedRow();
			if(gui.getTabela().getValueAt(linhaSelecionada, 3)!=null){
				int op = JOptionPane.showConfirmDialog(
						null, "Atenção: Deseja deletar o candidato "+
						gui.getTabela().getValueAt(linhaSelecionada, 0).toString()+" ?" , "Atenção" , 
						JOptionPane.YES_NO_OPTION);
				if(op != 1)
				{
					candidatoBD.removeBD(gui.getTabela().getValueAt(linhaSelecionada, 3).toString(),
							gui.getTabela().getValueAt(linhaSelecionada, 1).toString());
					JOptionPane.showMessageDialog(null, "Cadastro removido com sucesso.","Sucesso",
							JOptionPane.INFORMATION_MESSAGE);
					modeloTabela.removeRow(linhaSelecionada);
				}			
			}
		}
}

	public void keyReleased(KeyEvent evento) {

		modeloTabela = (DefaultTableModel)(gui.getTabela().getModel()); // Modelo para Tabela

		//Verifica se o botao pesquisar foi clicado e se o campo nome está vazio
		if(!gui.getNomeCandidatoField().getText().isEmpty()){

			modeloTabela.setRowCount(0); // Zera a tabela

			resultado = candidatoBD.consultaCandidatoNome(
					gui.getNomeCandidatoField().getText().replace("'", "\\'")); // valida aspas simples

			Object[] linha = new Object[4];
			try {
				while(resultado.next())
				{
					linha[0] = resultado.getString("nome");
					linha[1] = resultado.getString("cargo");
					linha[2] = resultado.getString("partido");
					linha[3] = resultado.getString("numero");
					modeloTabela.addRow(linha);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
			} 
			gui.repaint(); // Atualiza tela
		}else 
			//Limpa tabela se todos os campos estiverem vazios
			if(gui.getNumeroField().getText().isEmpty() && gui.getNomeCandidatoField().getText().isEmpty()){
				modeloTabela.setRowCount(0);
			}
	}

	public void keyPressed(KeyEvent evento) {
	}

	public void keyTyped(KeyEvent evento) {
	}
}
