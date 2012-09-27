package br.edu.ifsudestemg.tsi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import br.edu.ifsudestemg.tsi.eventos.TratadorEventoApuracao;
import br.edu.ifsudestemg.tsi.persistencia.ApuracaoBD;

/**
 * Cria conteúdo gráfico da tela de Apuração de Votos 
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see JDialog
 */
public class TelaApuracao extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Icones
	private ImageIcon iconePersonalizado = new ImageIcon("icones/logo2.png" );

	//Paineis
	private JPanel painelTitulo = new JPanel();
	private JPanel painelItens = new JPanel(new GridBagLayout());
	private JPanel painelTotalEleitores = new JPanel();

	//Rótulos
	private JLabel tituloLabel = new JLabel("Apuração dos Votos");
	private JLabel cargoLabel = new JLabel("Cargo: ");
	private JLabel totalEleitoresLabel = new JLabel("Total de Eleitores: ");

	//Fonte
	private Font fonte = new Font("Arial", Font.BOLD, 20);
	private Font fonte2 = new Font("Arial", Font.BOLD, 12);
	private Font fonte3 = new Font("Arial", Font.PLAIN, 20);

	//ComboBox
	private JComboBox<String> cargoCB;
	private String[] cargosStr;

	//Database
	private ApuracaoBD apuracaoBD = new ApuracaoBD();
	private ResultSet resultadoCargo;

	//Grid
	private GridBagConstraints gbc = new GridBagConstraints();

	//Lista e Arrays
	private List<String> cargosLista = new ArrayList<String>();
	private List<String> nomeNumeroLista = new ArrayList<String>();

	//Painel chart
	private ChartPanel chartPanel = new ChartPanel(null);

	//Int
	private int brancos;
	private int nulos;

	/** 
	 * Construtor default que cria toda interface gráfica da tela de Apuração de Votos
	 */
	public TelaApuracao() {
		super();
		TratadorEventoApuracao tratadorEvento = new TratadorEventoApuracao(this);

		//GBC
		gbc.insets = new Insets(10, 3, 0, 0); // Setando espaços para o GridBadLayout 
		gbc.fill = GridBagConstraints.BOTH;
		//-----------------------------TITULO DO DIALOGO---------------------------
		tituloLabel.setFont(fonte);
		painelTitulo.add(tituloLabel);

		//----------------------------------CARGO-----------------------------------
		resultadoCargo = apuracaoBD.consultaCargo(); // PESQUISA TODOS OS NOMES

		cargoLabel.setFont(fonte2);

		try {
			while(resultadoCargo.next())
			{
				cargosLista.add( resultadoCargo.getString("nome") ); // Adiciona na lista os nomes 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 

		cargosStr = (String[]) cargosLista.toArray (new String[0]);   // transfere para string
		cargoCB = new JComboBox<String>(cargosStr);

		gbc.gridx = 0;
		gbc.gridy = 0;
		painelItens.add(cargoLabel,gbc);

		gbc.insets = new Insets(10, 0, 0, 40); // Setando espaços para o GridBadLayout
		gbc.gridx = 1;
		gbc.gridy = 0;
		cargoCB.addItemListener(tratadorEvento);
		painelItens.add(cargoCB,gbc);
		
		totalEleitoresLabel.setText("Total de Eleitores: "+apuracaoBD.consultaTotalEleitores());
		totalEleitoresLabel.setFont(fonte3);
		
		gbc.insets = new Insets(10, 100, 0, 0); // Setando espaços para o GridBadLayout 
		gbc.gridx = 2;
		gbc.gridy = 0;
		painelItens.add(totalEleitoresLabel,gbc);
		//-----------------------------ADICIONANDO GRAFICO--------------------------
		brancos = apuracaoBD.consultaVotosBranco(cargoCB.getItemAt(cargoCB.getSelectedIndex()));
		nulos = apuracaoBD.consultaVotosNulo(cargoCB.getItemAt(cargoCB.getSelectedIndex()));

		//Captura o nome e numero de cada candidato
		resultadoCargo = apuracaoBD.consultaCandidatos(cargoCB.getItemAt(cargoCB.getSelectedIndex()));

		try {
			while(resultadoCargo.next())
			{
				nomeNumeroLista.add( resultadoCargo.getString("nome") + " - (" + resultadoCargo.getString("numero") + ")"); // Adiciona na lista os ids 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 

		criaGrafico(this,brancos,nulos,nomeNumeroLista);

		System.out.print(apuracaoBD.consultaTotalEleitores());


		//----------------------------ADICIONANDO COR BRANCA------------------------
		painelTitulo.setBackground(Color.WHITE);
		painelItens.setBackground(Color.WHITE);
		painelTotalEleitores.setBackground(Color.WHITE);
		
		//---------------------------ADICIOANANDO OS PAINEIS------------------------
		add(painelTitulo,BorderLayout.NORTH);
		add(painelItens,BorderLayout.CENTER);
		//add(painelTotalEleitores, BorderLayout.SOUTH);

		//------------------------------ICONE DA JANELA-----------------------------
		setIconImage( iconePersonalizado.getImage() );

		//-----------------------SETANDO ATRIBUTOS DO JFRAME------------------------
		pack();
		setTitle("Relatório de Pesquisa Eleitoral");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	} 

	/** Cria primeiro gráfico de apuração dos vótos
	 * 
	 * @param gui <code>TelaApuracao</code> com a referência da tela de apuração dos votos
	 * @param brancos <code>int</code> com o número de votos em branco
	 * @param nulos <code>int</code> com o número de votos nulos
	 * @param nomeNumeroListaGrafico {@code List<String>} com a lista de nomes e números dos candidatos
	 */
	public void criaGrafico(TelaApuracao gui,
			int brancos, 
			int nulos,
			List<String> nomeNumeroListaGrafico){

		int total = apuracaoBD.consultaNumeroEleitores(cargoCB.getItemAt(cargoCB.getSelectedIndex()));
		double porcentagem = 100.0 / total;


		//Cria um dataSet para inserir os dados que serão passados para a criação do grafico tipo linha
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		if(nomeNumeroListaGrafico.size() > 0){
			for(int i=0; i < nomeNumeroListaGrafico.size(); i++){
				dataset.addValue(
						apuracaoBD.consultaVotos(
								cargoCB.getItemAt(cargoCB.getSelectedIndex()), 
								nomeNumeroListaGrafico.get(i).substring(nomeNumeroListaGrafico.get(i).indexOf("(")+1,
										nomeNumeroListaGrafico.get(i).indexOf(")")) ) * porcentagem, 
										nomeNumeroListaGrafico.get(i), 
										nomeNumeroListaGrafico.get(i).substring(nomeNumeroListaGrafico.get(i).indexOf("(")+1,
												nomeNumeroListaGrafico.get(i).indexOf(")")));

			}
		}

		if(total > 0){
			dataset.addValue(brancos * porcentagem, "EM BRANCO", "EM BRANCO");
			dataset.addValue(nulos * porcentagem, "NULOS", "NULOS");
		}


		JFreeChart grafico = ChartFactory.createBarChart(
				"",             // chart title
				"",                      // domain axis label
				"%",                         // range axis label
				dataset,                         // data
				PlotOrientation.HORIZONTAL,        // orientation
				true,                            // include legend
				true,                            // tooltips
				false                            // urls
				);

		grafico.setBackgroundPaint(Color.WHITE);

		CategoryPlot plot = (CategoryPlot) grafico.getPlot();

		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setRangeZeroBaselineVisible(true);
		plot.setNoDataMessage("Nenhum dado encontrado");

		CategoryPlot categoryPlot = grafico.getCategoryPlot();
		BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();

		// Modificando renderizado		
		//renderer.setMaximumBarWidth(.08); //tamanho max da barra 8% do grafico

		if(nomeNumeroListaGrafico.size() > 0){
			for(int i=0; i < nomeNumeroListaGrafico.size(); i++){
				renderer.setSeriesItemLabelGenerator(i, 
						new StandardCategoryItemLabelGenerator(" {2}" + "%  " + 
								apuracaoBD.consultaVotos(
										cargoCB.getItemAt(cargoCB.getSelectedIndex()), 
										nomeNumeroListaGrafico.get(i).substring(nomeNumeroListaGrafico.get(i).indexOf("(")+1,
												nomeNumeroListaGrafico.get(i).indexOf(")"))) + " votos",
												NumberFormat.getInstance() ) );
			}
		}

		renderer.setSeriesItemLabelGenerator(nomeNumeroListaGrafico.size(), 
				new StandardCategoryItemLabelGenerator(" {2}" + "%  " + 
						apuracaoBD.consultaVotos(cargoCB.getItemAt(cargoCB.getSelectedIndex()),"0") + " votos",
						NumberFormat.getInstance() ) );

		renderer.setSeriesItemLabelGenerator(nomeNumeroListaGrafico.size() + 1, 
				new StandardCategoryItemLabelGenerator(" {2}" + "%  " + 
						apuracaoBD.consultaVotos(cargoCB.getItemAt(cargoCB.getSelectedIndex()),"1") + " votos",
						NumberFormat.getInstance() ) );

		renderer.setAutoPopulateSeriesShape(false);
		renderer.setAutoPopulateSeriesStroke(false);

		renderer.setDrawBarOutline(true);

		renderer.setBaseItemLabelFont(fonte2);
		renderer.setBaseItemLabelsVisible(true); // Legenda na barra
		//renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setRange(0, 100);

		//Adiciona ao painel o grafico
		chartPanel.setChart(grafico);

		//Adicionando na janela
		gui.add( chartPanel, BorderLayout.SOUTH );

	}

	/** Retorna referência do {@code JComboBox<String>} excluirPartido
	 * 
	 * @return referência do {@code JComboBox<String>} excluirPartido
	 * 
	 * @see JMenuItem
	 */
	public JComboBox<String> getCargoCB() {
		return cargoCB;
	}

}
