package br.edu.ifsudestemg.tsi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import br.edu.ifsudestemg.tsi.eventos.TratadorEventoSelecionaCargos;
import br.edu.ifsudestemg.tsi.persistencia.CargoBD;

import com.sun.java.swing.plaf.windows.WindowsBorders.DashedBorder;

/**
 * Cria conteúdo gráfico da tela de seleção de cargos
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see JDialog
 */
public class TelaVotacaoSelecionaCargos extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Icones
	private ImageIcon iconePersonalizado = new ImageIcon("icones/logo2.png" );

	//Paineis
	private JPanel painelTitulo = new JPanel();
	private JPanel painelItens = new JPanel();
	private JPanel painelBotaoesSeleciona = new JPanel(new GridBagLayout());
	private JPanel painelBotao = new JPanel();

	//Fonte
	private Font fonte2 = new Font("Arial", Font.BOLD, 12);

	//JList
	private JList<String> cargos = new JList<String>(new DefaultListModel<String>());
	private JList<String> cargosSelecionados = new JList<String>(new DefaultListModel<String>());

	//Scrollbar
	private JScrollPane barraRolagemCargos ;
	private JScrollPane barraRolagemCargosSelecionados;

	//Botão 
	private JButton iniciar = new JButton("Iniciar");
	private JButton limpar = new JButton("Limpar");
	private JButton adiciona = new JButton(">>");
	private JButton remove = new JButton("<<");

	//Database
	private CargoBD cargoBD = new CargoBD();
	private ResultSet resultadoCargo;

	//Grid
	private GridBagConstraints gbc = new GridBagConstraints();
	
	//Lista e Arrays
	private List<String> cargosLista = new ArrayList<String>(); 
	private ArrayList<String> cargosParaVotacao = new ArrayList<String>(); 

	/**
	 * Construtor default que criará todo conteúdo gráfico da tela
	 */
	public TelaVotacaoSelecionaCargos() {
		super();
		
		TratadorEventoSelecionaCargos tratadorEvento = new TratadorEventoSelecionaCargos(this);

		//GBC
		gbc.insets = new Insets(10, 3, 3, 10); // Setando espaços para o GridBadLayout 
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.BOTH;  // Preenche todo a coluna 
		
		//----------------------------------JLIST-------------------------------------
		resultadoCargo = cargoBD.consultaCargo(); // PESQUISA TODOS OS NOMES

		try {
			while(resultadoCargo.next())
			{
				cargosLista.add( resultadoCargo.getString("nome") ); // Adiciona na lista os nomes 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		}
				
		DefaultListModel<String> modeloLista = (DefaultListModel<String>) cargos.getModel();
		
		//Carrega o array de desordenados
		ArrayList<String> cargosDesordenados = new ArrayList<String>();
		for(int i = 0; i < cargosLista.size(); i++)
			cargosDesordenados.add(cargosLista.get(i));
		
		//Ordena o array
		Collections.sort(cargosDesordenados);
		
		//Adicionar o array ordenado
		for(int i=0; i < cargosDesordenados.size(); i++)
			modeloLista.addElement(cargosDesordenados.get(i));
						
		cargos.setModel(modeloLista); // seta modelo
		cargos.setFont(fonte2); // fonte da lista
		cargos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // uma unica seleção
		
		cargosSelecionados.setFont(fonte2);
		
		barraRolagemCargos = new JScrollPane(cargos);
		barraRolagemCargosSelecionados = new JScrollPane(cargosSelecionados);
		
		barraRolagemCargos.setPreferredSize(new Dimension(200,220));
		barraRolagemCargosSelecionados.setPreferredSize(new Dimension(200,220));
		
		barraRolagemCargos.setBorder(new TitledBorder(
			      DashedBorder.createGrayLineBorder(),
			      "Cargos não selecionados",
			      TitledBorder.CENTER, 
			      TitledBorder.TOP,
			      new Font ("Arial", Font.PLAIN, 11),
			      Color.BLACK));
		
		barraRolagemCargosSelecionados.setBorder(new TitledBorder(
				  DashedBorder.createGrayLineBorder(),
			      "Cargos selecionados",
			      TitledBorder.CENTER, 
			      TitledBorder.TOP,
			      new Font ("Arial", Font.PLAIN, 11),
			      Color.BLACK));
						
		//------------------------------ADICIONANDO BOTOES-----------------------------
		iniciar.addActionListener(tratadorEvento);
		limpar.addActionListener(tratadorEvento);
		adiciona.addActionListener(tratadorEvento);
		remove.addActionListener(tratadorEvento);
		
		iniciar.setEnabled(false);
		limpar.setFocusable(false);
		iniciar.setFocusable(false);
		painelBotao.add(limpar);
		painelBotao.add(iniciar);

		adiciona.setFocusable(false);
		remove.setFocusable(false);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		painelBotaoesSeleciona.add(adiciona,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		painelBotaoesSeleciona.add(remove,gbc);

		//----------------------------ADICIONANDO COR BRANCA---------------------------
		painelTitulo.setBackground(Color.WHITE);
		painelBotaoesSeleciona.setBackground(Color.WHITE);
		painelBotao.setBackground(Color.WHITE);
		painelItens.setBackground(Color.WHITE);

		//---------------------------ADICIOANANDO OS PAINEIS--------------------------
		add(painelTitulo,BorderLayout.NORTH);
		painelItens.add(barraRolagemCargos,BorderLayout.WEST);
		painelItens.add(painelBotaoesSeleciona,BorderLayout.CENTER);
		painelItens.add(barraRolagemCargosSelecionados,BorderLayout.EAST);
		add(painelItens);
		add(painelBotao,BorderLayout.SOUTH);

		//------------------------------ICONE DA JANELA-----------------------------
		setIconImage( iconePersonalizado.getImage() );

		//-----------------------SETANDO ATRIBUTOS DO JFRAME------------------------
		pack();
		setTitle("Cargos para a eleição");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/** Retorna referência do {@code JList<String>} cargos
	 * 
	 * @return referência do {@code JList<String>} cargos
	 * 
	 * @see JList 
	 */
	public JList<String> getCargos() {
		return cargos;
	}

	/** Retorna referência do {@code JList<String>} cargosSelecionados
	 * 
	 * @return referência do {@code JList<String>} cargosSelecionados
	 * 
	 * @see JList 
	 */
	public JList<String> getCargosSelecionados() {
		return cargosSelecionados;
	}

	/** Retorna referência do <code>JButton</code> iniciar
	 * 
	 * @return referência do <code>JButton</code> iniciar
	 * 
	 * @see JButton 
	 */
	public JButton getIniciar() {
		return iniciar;
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

	/** Retorna referência do <code>JButton</code> remove
	 * 
	 * @return referência do <code>JButton</code> remove
	 * 
	 * @see JButton 
	 */
	public JButton getAdiciona() {
		return adiciona;
	}

	/** Retorna referência do <code>JButton</code> remove
	 * 
	 * @return referência do <code>JButton</code> remove
	 * 
	 * @see JButton 
	 */
	public JButton getRemove() {
		return remove;
	}

	/** Retorna referência do {@code ArrayList<String>} cargosParaVotacao
	 * 
	 * @return referência do {@code ArrayList<String>} cargosParaVotacao
	 * 
	 * @see ArrayList 
	 */
	public ArrayList<String> getCargosParaVotacao() {
		return cargosParaVotacao;
	}
}
