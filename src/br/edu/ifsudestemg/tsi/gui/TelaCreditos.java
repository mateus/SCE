package br.edu.ifsudestemg.tsi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Cria conteúdo gráfico da tela de créditos
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see JDialog
 */
public class TelaCreditos extends JDialog {
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
	private JLabel tituloLabel = new JLabel("Créditos");
	private JLabel textoLabel = new JLabel();

	//Fonte
	private Font fonte = new Font("Arial", Font.BOLD, 20);
	private Font fonte2 = new Font("Arial", Font.PLAIN, 13);
	/**
	 * Construtor default que criará todo conteúdo gráfico da tela
	 */
	public TelaCreditos(){
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
				"<html><center>" +
				"<font size='5' color='#68B92C'><b>Instituto Federal de Educação," +
				"<b><br>Ciência e Tecnologia</font>" +
				"<br><font size = '3' color='#000000'><hr><br><b>Mateus Ferreira Silva</b> - TSI 3º Período - Barbacena" +
				"<b><br>Vinicius Lehmann</b> - TSI 3º Período - Barbacena</font>" +
				"<br></html>");
	
		painelTexto.add(textoLabel,BorderLayout.WEST);
		painelTexto.setBorder(BorderFactory.createTitledBorder(""));

		//------------------------------ICONE DA JANELA------------------------------
		setIconImage( iconePersonalizado.getImage() );

		//---------------------------ADICIOANANDO OS PAINEIS-------------------------
		add(painelTitulo,BorderLayout.NORTH);
		add(painelTexto,BorderLayout.CENTER);

		//-----------------------SETANDO ATRIBUTOS DO JFRAME-------------------------
		setTitle("Créditos");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(new Dimension(330,200));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
