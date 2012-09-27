package br.edu.ifsudestemg.tsi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import br.edu.ifsudestemg.tsi.eventos.TratadorEventoEditarCadastro;
import br.edu.ifsudestemg.tsi.persistencia.CargoBD;
import br.edu.ifsudestemg.tsi.persistencia.PartidoBD;

/**
 * Cria conteúdo gráfico da tela de editar cadastro de um candidato
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see JDialog
 */
public class TelaEditarCadastro extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Icones
	private ImageIcon iconePersonalizado = new ImageIcon("icones/logo2.png" );
	private ImageIcon usuario;

	//Paineis
	private JPanel painelTitulo = new JPanel();
	private JPanel painelItens = new JPanel(new GridLayout(3,2));
	private JPanel painelNome = new JPanel();
	private JPanel painelBotao = new JPanel();
	private JPanel painelImagem = new JPanel(new GridLayout(1,1));
	private JPanel painelCentral = new JPanel();

	//Rótulos
	private JLabel tituloLabel = new JLabel("Alterar Candidatos");
	private JLabel nomeCandidatoLabel = new JLabel(" Nome: ");
	private JLabel cargoLabel = new JLabel(" Cargo: ");
	private JLabel partidoLabel = new JLabel(" Partido: ");
	private JLabel numeroLabel = new JLabel(" Número: ");
	/**
	 * Rótulo da imagem do candidato 
	 */
	public JLabel imagemClicavel;

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
	private JButton alterar = new JButton("Alterar");
	private JButton limpar = new JButton("Limpar");

	//Database
	private PartidoBD partidoBD = new PartidoBD();
	private CargoBD cargoBD = new CargoBD();
	private ResultSet resultadoCargo,resultadoPartido;
	
	//Auxiliares
	private int numeroAntigo;
	private String diretorioAntigo;
	private String cargoAntigo;
	
	/**
	 * Construtor default que criará todo conteúdo gráfico da tela
	 * 
	 * @param nome <code>String</code> com o nome do candidato
	 * @param cargo <code>String</code> com com o cargo do candidato
	 * @param partido <code>String</code> com com o partido do candidato
	 * @param numero <code>String</code> com com o número do candidato
	 * @param diretorio <code>String</code> com o diretório da imagem do candidato
	 */
	public TelaEditarCadastro(String nome,String cargo,String partido,String numero,String diretorio) {
		super();
		
		setNumeroAntigo(Integer.parseInt(numero));
		setDiretorioAntigo(diretorio);
		setCargoAntigo(cargo);
		
		TratadorEventoEditarCadastro tratadorEventos = new TratadorEventoEditarCadastro(this);

		//-----------------------------TITULO DO DIALOGO-----------------------------
		tituloLabel.setFont(fonte);
		painelTitulo.add(tituloLabel);

		//----------------------------CAMPO NOME CANDIDATO---------------------------
		nomeCandidatoLabel.setFont(fonte2);
		painelNome.add(nomeCandidatoLabel);
		nomeCandidatoField.setText(nome);
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
		
		// Encontra qual o indice do cargo do candidato
		int i = 0;
		for(i=0; i < cargosStr.length; i++){
			if(cargosStr[i].equalsIgnoreCase(cargo))
				break;
		}
		
		cargoCB.setSelectedIndex(i);
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
		
		// Encontra qual o indice do cargo do candidato
		int k = 0;
		for(k=0; k < partidosStr.length; k++){
			if(partidosStr[k].equalsIgnoreCase(partido))
				break;
		}
		
		partidosCB.setSelectedIndex(k);
		partidosCB.addItemListener(tratadorEventos);
		painelItens.add(partidosCB);

		//---------------------------------CAMPO NUMERO-------------------------------
		numeroField = new JTextField(new Maxlength(cargoBD.consultaDigitos(cargoCB.getItemAt(cargoCB.getSelectedIndex()))),"",5);
		numeroField.setText("" + partidoBD.consultaLegenda(partidosCB.getItemAt(partidosCB.getSelectedIndex())));
		numeroField.setText(numero);
		numeroField.addMouseListener(tratadorEventos);
		numeroLabel.setFont(fonte2);
		painelItens.add(numeroLabel);
		painelItens.add(numeroField);


		//------------------------------ADICIONANDO IMAGEM-----------------------------
		usuario = new ImageIcon(diretorio);
		imagemClicavel = new JLabel(usuario);

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
		alterar.addActionListener(tratadorEventos);
		alterar.setEnabled(true);
		limpar.setFocusable(false);
		alterar.setFocusable(false);
		painelBotao.add(limpar);
		painelBotao.add(alterar);

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
		setTitle("Alterar Candidatos");
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

	/** Retorna referência do <code>JButton</code> alterar
	 * 
	 * @return referência do <code>JButton</code> alterar
	 * 
	 * @see JButton 
	 */
	public JButton getAlterar() {
		return alterar;
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

	/** Retorna um <code>int</code> referente ao numero antigo
	 * 
	 * @return um <code>int</code> referente ao numero antigo
	 */
	public int getNumeroAntigo() {
		return numeroAntigo;
	}

	/** Adiciona o número antigo do candidato
	 * 
	 * @param numeroAntigo um <code>int</code> com o número antigo do candidato
	 */
	public void setNumeroAntigo(int numeroAntigo) {
		this.numeroAntigo = numeroAntigo;
	}

	/** Retorna uma <code>String</code> referente ao diretório da imagem antigo
	 * 
	 * @return uma <code>String</code> referente ao diretório da imagem antigo
	 */
	public String getDiretorioAntigo() {
		return diretorioAntigo;
	}

	/** Adiciona o diretório antigo do candidato
	 * 
	 * @param diretorioAntigo <code>String</code> com o diretório antigo do candidato
	 */
	public void setDiretorioAntigo(String diretorioAntigo) {
		this.diretorioAntigo = diretorioAntigo;
	}

	/** Adiciona o cargo antigo do candidato
	 * 
	 * @param cargoAntigo <code>String</code> com o cargo antigo do candidato
	 */
	public void setCargoAntigo(String cargoAntigo) {
		this.cargoAntigo = cargoAntigo;
	}

	/** Retorna uma <code>String</code> referente ao cargo antigo
	 * 
	 * @return uma <code>String</code> referente ao cargo antigo
	 */
	public String getCargoAntigo() {
		return cargoAntigo;
	}
	
}
