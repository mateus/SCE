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
import br.edu.ifsudestemg.tsi.eventos.TratadorEventoCadastrarCargo;
import br.edu.ifsudestemg.tsi.eventos.TratadorEventoValidarNumero;

/**
 * Cria conteúdo gráfico da tela de cadastrar cargo
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see JDialog
 */
public class TelaCadastrarCargo extends JDialog {
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
	private JLabel tituloLabel = new JLabel("Cadastrar Cargo");
	private JLabel nomeCargoLabel = new JLabel(" Cargo: ");
	private JLabel qtdDigitosLabel = new JLabel(" Digitos: ");

	//Fonte
	private Font fonte = new Font("Arial", Font.BOLD, 20);
	private Font fonte2 = new Font("Arial", Font.BOLD, 12);

	//Campos de texto
	private JTextField nomeCargoField = new JTextField(new Maxlength(20),"",10);
	private JTextField qtdDigitosField = new JTextField(new Maxlength(1),"",5);

	//Botão 
	private JButton cadastrar = new JButton("Cadastrar");
	private JButton limpar = new JButton("Limpar");

	//Grid
	private GridBagConstraints gbc = new GridBagConstraints();

	/**
	 * Construtor default que criará todo conteúdo gráfico da tela
	 */
	public TelaCadastrarCargo(){
		super();
		TratadorEventoCadastrarCargo tratadorEventos = new TratadorEventoCadastrarCargo(this);
		TratadorEventoValidarNumero tratadorEventosNumero = new TratadorEventoValidarNumero(this);
		
		//-----------------------------TITULO DO DIALOGO-----------------------------
		tituloLabel.setFont(fonte);
		painelTitulo.add(tituloLabel);

		//GBC
		gbc.insets = new Insets(5, 15, 10, 10); // Setando espaços para o GridBadLayout 
		gbc.fill = GridBagConstraints.BOTH;  // Preenche todo a coluna

		//-------------------------------CAMPO NOME CARGO-----------------------------
		nomeCargoLabel.setFont(fonte2);
		gbc.gridx = 0;
		gbc.gridy = 0;
		painelItens.add(nomeCargoLabel,gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		nomeCargoField.addKeyListener(tratadorEventos);
		painelItens.add(nomeCargoField,gbc);

		//-------------------------------CAMPO QTD DIGITOS-----------------------------
		qtdDigitosLabel.setFont(fonte2);
		gbc.gridx = 0;
		gbc.gridy = 1;
		painelItens.add(qtdDigitosLabel,gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		qtdDigitosField.addKeyListener(tratadorEventosNumero);
		qtdDigitosField.addKeyListener(tratadorEventos);
		painelItens.add(qtdDigitosField,gbc);

		//-------------------------------ADICIONANDO BOTOES-----------------------------
		limpar.addActionListener(tratadorEventos);
		cadastrar.addActionListener(tratadorEventos);
		cadastrar.setEnabled(false);
		limpar.setFocusable(false);
		cadastrar.setFocusable(false);
		painelBotao.add(limpar);
		painelBotao.add(cadastrar);

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
		setTitle("Cadastrar Cargo");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/** Retorna referência do <code>JTextField</code> nomeCargoField
	 * 
	 * @return referência do <code>JTextField</code> nomeCargoField
	 * 
	 * @see JTextField
	 */
	public JTextField getNomeCargoField() {
		return nomeCargoField;
	}

	/** Retorna referência do <code>JTextField</code> qtdDigitosField
	 * 
	 * @return referência do <code>JTextField</code> qtdDigitosField
	 * 
	 * @see JTextField
	 */
	public JTextField getQtdDigitosField() {
		return qtdDigitosField;
	}

	/** Retorna referência do <code>JButton</code> cadastrar
	 * 
	 * @return referência do <code>JButton</code> cadastrar
	 * 
	 * @see JButton
	 */
	public JButton getCadastrar() {
		return cadastrar;
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
