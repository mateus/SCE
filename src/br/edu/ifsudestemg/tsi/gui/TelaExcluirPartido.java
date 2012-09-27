package br.edu.ifsudestemg.tsi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.edu.ifsudestemg.tsi.classes.Maxlength;
import br.edu.ifsudestemg.tsi.eventos.TratadorEventoExcluirPartido;
import br.edu.ifsudestemg.tsi.eventos.TratadorEventoValidarNumero;

/**
 * Cria conteúdo gráfico da tela de excluir partido
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see JDialog
 */
public class TelaExcluirPartido extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Icones
	private ImageIcon iconePersonalizado = new ImageIcon("icones/logo2.png" );

	//Paineis
	private JPanel painelTitulo = new JPanel();
	private JPanel painelItens = new JPanel(new GridBagLayout());
	private JPanel painelBotao = new JPanel();

	//Rótulos
	private JLabel tituloLabel = new JLabel("Excluir Partido");
	private JLabel siglaLabel = new JLabel(" Sigla: ");
	private JLabel numeroLabel = new JLabel(" Legenda: ");

	//Fonte
	private Font fonte = new Font("Arial", Font.BOLD, 20);
	private Font fonte2 = new Font("Arial", Font.BOLD, 12);

	//Campos de texto
	private JTextField siglaField = new JTextField(new Maxlength(10),"",10);
	private JTextField numeroField = new JTextField(new Maxlength(2),"",10);

	//Botão 
	private JButton excluir = new JButton("Excluir");
	private JButton limpar = new JButton("Limpar");

	//Grid
	private GridBagConstraints gbc = new GridBagConstraints();

	/**
	 * Construtor default que criará todo conteúdo gráfico da tela
	 */
	public TelaExcluirPartido() {
		super();
		TratadorEventoExcluirPartido tratadorEventos = new TratadorEventoExcluirPartido(this);
		TratadorEventoValidarNumero tratadorEventosValidaNumero = new TratadorEventoValidarNumero(this);

		//-----------------------------TITULO DO DIALOGO-----------------------------
		tituloLabel.setFont(fonte);
		painelTitulo.add(tituloLabel);

		//GBC
		gbc.insets = new Insets(5, 15, 10, 10); // Setando espaços para o GridBadLayout 
		gbc.fill = GridBagConstraints.BOTH;  // Preenche todo a coluna

		//-------------------------------CAMPO SIGLA PARTIDO---------------------------
		siglaLabel.setFont(fonte2);
		gbc.gridx = 0;
		gbc.gridy = 0;
		painelItens.add(siglaLabel,gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		siglaField.addKeyListener(tratadorEventos);
		painelItens.add(siglaField,gbc);

		//-------------------------------CAMPO NUMERO PARTIDO---------------------------
		numeroLabel.setFont(fonte2);
		gbc.gridx = 0;
		gbc.gridy = 1;
		painelItens.add(numeroLabel,gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		numeroField.addKeyListener(tratadorEventos);
		numeroField.addKeyListener(tratadorEventosValidaNumero);
		painelItens.add(numeroField,gbc);

		//-------------------------------ADICIONANDO BOTOES-----------------------------
		limpar.addActionListener(tratadorEventos);
		excluir.addActionListener(tratadorEventos);
		excluir.setEnabled(false);
		limpar.setFocusable(false);
		excluir.setFocusable(false);
		painelBotao.add(limpar);
		painelBotao.add(excluir);

		//----------------------------ADICIONANDO COR BRANCA-----------------------------
		painelTitulo.setBackground(Color.WHITE);
		painelItens.setBackground(Color.WHITE);
		painelBotao.setBackground(Color.WHITE);

		//---------------------------ADICIOANANDO OS PAINEIS-----------------------------
		add(painelTitulo,BorderLayout.NORTH);
		add(painelItens);
		add(painelBotao,BorderLayout.SOUTH);

		//--------------------------------ICONE DA JANELA-------------------------------
		setIconImage( iconePersonalizado.getImage() );

		//--------------------------SETANDO ATRIBUTOS DO JFRAME--------------------------
		pack();
		setTitle("Excluir Partido");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/** Retorna referência do <code>JTextField</code> siglaField
	 * 
	 * @return referência do <code>JTextField</code> siglaField
	 * 
	 * @see JTextField 
	 */
	public JTextField getSiglaField() {
		return siglaField;
	}

	/** Retorna referência do <code>JTextField</code> numeroField
	 * 
	 * @return referência do <code>JTextField</code> numeroField
	 * 
	 * @see JTextField 
	 */
	public JTextField getNumeroField() {
		return numeroField;
	}

	/** Retorna referência do <code>JButton</code> excluir
	 * 
	 * @return referência do <code>JButton</code> excluir
	 * 
	 * @see JButton 
	 */
	public JButton getExcluir() {
		return excluir;
	}

	/** Retorna referência do <code>JButton</code> limpar
	 * 
	 * @return referência do <code>JButton</code> limpar
	 * 
	 * @see JButton 
	 */
	public JButton getLimpar() {
		return limpar;
	}
	
}
