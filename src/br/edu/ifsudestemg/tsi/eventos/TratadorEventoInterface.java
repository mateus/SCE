package br.edu.ifsudestemg.tsi.eventos;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JOptionPane;

import br.edu.ifsudestemg.tsi.gui.Interface;
import br.edu.ifsudestemg.tsi.gui.TelaApuracao;
import br.edu.ifsudestemg.tsi.gui.TelaCadastrar;
import br.edu.ifsudestemg.tsi.gui.TelaCadastrarCargo;
import br.edu.ifsudestemg.tsi.gui.TelaCadastrarPartido;
import br.edu.ifsudestemg.tsi.gui.TelaCadastrarPesquisa;
import br.edu.ifsudestemg.tsi.gui.TelaConfiguracoes;
import br.edu.ifsudestemg.tsi.gui.TelaConsultar;
import br.edu.ifsudestemg.tsi.gui.TelaCreditos;
import br.edu.ifsudestemg.tsi.gui.TelaExcluirCargo;
import br.edu.ifsudestemg.tsi.gui.TelaExcluirPartido;
import br.edu.ifsudestemg.tsi.gui.TelaRelatorioPesquisa;
import br.edu.ifsudestemg.tsi.gui.TelaSobre;
import br.edu.ifsudestemg.tsi.gui.TelaVotacaoSelecionaCargos;
import br.edu.ifsudestemg.tsi.persistencia.CargoBD;
import br.edu.ifsudestemg.tsi.persistencia.PartidoBD;
import br.edu.ifsudestemg.tsi.persistencia.PesquisaBD;
import br.edu.ifsudestemg.tsi.persistencia.PopDatabase;
import br.edu.ifsudestemg.tsi.persistencia.PopDatabasePesquisa;

/**Tratador de eventos para a tela principal
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see MouseAdapter
 * @see ActionListener
 *
 */
public class TratadorEventoInterface extends MouseAdapter implements ActionListener {

	private Interface gui;
	private CargoBD cargoBD = new CargoBD();
	private PartidoBD partidoBD = new PartidoBD();
	private PesquisaBD pesquisa = new PesquisaBD();

