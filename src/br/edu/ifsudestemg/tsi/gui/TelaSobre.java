package br.edu.ifsudestemg.tsi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Cria conteúdo gráfico da tela sobre
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see JDialog
 */
public class TelaSobre extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Icones
		private ImageIcon iconePersonalizado = new ImageIcon("icones/logo2.png" );

		//Paineis
		private JPanel painelTitulo = new JPanel();
		private JPanel painelTexto = new JPanel();

		//Rótulos
		private JLabel tituloLabel = new JLabel("Sobre");
		private JLabel textoLabel = new JLabel();

		//Fonte
		private Font fonte = new Font("Arial", Font.BOLD, 20);
		private Font fonte2 = new Font("Arial", Font.PLAIN, 12);

		
		/**
		 * Construtor default que criará todo conteúdo gráfico da tela
		 */
		public TelaSobre() {
			super();
			
			//-----------------------------TITULO DO DIALOGO-----------------------------
			tituloLabel.setFont(fonte);
			painelTitulo.add(tituloLabel);

			//----------------------------ADICIONANDO COR BRANCA-------------------------
			painelTitulo.setBackground(Color.WHITE);
			painelTexto.setBackground(Color.WHITE);
			
			//------------------------------ADICIONANDO TEXTOS---------------------------
			textoLabel.setFont(fonte2);
			textoLabel.setText(
					"<html><font size=\"4\">" +
					"Software com a finalidade de simular um sistema de eleições." +
					"<br><br><b>Módulos presentes:</b>" +
					"<table cellspacing=\"2\" cellpadding=\"2\" border=\"0\" width=\"100%\">" +
						"<tr>" +
							"<td width=\"40%\" valign=\"top\" align=\"left\">" +
								"<ul TYPE=\"1\">" +
									"<li>Candidato" +
										"<ul>" +
											"<li>Cadastro</li>" +
											"<li>Editar</li>" +
											"<li>Excluir</li>" +
											"<li>Consultar</li>" +
										"</ul>" +
									"</li>" +
									"<li>Cargo" +
										"<ul>" +
											"<li>Cadastrar</li>" +
											"<li>Excluir</li>" +
										"</ul>" +
									"</li>" +
									"<li>Partido" +
									"<ul>" +
										"<li>Cadastrar</li>" +
										"<li>Excluir</li>" +
									"</ul>" +
								"</li>" +
								"</ul>" +
							"</td>" +
							"<td width=\"60%\" valign=\"top\" align=\"left\">" +
								"<ul TYPE=\"1\" START=\"4\">" +
									"<li>Pesquisar" +
										"<ul>" +
											"<li>Cadastrar Pesq. Eleitoral</li>" +
											"<li>Relatório de Pesq Eleitoral</li>" +
										"</ul>" +
									"</li>" +
									"<li>Apuração" +
										"<ul>" +
											"<li>Apuração Eleitoral</li>" +
										"</ul>" +
									"</li>" +
									"<li>Simular votação" +
									"</li>" +
								"</ul>" +
							"</td>" +
						"</tr>" +
					"</table>" +
					"</font></html>");
		
			painelTexto.add(textoLabel,BorderLayout.WEST);
			painelTexto.setBorder(BorderFactory.createTitledBorder(""));

			//------------------------------ICONE DA JANELA------------------------------
			setIconImage( iconePersonalizado.getImage() );

			//---------------------------ADICIOANANDO OS PAINEIS-------------------------
			add(painelTitulo,BorderLayout.NORTH);
			add(painelTexto,BorderLayout.CENTER);

			//-----------------------SETANDO ATRIBUTOS DO JFRAME-------------------------
			pack();
			setTitle("Sobre");
			setModal(true);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setResizable(false);
			setLocationRelativeTo(null);
			setVisible(true);
		}
}
