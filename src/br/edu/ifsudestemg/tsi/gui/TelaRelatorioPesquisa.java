package br.edu.ifsudestemg.tsi.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.geom.Ellipse2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXDatePicker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import br.edu.ifsudestemg.tsi.eventos.TratadorEventoRelatorioPesquisa;
import br.edu.ifsudestemg.tsi.persistencia.CargoBD;
import br.edu.ifsudestemg.tsi.pesquisa.Pesquisa;

/**
 * Cria conteúdo gráfico da tela de relatório de pesquisa
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see JDialog
 */
public class TelaRelatorioPesquisa extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Icones
	private ImageIcon iconePersonalizado = new ImageIcon("icones/logo2.png" );

	//Paineis
	private JPanel painelTitulo = new JPanel();
	private JPanel painelItens = new JPanel(new GridBagLayout());

	//Rótulos
	private JLabel tituloLabel = new JLabel("Relatório de Pesquisa Eleitoral");
	private JLabel cargoLabel = new JLabel("Cargo: ");
	private JLabel dataInicioLabel = new JLabel("Início da Pesquisa: ");
	private JLabel dataFimLabel = new JLabel("Fim da Pesquisa: ");

	//Combo Box
	private String[] cargosStr;
	private List<String> cargos = new ArrayList<String>(); 
	private JComboBox<String> cargoCB;

	//DataPicker	
	private JXDatePicker dataInicial = new JXDatePicker(System.currentTimeMillis());
	private JXDatePicker dataFinal = new JXDatePicker(System.currentTimeMillis());

	//Fonte
	private Font fonte = new Font("Arial", Font.BOLD, 20);
	private Font fonte2 = new Font("Arial", Font.BOLD, 12);

	//Botao
	private JButton exibir = new JButton("Exibir");

	//Grid
	private GridBagConstraints gbc = new GridBagConstraints();

	//Database
	private CargoBD cargoBD = new CargoBD();
	private ResultSet resultadoCargo;
	private ResultSet resultado;

	//Pesquisa
	private Pesquisa pesquisa = new Pesquisa();

	//Matriz
	private List<ArrayList<String>> matriz = new ArrayList<ArrayList<String>>();

	//Array
	private ArrayList<String> entrevistados = new ArrayList<String>();
	private ArrayList<String> municipios = new ArrayList<String>();
	private ArrayList<String> mesInicio = new ArrayList<String>();
	private ArrayList<String> anoInicio = new ArrayList<String>();
	private ArrayList<String> brancosNulos = new ArrayList<String>();
	private ArrayList<String> indecisos = new ArrayList<String>();

	//Painel chart
	private ChartPanel chartPanel = new ChartPanel(null);

	/**
	 * Construtor default que criará todo conteúdo gráfico da tela
	 */
	@SuppressWarnings("deprecation")
	public TelaRelatorioPesquisa() {
		super();	
		TratadorEventoRelatorioPesquisa tratadorEvento = new TratadorEventoRelatorioPesquisa(this);

		//-----------------------------TITULO DO DIALOGO---------------------------
		tituloLabel.setFont(fonte);
		painelTitulo.add(tituloLabel);

		//GBC
		gbc.insets = new Insets(10, 3, 3, 10); // Setando espaços para o GridBadLayout 
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.BOTH;  // Preenche todo a coluna 

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
		//-----------------------ADICIONANDO CAMPO DATA INICIAL------------------------
		dataInicioLabel.setFont(fonte2);
		gbc.gridx = 2;
		gbc.gridy = 0;
		painelItens.add(dataInicioLabel,gbc);

		dataInicial.setFormats(new String[] {"dd/MM/yyyy"});
		dataInicial.setLinkDate(System.currentTimeMillis(), "Data atual {0}");
		dataInicial.setDate(new Date("01/01/2012"));
		dataInicial.addActionListener(tratadorEvento);
		gbc.gridx = 3;
		gbc.gridy = 0;
		painelItens.add(dataInicial,gbc);
		//-----------------------ADICIONANDO CAMPO DATA FINAL-------------------------
		dataFimLabel.setFont(fonte2);
		gbc.gridx = 4;
		gbc.gridy = 0;
		painelItens.add(dataFimLabel,gbc);

		dataFinal.setFormats(new String[] {"dd/MM/yyyy"});
		dataFinal.setLinkDate(System.currentTimeMillis(), "Data atual {0}");
		dataFinal.addActionListener(tratadorEvento);
		gbc.gridx = 5;
		gbc.gridy = 0;
		painelItens.add(dataFinal,gbc);


		//----------------------------------BOTAO EXIBIR----------------------------
		exibir.setFocusable(false);
		exibir.addActionListener(tratadorEvento);
		gbc.gridx = 6;
		gbc.gridy = 0;
		painelItens.add(exibir,gbc);

		//----------------------------CRIA PAINEL JFREECHAR-------------------------
		List<String> res = new ArrayList<String>(); 
		String[] resStr;

		//Captura somente os IDs de pesquisa
		resultado = pesquisa.consultaEntreDatas(cargoCB.getItemAt(cargoCB.getSelectedIndex())
				, dataInicial.getEditor().getText(), 
				dataFinal.getEditor().getText());

		try {
			while(resultado.next())
			{
				res.add( resultado.getString("id") ); // Adiciona na lista os ids 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 

		resStr = (String[]) res.toArray (new String[0]);   // transfere para string

		//Para cada ID de pesquisa imprime o seus resultados
		for(int i=0;i<resStr.length;i++){
			//System.out.print("\n ID:"+resStr[i]);

			List<String> numero = new ArrayList<String>(); 
			List<String> intencao = new ArrayList<String>();
			String[] numeroStr;
			String[] intencaoStr;

			resultado = pesquisa.consultaCandidatoPesquisa(resStr[i]);
			try {
				while(resultado.next())
				{
					numero.add( resultado.getString("nome") );
					intencao.add( resultado.getString("intencaovoto") );
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
			} 

			numeroStr = (String[]) numero.toArray (new String[0]);   // transfere para string
			intencaoStr = (String[]) intencao.toArray (new String[0]);   // transfere para string

			matriz.add(new ArrayList<String>()); // Cria linha 1

			//Imprime resultado com nome intenção
			for(int k=0;k<numeroStr.length;k++){
				//System.out.print("\n Nome:"+numeroStr[k]+" Intencao:"+intencaoStr[k]);

				//Preencher matriz
				if(i == 0){ //Nome do candidato + inteção
					matriz.add(new ArrayList<String>());
					matriz.get(i).add( numeroStr[k] );
					matriz.get(i+1).add( intencaoStr[k] );
				}else{ // Somente inteção
					matriz.get(i+1).add( intencaoStr[k] );
				}

			}
		}

		//Preencher array de datas inicio
		List<String> resMes = new ArrayList<String>(); 
		List<String> resAno = new ArrayList<String>(); 
		String[] resMesStr;
		String[] resAnoStr;


		//Captura somente os meses e anos de pesquisa
		resultado = pesquisa.consultaDataInicioEntreDatas(cargoCB.getItemAt(cargoCB.getSelectedIndex())
				, dataInicial.getEditor().getText(), 
				dataFinal.getEditor().getText());

		try {
			while(resultado.next())
			{
				resMes.add( resultado.getString("mes") ); // Adiciona na lista 
				resAno.add( resultado.getString("ano") ); // Adiciona na lista 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 

		resMesStr = (String[]) resMes.toArray (new String[0]);   // transfere para string
		resAnoStr = (String[]) resAno.toArray (new String[0]);   // transfere para string
		for(int a=0;a<resMesStr.length;a++)
			mesInicio.add(resMesStr[a]);

		for(int a=0;a<resAnoStr.length;a++)
			anoInicio.add(resAnoStr[a]);
		
		//Preencher array de entrevistados e municípios
		List<String> resEntr = new ArrayList<String>(); 
		List<String> resMun = new ArrayList<String>(); 
		String[] resEntrStr;
		String[] resMunStr;
		
		//Captura somente os entrevistados e municipios de pesquisa
				resultado = pesquisa.consultaEntrMunEntreDatas(cargoCB.getItemAt(cargoCB.getSelectedIndex())
						, dataInicial.getEditor().getText(), 
						dataFinal.getEditor().getText());

				try {
					while(resultado.next())
					{
						resEntr.add( resultado.getString("entr") ); // Adiciona na lista 
						resMun.add( resultado.getString("mun") ); // Adiciona na lista 
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
				} 

				resEntrStr = (String[]) resEntr.toArray (new String[0]);   // transfere para string
				resMunStr = (String[]) resMun.toArray (new String[0]);   // transfere para string
				for(int a=0;a<resEntrStr.length;a++)
					entrevistados.add(resEntrStr[a]);

				for(int a=0;a<resMunStr.length;a++)
					municipios.add(resMunStr[a]);

		//Preencher array de brancos, nulos, indecisos
		List<String> resBrancosNulos = new ArrayList<String>(); 
		List<String> resIndecisos = new ArrayList<String>(); 
		String[] resBrancosNulosStr;
		String[] resIndecisosStr;

		resultado = pesquisa.consultaBrancosNulosIndecisos();

		try {
			while(resultado.next())
			{
				resBrancosNulos.add( resultado.getString("nulos_brancos") ); // Adiciona na lista 
				resIndecisos.add( resultado.getString("indecisos") ); // Adiciona na lista 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 

		resBrancosNulosStr = (String[]) resBrancosNulos.toArray (new String[0]);   // transfere para string
		resIndecisosStr = (String[]) resIndecisos.toArray (new String[0]);   // transfere para string

		for(int a=0;a<resBrancosNulosStr.length;a++)
			brancosNulos.add(resBrancosNulosStr[a]);

		for(int a=0;a<resIndecisosStr.length;a++)
			indecisos.add(resIndecisosStr[a]);

		criaGrafico(this,matriz,mesInicio,anoInicio,brancosNulos,indecisos, entrevistados, municipios);

		//----------------------------ADICIONANDO COR BRANCA------------------------
		painelTitulo.setBackground(Color.WHITE);
		painelItens.setBackground(Color.WHITE);

		//---------------------------ADICIOANANDO OS PAINEIS------------------------
		add(painelTitulo,BorderLayout.NORTH);
		add(painelItens);

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


	/** Cria gráfico de com os resultados das pesquisas 
	 * 
	 * @param gui <code>TelaRelatorioPesquisa</code> com a referência da tela de relatório de pesquisa
	 * @param matriz {@code List<ArrayList<String>>} com os resultados de pesquisa
	 * @param mesInicio {@code ArrayList<String>} com os meses de inicio
	 * @param anoInicio {@code ArrayList<String>} com os anos de inicio
	 * @param brancosNulos {@code ArrayList<String>} com os votos brancos e nulos
	 * @param indecisos {@code ArrayList<String>} com a quantidade de indecisos
	 * @param entrevistados {@code ArrayList<String>} com o número de entrevistados
	 * @param muncipios {@code ArrayList<String>} com o número de municípios
	 */
	public void criaGrafico(TelaRelatorioPesquisa gui,
			List<ArrayList<String>> matriz, 
			ArrayList<String> mesInicio,
			ArrayList<String> anoInicio,
			ArrayList<String> brancosNulos,
			ArrayList<String> indecisos,
			ArrayList<String> entrevistados,
			ArrayList<String> muncipios){

		//Cria vetor de meses escritos
		ArrayList<String> mesInicioEscrito = new ArrayList<String>();
		for(int i = 0; i < mesInicio.size(); i++){
			switch (Integer.parseInt( mesInicio.get(i) )) {
				case 1: mesInicioEscrito.add("jan");
					break;
				case 2: mesInicioEscrito.add("fev");
					break;
				case 3: mesInicioEscrito.add("mar");
					break;
				case 4: mesInicioEscrito.add("abr");
					break;
				case 5: mesInicioEscrito.add("mai");
					break;
				case 6: mesInicioEscrito.add("jun");
					break;
				case 7: mesInicioEscrito.add("jul");
					break;
				case 8: mesInicioEscrito.add("ago");
					break;
				case 9: mesInicioEscrito.add("set");
					break;
				case 10: mesInicioEscrito.add("out");
					break;
				case 11: mesInicioEscrito.add("nov");
					break;
				case 12: mesInicioEscrito.add("dez");
					break;
			}
		}

		ArrayList<Double> listaDePorcentagem = new ArrayList<Double>();
		Integer total = 0;

		//Cria um dataSet para inserir os dados que serão passados para a criação do grafico tipo linha
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		//Calcula porcentagem 
		if(matriz.size()>0){
			for(int i = 0 ; i < mesInicio.size(); i++ ){
				for(int j = 0; j < matriz.get(0).size(); j++){
					total += Integer.parseInt( matriz.get(i+1).get(j));
				}
				total += Integer.parseInt( brancosNulos.get(i) );
				total += Integer.parseInt( indecisos.get(i) );
				listaDePorcentagem.add(100.0 / total);
				total = 0;
			}
		}

		//Adiciona os dados ao dataSet 
		if(matriz.size()>0){
			// Candidatos
			for(int i=0; i < matriz.get(0).size(); i++ ){
				for(int j = 0; j <mesInicio.size() ; j++){
					dataset.addValue( Integer.parseInt( matriz.get(j+1).get(i) ) * listaDePorcentagem.get(j), 
							matriz.get(0).get(i), 
							mesInicioEscrito.get(j)+"/"+anoInicio.get(j)+
							" ("+entrevistados.get(j)+"/"+municipios.get(j)+")");
				}
			}

			//BrancosNulos e Indecisos
			for(int i=0; i < matriz.get(0).size(); i++ ){
				for(int j = 0; j <mesInicio.size() ; j++){
					dataset.addValue( Integer.parseInt( brancosNulos.get(j) ) * listaDePorcentagem.get(j), 
							"Brancos/Nulos", 
							mesInicioEscrito.get(j)+"/"+anoInicio.get(j)+
							" ("+entrevistados.get(j)+"/"+municipios.get(j)+")");

					dataset.addValue( Integer.parseInt( indecisos.get(j) ) * listaDePorcentagem.get(j), 
							"Indecisos", 
							mesInicioEscrito.get(j)+"/"+anoInicio.get(j)+
							" ("+entrevistados.get(j)+"/"+municipios.get(j)+")");
				}
			}

		}

		JFreeChart grafico = ChartFactory.createLineChart(
				"",             // chart title
				"Datas das Pesquisas",                      // domain axis label
				"Porcentagem (%)",                         // range axis label
				dataset,                         // data
				PlotOrientation.VERTICAL,        // orientation
				true,                            // include legend
				true,                            // tooltips
				false                            // urls
				);

		grafico.setBackgroundPaint(Color.WHITE);

		CategoryPlot plot = (CategoryPlot) grafico.getPlot();

		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setRangeZeroBaselineVisible(true);
		plot.setNoDataMessage("Nenhuma pesquisa cadastrada neste período");

		// Modificando renderizador
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();

		renderer.setBaseShapesVisible(true);
		renderer.setBaseStroke(new BasicStroke(6));
		renderer.setBaseShape(new Ellipse2D.Float(-5,-5, 10, 10));
		
		renderer.setBaseItemLabelFont(fonte2);
		
		renderer.setAutoPopulateSeriesShape(false);
		renderer.setAutoPopulateSeriesStroke(false);

		renderer.setDrawOutlines(true); // Linhas
		renderer.setUseFillPaint(false); // Bolinha preenchida

		renderer.setBaseItemLabelsVisible(true); // Legenda na bolinha
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

		CategoryAxis axis = plot.getDomainAxis();
		axis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD); // Estilo da escrita dos meses

		//Adiciona ao painel o grafico
		chartPanel.setChart(grafico);
		
		//Adicionando na janela
		gui.add( chartPanel, BorderLayout.SOUTH );

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

	/** Retorna referência do <code>JXDatePicker</code> dataInicial
	 * 
	 * @return referência do <code>JXDatePicker</code> dataInicial
	 * 
	 * @see JXDatePicker 
	 */
	public JXDatePicker getDataInicial() {
		return dataInicial;
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

	/** Retorna referência do <code>JButton</code> exibir
	 * 
	 * @return referência do <code>JButton</code> exibir
	 * 
	 * @see JButton 
	 */
	public JButton getExibir() {
		return exibir;
	}

	/** Retorna referência do {@code List<ArrayList<String>>} matriz
	 * 
	 * @return referência do {@code List<ArrayList<String>>} matriz
	 * 
	 * @see List 
	 */
	public List<ArrayList<String>> getMatriz() {
		return matriz;
	}

	/** Insere matriz de resultados
	 * 
	 * @param matriz {@code List<ArrayList<String>>} com os resultados da pesquisa
	 */
	public void setMatriz(List<ArrayList<String>> matriz) {
		this.matriz = matriz;
	}

	/** Lista com os meses presentes na pesquisa
	 * @param mesInicio {@code ArrayList<String>} meses presentes na pesquisa
	 */
	public void setMesInicio(ArrayList<String> mesInicio) {
		this.mesInicio = mesInicio;
	}

	/** Lista com os anos presentes na pesquisa
	 * @param anoInicio {@code ArrayList<String>} anos presentes na pesquisa
	 */
	public void setAnoInicio(ArrayList<String> anoInicio) {
		this.anoInicio = anoInicio;
	}

	/** Retorna referência do {@code ArrayList<String>} mesInicio
	 * 
	 * @return referência do {@code ArrayList<String>} mesInicio
	 * 
	 * @see ArrayList 
	 */
	public ArrayList<String> getMesInicio() {
		return mesInicio;
	}

	/** Retorna referência do {@code ArrayList<String>} anoInicio
	 * 
	 * @return referência do {@code ArrayList<String>} anoInicio
	 * 
	 * @see ArrayList 
	 */
	public ArrayList<String> getAnoInicio() {
		return anoInicio;
	}



}