	/** Construtor default
	 * @param gui <code>TelaExcluirCargo</code> com a referência da tela principal
	 * 
	 * @see TelaExcluirCargo
	 */
	public TratadorEventoInterface(Interface gui) {
		this.gui = gui;
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent evento) {

		//Instancia variavel para som dos botões
		AudioClip somClick = null;
		try {
			somClick = Applet.newAudioClip(new File("sons/Button3.wav").toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 

		//CONFIGURAÇÕES
		if(evento.getSource() == gui.getConfiguracoes())
		{				
			somClick.play();  
			new TelaConfiguracoes();
		}

		//POPULAR BANCO DE DADOS
		if(evento.getSource() == gui.getPopDatabase())
		{				
			somClick.play();  
			new PopDatabase();
		}

		//POPULAR BANCO DE DADOS COM PESQUISAS
		if(evento.getSource() == gui.getPopDatabasePesquisa())
		{				
			somClick.play();  
			new PopDatabasePesquisa();
		}

		//CADASTRAR
		if(evento.getSource() == gui.getCadastrar())
		{
			somClick.play();

			//Verifica se existem partidos e cargos
			if(!partidoBD.existePartido() && !cargoBD.existeCargo()){	
				JOptionPane.showMessageDialog(null,
						"Não existem Partidos e Cargos cadastrados","Erro",
						JOptionPane.ERROR_MESSAGE);
			}
			else
				if(!partidoBD.existePartido()){
					JOptionPane.showMessageDialog(null,
							"Não existem Partidos cadastrados","Erro",
							JOptionPane.ERROR_MESSAGE);
				}
				else
					if(!cargoBD.existeCargo()){
						JOptionPane.showMessageDialog(null,
								"Não existem Cargos cadastrados","Erro",
								JOptionPane.ERROR_MESSAGE);
					}
					else
						if(pesquisa.consultaSequencia() > 0){
							JOptionPane.showMessageDialog(null,
									"Sistema de cadastro bloqueado","Erro",
									JOptionPane.ERROR_MESSAGE);
						}
						else{
							try {
								new TelaCadastrar();
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}

		}

		//CONSULTAR
		if(evento.getSource() == gui.getConsultar())
		{
			somClick.play();
			try {
				new TelaConsultar(false,false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//EDITAR
		if(evento.getSource() == gui.getEditar())
		{
			somClick.play();
			if(pesquisa.consultaSequencia() > 0){
				JOptionPane.showMessageDialog(null,
						"Sistema de edição bloqueado","Erro",
						JOptionPane.ERROR_MESSAGE);
			}
			else{
				try {
					new TelaConsultar(false,true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		//EXCLUIR
		if(evento.getSource() == gui.getExcluir())
		{
			somClick.play();
			if(pesquisa.consultaSequencia() > 0){
				JOptionPane.showMessageDialog(null,
						"Sistema de exclusão bloqueado","Erro",
						JOptionPane.ERROR_MESSAGE);
			}else{
				try {
					new TelaConsultar(true,false);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		//CADASTRAR CARGO
		if(evento.getSource() == gui.getCadastrarCargo())
		{
			somClick.play();
			new TelaCadastrarCargo();
		}

		//EXCLUIR CARGO
		if(evento.getSource() == gui.getExcluirCargo())
		{
			somClick.play();
			new TelaExcluirCargo();
		}


		//CADASTRAR PARTIDO
		if(evento.getSource() == gui.getCadastrarPartido())
		{
			somClick.play();
			new TelaCadastrarPartido();
		}

		//EXCLUIR PARTIDO
		if(evento.getSource() == gui.getExcluirPartido())
		{
			somClick.play();
			new TelaExcluirPartido();	
		}

		//CADASTRAR PESQUISA ELEITORAL
		if(evento.getSource() == gui.getCadPesqEleitoral())
		{
			//Verifica se existem partidos e cargos
			if(!partidoBD.existePartido() && !cargoBD.existeCargo()){	
				JOptionPane.showMessageDialog(null,
						"Não existem Partidos e Cargos cadastrados","Erro",
						JOptionPane.ERROR_MESSAGE);
			}
			else
				if(!partidoBD.existePartido()){
					JOptionPane.showMessageDialog(null,
							"Não existem Partidos cadastrados","Erro",
							JOptionPane.ERROR_MESSAGE);
				}
				else
					if(!cargoBD.existeCargo()){
						JOptionPane.showMessageDialog(null,
								"Não existem Cargos cadastrados","Erro",
								JOptionPane.ERROR_MESSAGE);
					}
					else{
						new TelaCadastrarPesquisa();
					}
		}

		//RELATORIO DE PESQUISA ELEITORAL
		if(evento.getSource() == gui.getRelPesqEleitoral())
		{
			if(!cargoBD.existeCargo()){
				JOptionPane.showMessageDialog(null,
						"Não existem Cargos cadastrados","Erro",
						JOptionPane.ERROR_MESSAGE);
			}
			else{
				somClick.play();
				new TelaRelatorioPesquisa();
			}
		}

		//APURAÇÃO ELEITORAL
		if(evento.getSource() == gui.getApuraEleitoral())
		{
			somClick.play();
			new TelaApuracao();
		}

		//SOBRE
		if(evento.getSource() == gui.getSobre())
		{
			somClick.play();
			new TelaSobre();
		}

		//CREDITOS
		if(evento.getSource() == gui.getCreditos())
		{
			somClick.play();
			new TelaCreditos();
		}

		//SAIR
		if(evento.getSource() == gui.getSair2())
		{
			System.exit(0);
		}

		//BOTAO DE VOTAR
		if(evento.getSource() == gui.getVotar())
		{
			somClick.play(); 
			new TelaVotacaoSelecionaCargos();
		}

	}

	public void mouseClicked(MouseEvent evento) {
		//Ação do Botão sair do JMenuBar----------------------------------------------------------
		if(evento.getSource() == gui.getSair1())
		{
			System.exit(0);
		}		
	}

}
