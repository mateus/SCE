package br.edu.ifsudestemg.tsi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXDatePicker;

import br.edu.ifsudestemg.tsi.classes.Maxlength;
import br.edu.ifsudestemg.tsi.eventos.TratadorEventoCadastrarPesquisa;
import br.edu.ifsudestemg.tsi.eventos.TratadorEventoCadastrarPesquisa.TratadorEventoCadastrarPesquisaKeyReleased;
import br.edu.ifsudestemg.tsi.eventos.TratadorEventoCadastrarPesquisa.TratadorEventoCadastrarPesquisaPerdaFoco;
import br.edu.ifsudestemg.tsi.eventos.TratadorEventoValidarNumero;
import br.edu.ifsudestemg.tsi.persistencia.CandidatoBD;
import br.edu.ifsudestemg.tsi.persistencia.CargoBD;

/**
 * Cria conteúdo gráfico da tela de cadastrar pesquisa
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see JDialog
 */
public class TelaCadastrarPesquisa extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Icones
	private ImageIcon iconePersonalizado = new ImageIcon("icones/logo2.png" );

	//Paineis
	private JPanel painelTitulo = new JPanel();
	private JPanel painelCentral = new JPanel();
	private JPanel painelItens = new JPanel(new GridBagLayout());
	private JPanel painelBotao = new JPanel();
	private JPanel painelTabela = new JPanel(new GridBagLayout());

	//Rótulos
	private JLabel tituloLabel = new JLabel("Cadastrar Pesquisa Eleitoral");
	private JLabel cargoLabel = new JLabel("Cargo: ");
	private JLabel dataInicioLabel = new JLabel("Início da Pesquisa: ");
	private JLabel dataFimLabel = new JLabel("Fim da Pesquisa: ");
	private JLabel votosBrancoNuloLabel = new JLabel("<html>Branco/<br>Nulo</html>");
	private JLabel naoSoubeNaoRespondeuLabel = new JLabel("<html>Não soube/<br>Não respondeu:</html>");
	private JLabel entrevistadosLabel = new JLabel("Entrevistados: ");
	private JLabel municipiosLabel = new JLabel("Municípios: ");

	//Fonte
	private Font fonte = new Font("Arial", Font.BOLD, 20);
	private Font fonte2 = new Font("Arial", Font.BOLD, 12);

	//Combo Box
	private String[] cargosStr;
	private List<String> cargos = new ArrayList<String>(); 
	private JComboBox<String> cargoCB;

	//Botão 
	private JButton cadastrar = new JButton("Cadastrar");
	private JButton limpar = new JButton("Limpar");
	private JButton aplicar = new JButton("Calcular");

	//Database
	private CandidatoBD candidatoBD = new CandidatoBD();
	private ResultSet resultado;

	//Campos de Texto
	private JTextField intencoesVotosField = new JTextField();
	private JTextField votosBrancoNuloField = new JTextField("0");
	private JTextField naoSoubeNaoRespondeuField = new JTextField("0");
	private JTextField entrevistadosField = new JTextField();
	private JTextField municipiosField = new JTextField("0");

	//Grid
	private GridBagConstraints gbc = new GridBagConstraints();
	private GridBagConstraints gbc2 = new GridBagConstraints();
	
	//Dados para a tabela
	/**
	 * <code>String</code> com as colunas de dados da tabela
	 */
	public static String colunasTabela[] = {"Nome Candidato - Número", "Intenções de Voto"};
	
	/**
	 * <code>String</code> que conterá todos as informações dos candidatos
	 */
	public static String dadosTabela[][] = new String[0][0]; 

	//Tabela de Resultados
	private JTable tabela;
	
	//ScrollPane
	private JScrollPane barraRolagem;
	
	//Database
	private CargoBD cargoBD = new CargoBD();
	private ResultSet resultadoCargo;
	
	//Modelo para a Tabela
	private DefaultTableModel modeloTabela;
		
	//DataPicker	
	private JXDatePicker dataInicial = new JXDatePicker(System.currentTimeMillis());
	private JXDatePicker dataFinal = new JXDatePicker(System.currentTimeMillis());

	/**
	 * Construtor default que criará todo conteúdo gráfico da tela
	 */
	public TelaCadastrarPesquisa() {
		super();
		TratadorEventoCadastrarPesquisa tratadorEventos = new TratadorEventoCadastrarPesquisa(this);
		TratadorEventoValidarNumero tratadorEventosNumeros = new TratadorEventoValidarNumero(this);

		//-----------------------------TITULO DO DIALOGO-------------------------------
		tituloLabel.setFont(fonte);
		painelTitulo.add(tituloLabel);
		
		//GBC
		gbc.insets = new Insets(5, 15, 10, 10); // Setando espaços para o GridBadLayout 
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.BOTH;  // Preenche todo a coluna

		//GBC Tabela
		gbc2.insets = new Insets(5, 0, 10, 0); // Setando espaços para o GridBadLayout 
		gbc2.anchor = GridBagConstraints.LINE_START;

		//-------------------------ADICIONANDO CAMPO CARGO-----------------------------
		cargoLabel.setFont(fonte2);
		gbc.gridx = 0;
		gbc.gridy = 0;
		painelItens.add(cargoLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		
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

		painelItens.add(cargoCB, gbc);
		cargoCB.addItemListener(tratadorEventos);

		//------------------------ADICIONANDO TABELA CANDIDATO---------------------------
		gbc.gridx = 2;
		gbc.gridy = 0;
		
		tabela = new JTable(new DefaultTableModel(dadosTabela,colunasTabela)){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex,int colIndex){
				if(colIndex==0)
					return false; // Para não editar coluna 0.
				return true;
			};
		};
		
		tabela.setRowHeight(22); // Altura da linha
		
		//cria modelo da coluna 1
		TableColumn coluna = tabela.getColumnModel().getColumn(1);
		coluna.setCellEditor(new EditorDeCelula());
		
		modeloTabela = (DefaultTableModel)(tabela.getModel());
		modeloTabela.setRowCount(0);

		resultado = candidatoBD.consultaCandidatoCargo(cargoCB.getItemAt(cargoCB.getSelectedIndex()));

		Object[] linha = new Object[4];
		try {
			while(resultado.next()){
				linha[0] = resultado.getString("nome")+" - "+resultado.getString("numero");
				linha[1] = "0";

				modeloTabela.addRow(linha);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 
		
		tabela.getColumnModel().getColumn(0).setPreferredWidth(150);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
		tabela.setPreferredScrollableViewportSize(new Dimension(300, 208));
			
		barraRolagem = new JScrollPane(tabela);	
		tabela.addMouseListener(tratadorEventos);
				
		//-----------------------ADICIONANDO CAMPO DATA INICIAL------------------------
		dataInicioLabel.setFont(fonte2);
		gbc.gridx = 0;
		gbc.gridy = 1;
		painelItens.add(dataInicioLabel,gbc);

		dataInicial.setFormats(new String[] {"dd/MM/yyyy"});
		dataInicial.setLinkDate(System.currentTimeMillis(), "Data atual {0}");
		gbc.gridx = 1;
		gbc.gridy = 1;
		painelItens.add(dataInicial,gbc);

		//-----------------------ADICIONANDO CAMPO DATA FINAL-------------------------
		dataFimLabel.setFont(fonte2);
		gbc.gridx = 0;
		gbc.gridy = 2;
		painelItens.add(dataFimLabel,gbc);

		dataFinal.setFormats(new String[] {"dd/MM/yyyy"});
		dataFinal.setLinkDate(System.currentTimeMillis(), "Data atual {0}");
		gbc.gridx = 1;
		gbc.gridy = 2;
		painelItens.add(dataFinal,gbc);

		//-----------------------ADICIONANDO CAMPO BRANCO / NULO------------------------
		votosBrancoNuloLabel.setFont(fonte2);
		gbc.gridx = 0;
		gbc.gridy = 3;
		painelItens.add(votosBrancoNuloLabel,gbc);
		
		votosBrancoNuloField.addKeyListener(tratadorEventosNumeros);
		votosBrancoNuloField.addMouseListener(tratadorEventos);
		votosBrancoNuloField.addFocusListener(new TratadorEventoCadastrarPesquisaPerdaFoco(this));
		gbc.gridx = 1;
		gbc.gridy = 3;
		painelItens.add(votosBrancoNuloField,gbc);

		//----------------ADICIONANDO CAMPO NAO SOUBE / NAO RESPONDEU-------------------
		naoSoubeNaoRespondeuLabel.setFont(fonte2);
		gbc.gridx = 0;
		gbc.gridy = 4;
		painelItens.add(naoSoubeNaoRespondeuLabel,gbc);

		
		naoSoubeNaoRespondeuField.addKeyListener(tratadorEventosNumeros);
		naoSoubeNaoRespondeuField.addMouseListener(tratadorEventos);
		naoSoubeNaoRespondeuField.addFocusListener(new TratadorEventoCadastrarPesquisaPerdaFoco(this));
		gbc.gridx = 1;
		gbc.gridy = 4;
		painelItens.add(naoSoubeNaoRespondeuField,gbc);


		//-------------------------ADICIONANDO CAMPO MUNICIPIOS--------------------------
		municipiosLabel.setFont(fonte2);
		gbc.gridx = 0;
		gbc.gridy = 5;
		painelItens.add(municipiosLabel,gbc);

		municipiosField.addKeyListener(tratadorEventosNumeros);
		municipiosField.addMouseListener(tratadorEventos);
		municipiosField.addFocusListener(new TratadorEventoCadastrarPesquisaPerdaFoco(this));
		municipiosField.addKeyListener(new TratadorEventoCadastrarPesquisaKeyReleased(this));
		gbc.gridx = 1;
		gbc.gridy = 5;
		painelItens.add(municipiosField,gbc);
		
		//-----------------------ADICIONANDO CAMPO ENTRIVISTADOS-------------------------
		entrevistadosLabel.setFont(fonte2);
		gbc.gridx = 0;
		gbc.gridy = 6;
		painelItens.add(entrevistadosLabel,gbc);

		entrevistadosField.setEnabled(false);
		gbc.gridx = 1;
		gbc.gridy = 6;
		painelItens.add(entrevistadosField,gbc);

		//-------------------------------ADICIONANDO BOTOES-----------------------------
		limpar.addActionListener(tratadorEventos);
		cadastrar.addActionListener(tratadorEventos);
		cadastrar.setEnabled(false);
		limpar.setFocusable(false);
		cadastrar.setFocusable(false);
		painelBotao.add(limpar);
		painelBotao.add(cadastrar);
		aplicar.setFocusable(false);
		aplicar.addActionListener(tratadorEventos);

		//----------------------------ADICIONANDO COR BRANCA-----------------------------
		painelTitulo.setBackground(Color.WHITE);
		painelItens.setBackground(Color.WHITE);
		painelBotao.setBackground(Color.WHITE);
		painelCentral.setBackground(Color.WHITE);
		painelTabela.setBackground(Color.WHITE);

		//---------------------------ADICIOANANDO OS PAINEIS-----------------------------
		add(painelTitulo,BorderLayout.NORTH);
		
		painelCentral.add(painelItens,BorderLayout.WEST);
		
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		painelTabela.add(barraRolagem,gbc2);
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		painelTabela.add(aplicar,gbc2);
		
		painelCentral.add(painelTabela,BorderLayout.EAST);
		
		add(painelCentral);
		add(painelBotao,BorderLayout.SOUTH);
		
		//--------------------------------ICONE DA JANELA-------------------------------
		setIconImage( iconePersonalizado.getImage() );

		//--------------------------SETANDO ATRIBUTOS DO JFRAME--------------------------
		pack();
		setTitle("Cadastrar Pesquisa Eleitoral");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	//Classe interna para editar linha da tabela em INTENÇÕES DE VOTOS
	
	/**Editar linha da tabela em Intenções de Votos
	 * 
	 * @author Mateus Ferreira
	 * @author Vinicius Lehmann
	 * 
	 * @see AbstractCellEditor
	 * @see TableCellEditor
	 *
	 */
	public class EditorDeCelula extends AbstractCellEditor implements TableCellEditor {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//Este é o componente que irá manipular a edição do valor da célula
	    JComponent component = new JTextField(new Maxlength(10),"",15);
	   
	    //Construtor
	    
	    /**
	     * Construtor default que gerencia ações do campo intenções de votos 
	     */
	    public EditorDeCelula() {
	    	
	    	//Seleciona toda a linha
	    	component.addMouseListener(new MouseAdapter() {
	    		
	    		public void mousePressed(MouseEvent evento){
	    			JTextField editorCelula = (JTextField)evento.getSource();
	    			if(editorCelula.getText().equalsIgnoreCase("0"))
	    				editorCelula.setText("");
	    		}
			});
	    	
	    	//Retira letras e caracteres especiais
	    	component.addKeyListener(new KeyAdapter() {
	    		public void keyReleased(KeyEvent evento) {
	    			JTextField editorCelula = (JTextField)evento.getSource();
	    			editorCelula.setText(editorCelula.getText().replaceAll("[^0-9]", ""));
	    		}
			});
		}

		// Este método é chamado quando um valor de célula é editado pelo usuário.
	    public Component getTableCellEditorComponent(JTable table, Object value,
	            boolean isSelected, int rowIndex, int vColIndex) {
	        // 'value' é o valor contido na célula localizada em (rowIndex, vColIndex)

	        if (isSelected) {
	            //celular selecionada
	        }

	        // Configura componente com o valor específico
	        ((JTextField)component).setText((String)value);

	        // Retorn o valor configurado
	        return component;
	    }

	    // Este método é chamado quando a edição é concluída.
	    // Ela deve retornar o novo valor para ser armazenado na célula.
	    public Object getCellEditorValue() {
	    	JTextField editorCelula = (JTextField)component;
	    	if(editorCelula.getText().equalsIgnoreCase(""))
				editorCelula.setText("0");
	        return ((JTextField)component).getText();
	    }
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

	/** Retorna referência do <code>JXDatePicker</code> dataInicial
	 * 
	 * @return referência do <code>JXDatePicker</code> dataInicial
	 * 
	 * @see JXDatePicker
	 */
	public JXDatePicker getDataInicial() {
		return dataInicial;
	}

	/** Retorna referência do <code>JTextField</code> intencoesVotosField
	 * 
	 * @return referência do <code>JTextField</code> intencoesVotosField
	 * 
	 * @see JTextField
	 */
	public JTextField getIntencoesVotosField() {
		return intencoesVotosField;
	}

	/** Retorna referência do <code>JTextField</code> votosBrancoNuloField
	 * 
	 * @return referência do <code>JTextField</code> votosBrancoNuloField
	 * 
	 * @see JTextField
	 */
	public JTextField getVotosBrancoNuloField() {
		return votosBrancoNuloField;
	}

	/** Retorna referência do <code>JTextField</code> naoSoubeNaoRespondeuField
	 * 
	 * @return referência do <code>JTextField</code> naoSoubeNaoRespondeuField
	 * 
	 * @see JTextField
	 */
	public JTextField getNaoSoubeNaoRespondeuField() {
		return naoSoubeNaoRespondeuField;
	}

	/** Retorna referência do <code>JTextField</code> entrevistadosField
	 * 
	 * @return referência do <code>JTextField</code> entrevistadosField
	 * 
	 * @see JTextField
	 */
	public JTextField getEntrevistadosField() {
		return entrevistadosField;
	}

	/** Retorna referência do <code>JTextField</code> municipiosField
	 * 
	 * @return referência do <code>JTextField</code> municipiosField
	 * 
	 * @see JTextField
	 */
	public JTextField getMunicipiosField() {
		return municipiosField;
	}

	/** Retorna referência do <code>JXDatePicker</code> dataFinal
	 * 
	 * @return referência do <code>JXDatePicker</code> dataFinal
	 * 
	 * @see JXDatePicker
	 */
	public JXDatePicker getDataFinal() {
		return dataFinal;
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

	/** Retorna referência do <code>JButton</code> aplicar
	 * 
	 * @return referência do <code>JButton</code> aplicar
	 * 
	 * @see JButton
	 */
	public JButton getAplicar() {
		return aplicar;
	}		
}
