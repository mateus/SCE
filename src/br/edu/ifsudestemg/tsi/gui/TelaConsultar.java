package br.edu.ifsudestemg.tsi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.edu.ifsudestemg.tsi.classes.Maxlength;
import br.edu.ifsudestemg.tsi.eventos.TratadorEventoConsultar;
import br.edu.ifsudestemg.tsi.eventos.TratadorEventoValidarNumero;

/**
 * Cria conteúdo gráfico da tela de consulta candidatos
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see JDialog
 */
public class TelaConsultar extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Icones
	private ImageIcon iconePersonalizado = new ImageIcon("icones/logo2.png" );

	//Paineis
	private JPanel painelTitulo = new JPanel();
	private JPanel painelItens = new JPanel();
	private JPanel painelResultado = new JPanel(new GridLayout(4,2));

	//Rótulos
	private JLabel tituloLabel = new JLabel("Consultar Candidatos");
	private JLabel tituloLabelExcluir = new JLabel("Excluir Candidatos");
	private JLabel tituloLabelAlterar = new JLabel("Editar Candidatos");
	private JLabel nomeLabel = new JLabel("Nome: ");
	private JLabel numeroLabel = new JLabel("ou Número:");

	//Fonte
	private Font fonte = new Font("Arial", Font.BOLD, 20); 
	
	//Maxlength
	Maxlength maxNome = new Maxlength(50);
	Maxlength maxNumero = new Maxlength(5);

	//Campos de texto
	private JTextField numeroField = new JTextField(maxNumero,"", 6);
	private JTextField nomeCandidatoField = new JTextField(maxNome,"",20);
	

	//Dados para a tabela
	/**
	 * <code>String</code> com as colunas de dados da tabela
	 */
	public static String colunasTabela[] = {"Nome Candidato", "Cargo", "Partido", "Número"};
	/**
	 * <code>String</code> que conterá todos as informações dos candidatos
	 */
	public static String dadosTabela[][] = new String[0][0]; 

	//Tabela de Resultados
	private JTable tabela;

	//ScrollPane
	private JScrollPane barraRolagem;

	//Botão 
	private JButton pesquisar = new JButton("Pesquisar");
	
	//Tipo da ação
	private boolean excluir;
	private boolean alterar;

	/**
	 * Construtor default que criará todo conteúdo gráfico da tela
	 * 
	 * @param excluir <code>true</code> para operação de exclusão 
	 * @param alterar <code>true</code> para operação de alteração
	 * @throws SQLException para exceções de banco de dados
	 */
	public TelaConsultar(boolean excluir,boolean alterar) throws SQLException {
		super();
		
		this.excluir = excluir;
		this.alterar = alterar;

		TratadorEventoConsultar tratadorEventos = new TratadorEventoConsultar(this);
		TratadorEventoValidarNumero tratadorEventoNumero = new TratadorEventoValidarNumero(this);

		//-----------------------------TITULO DO DIALOGO-----------------------------
		tituloLabel.setFont(fonte);
		tituloLabelExcluir.setFont(fonte);
		tituloLabelAlterar.setFont(fonte);

		if(isAlterar())
			painelTitulo.add(tituloLabelAlterar);
		else
		if(isExcluir())
			painelTitulo.add(tituloLabelExcluir);
		else
			painelTitulo.add(tituloLabel);

		//-----------------------------CAMPOS PARA PESQUISA--------------------------
		pesquisar.addActionListener(tratadorEventos);
		pesquisar.setFocusable(false);
		numeroField.addKeyListener(tratadorEventoNumero);
		numeroField.addKeyListener(tratadorEventos);
		numeroField.addActionListener(tratadorEventos);

		painelItens.add(nomeLabel);
		painelItens.add(nomeCandidatoField);
		nomeCandidatoField.addKeyListener(tratadorEventos);
		painelItens.add(numeroLabel);
		painelItens.add(numeroField);
		painelItens.add(pesquisar);

		//---------------------------TABELA DE RESULTADOS-----------------------------
		tabela = new JTable(new DefaultTableModel(dadosTabela,colunasTabela)){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex,int colIndex){
				return false; // Para não editar celula.
			};
		};
		
		tabela.getColumnModel().getColumn(0).setPreferredWidth(150);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(130);
		tabela.setPreferredScrollableViewportSize(new Dimension(500, 200));
			
		barraRolagem = new JScrollPane(tabela);	
		tabela.addMouseListener(tratadorEventos);

		//---------------------------ADICIOANANDO OS PAINEIS--------------------------
		painelResultado.add(barraRolagem);
		painelItens.add(painelResultado);

		add(painelTitulo,BorderLayout.NORTH);
		add(painelItens,BorderLayout.CENTER);

		//----------------------------ADICIONANDO COR BRANCA--------------------------
		painelTitulo.setBackground(Color.WHITE);
		painelItens.setBackground(Color.WHITE);
		painelResultado.setBackground(Color.WHITE);

		//--------------------------------ICONE DA JANELA-----------------------------
		setIconImage( iconePersonalizado.getImage() );

		//------------------------SETANDO ATRIBUTOS DO JDIALOG------------------------
		if(isAlterar())
			setTitle("Alterar Candidatos");
		else
		if(isExcluir())
			setTitle("Excluir Candidatos");
		else
			setTitle("Consultar Candidatos");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(new Dimension(550,350));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		repaint();
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

	/** Retorna referência do <code>JButton</code> pesquisar
	 * 
	 * @return referência do <code>JButton</code> pesquisar
	 * 
	 * @see JButton
	 */
	public JButton getPesquisar() {
		return pesquisar;
	}

	/** Retorna referência do <code>JTable</code> tabela
	 * 
	 * @return referência do <code>JTable</code> tabela
	 * 
	 * @see JTable
	 */
	public JTable getTabela() {
		return tabela;
	}

	/** Retorna referência do <code>boolean</code> excluir
	 * 
	 * @return referência do <code>boolean</code> excluir
	 * 
	 */
	public boolean isExcluir() {
		return excluir;
	}

	/** Retorna referência do <code>boolean</code> alterar
	 * 
	 * @return referência do <code>boolean</code> alterar
	 * 
	 */
	public boolean isAlterar() {
		return alterar;
	}
	
	
}
