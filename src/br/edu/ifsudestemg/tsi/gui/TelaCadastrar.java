package br.edu.ifsudestemg.tsi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.edu.ifsudestemg.tsi.classes.Maxlength;
import br.edu.ifsudestemg.tsi.eventos.TratadorEventoCadastrar;
import br.edu.ifsudestemg.tsi.persistencia.CargoBD;
import br.edu.ifsudestemg.tsi.persistencia.PartidoBD;

/**
 * Cria conteúdo gráfico da tela de cadastrar candidatos
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see JDialog
 */
public class TelaCadastrar extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Icones
	private ImageIcon iconePersonalizado = new ImageIcon("icones/logo2.png" );
	private ImageIcon usuario = new ImageIcon("imagens//usuario.png");

	//Paineis
	private JPanel painelTitulo = new JPanel();
	private JPanel painelItens = new JPanel(new GridLayout(3,2));
	private JPanel painelNome = new JPanel();
	private JPanel painelBotao = new JPanel();
	private JPanel painelImagem = new JPanel(new GridLayout(1,1));
	private JPanel painelCentral = new JPanel();

	//Rótulos
	private JLabel tituloLabel = new JLabel("Cadastrar Candidatos");
	private JLabel nomeCandidatoLabel = new JLabel(" Nome: ");
	private JLabel cargoLabel = new JLabel(" Cargo: ");
	private JLabel partidoLabel = new JLabel(" Partido: ");
	private JLabel numeroLabel = new JLabel(" Número: ");
	/**
	 * Rótudo que contém a imagem do usuário
	 */
	public JLabel imagemClicavel = new JLabel(usuario);

	//Fonte
	private Font fonte = new Font("Arial", Font.BOLD, 20);
	private Font fonte2 = new Font("Arial", Font.BOLD, 12);

	//Campos de texto
	private JTextField nomeCandidatoField = new JTextField(new Maxlength(50),"",24);
	private JTextField numeroField;

	//Combo Box
	private String[] cargosStr;
	private String[] partidosStr;
	private List<String> cargos = new ArrayList<String>(); 
	private List<String> partidos = new ArrayList<String>(); 
	private JComboBox<String> partidosCB;
	private JComboBox<String> cargoCB;

	//Botão 
	private JButton cadastrar = new JButton("Cadastrar");
	private JButton limpar = new JButton("Limpar");

	//Database
	private PartidoBD partidoBD = new PartidoBD();
	private CargoBD cargoBD = new CargoBD();
	private ResultSet resultadoCargo,resultadoPartido;

	//Throws para mascara
	
	/** Construtor default que criará todo conteúdo gráfico da tela
	 * 
	 * @throws ParseException exceção para conversão de tipos
	 */
	public TelaCadastrar() throws ParseException {
		super();
		TratadorEventoCadastrar tratadorEventos = new TratadorEventoCadastrar(this);

		//-----------------------------TITULO DO DIALOGO-----------------------------
		tituloLabel.setFont(fonte);
		painelTitulo.add(tituloLabel);

		//----------------------------CAMPO NOME CANDIDATO---------------------------
		nomeCandidatoLabel.setFont(fonte2);
		painelNome.add(nomeCandidatoLabel);
		painelNome.add(nomeCandidatoField);

		//---------------------------------CAMPO CARGO--------------------------------
		cargoLabel.setFont(fonte2);
		painelItens.add(cargoLabel);
		
		resultadoCargo = cargoBD.consultaCargo(); // PESQUISA TODOS OS NOMES
		
		try {
			while(resultadoCargo.next())
			{
				cargos.add( resultadoCargo.getString("nome") ); // Adiciona na lista os nomes 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 

		cargosStr = (String[]) cargos.toArray (new String[0]);   // transfere para string
		cargoCB = new JComboBox<String>(cargosStr);
		
		cargoCB.addItemListener(tratadorEventos);
		painelItens.add(cargoCB);

		//--------------------------------CAMPO PARTIDOS------------------------------
		partidoLabel.setFont(fonte2);
		painelItens.add(partidoLabel);
		
		resultadoPartido = partidoBD.consultaPartido(); // PESQUISA TODOS OS NOMES
		
		try {
			while(resultadoPartido.next())
			{
				partidos.add( resultadoPartido.getString("sigla") ); // Adiciona na lista os nomes 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 

		partidosStr = (String[]) partidos.toArray (new String[0]);   // transfere para string
		partidosCB = new JComboBox<String>(partidosStr);
		
		partidosCB.addItemListener(tratadorEventos);
		painelItens.add(partidosCB);

		//---------------------------------CAMPO NUMERO-------------------------------
		numeroField  = new JTextField(new Maxlength(cargoBD.consultaDigitos(cargoCB.getItemAt(cargoCB.getSelectedIndex()))),"",5);
		numeroField.setText("" + partidoBD.consultaLegenda(partidosCB.getItemAt(partidosCB.getSelectedIndex())));
		numeroField.addMouseListener(tratadorEventos);
		numeroLabel.setFont(fonte2);
		painelItens.add(numeroLabel);
		painelItens.add(numeroField);


		//------------------------------ADICIONANDO IMAGEM-----------------------------

		imagemClicavel.setBorder(BorderFactory.createTitledBorder(""));
		imagemClicavel.addMouseListener(tratadorEventos);
		imagemClicavel.setToolTipText("Clique para adicionar uma imagem");
		painelImagem.add(imagemClicavel);

		numeroField.addMouseListener(tratadorEventos);
		nomeCandidatoField.addMouseListener(tratadorEventos);
		numeroField.addKeyListener(tratadorEventos);
		nomeCandidatoField.addKeyListener(tratadorEventos);

		//------------------------------ADICIONANDO BOTOES-----------------------------
		limpar.addActionListener(tratadorEventos);
		cadastrar.addActionListener(tratadorEventos);
		cadastrar.setEnabled(false);
		limpar.setFocusable(false);
		cadastrar.setFocusable(false);
		painelBotao.add(limpar);
		painelBotao.add(cadastrar);

		//----------------------------ADICIONANDO COR BRANCA---------------------------
		painelTitulo.setBackground(Color.WHITE);
		painelItens.setBackground(Color.WHITE);
		painelBotao.setBackground(Color.WHITE);
		painelImagem.setBackground(Color.WHITE);
		painelCentral.setBackground(Color.WHITE);
		painelNome.setBackground(Color.WHITE);

		//---------------------------ADICIOANANDO OS PAINEIS--------------------------
		add(painelTitulo,BorderLayout.NORTH);
		painelCentral.add(painelNome,BorderLayout.NORTH);
		painelCentral.add(painelItens,BorderLayout.CENTER);
		painelCentral.add(painelImagem,BorderLayout.EAST);
		add(painelCentral,BorderLayout.CENTER);
		add(painelBotao,BorderLayout.SOUTH);

		//------------------------------ICONE DA JANELA-----------------------------
		setIconImage( iconePersonalizado.getImage() );

		//-----------------------SETANDO ATRIBUTOS DO JFRAME------------------------
		setTitle("Cadastrar Candidatos");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(new Dimension(360,400));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/** Retorna referência do <code>JLabel</code> imagemClicavel
	 * 
	 * @return referência do <code>JLabel</code> imagemClicavel
	 * 
	 * @see JLabel
	 */
	public JLabel getImagemClicavel() {
		return imagemClicavel;
	}

	/** Retorna referência do <code>JTextField</code> nomeCandidatoField
	 * 
	 * @return referência do <code>JTextField</code> nomeCandidatoField
	 * 
	 * @see JTextField
	 */
	public JTextField getNomeCandidatoField() {
		return nomeCandidatoField;
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

	/** Retorna referência do {@code JComboBox<String>} partidosCB
	 * 
	 * @return referência do {@code JComboBox<String>} partidosCB
	 * 
	 * @see JComboBox
	 */
	public JComboBox<String> getPartidosCB() {
		return partidosCB;
	}

	/** Retorna referência do {@code JComboBox<String>} cargoCB
	 * 
	 * @return referência do {@code JComboBox<String>} cargoCB
	 * 
	 * @see JComboBox
	 */
	public JComboBox<String> getCargoCB() {
		return cargoCB;
	}

	/** Retorna referência do <code>JButton</code> limpar
	 * 
	 * @return referência do <code>JButton</code> limpar
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
