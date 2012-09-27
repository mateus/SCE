package br.edu.ifsudestemg.tsi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.Timer;

import br.edu.ifsudestemg.tsi.classes.Data;
import br.edu.ifsudestemg.tsi.eventos.TratadorEventoInterface;
import br.edu.ifsudestemg.tsi.persistencia.ConfiguracoesBD;


/** Cria conteúdo gráfico da tela principal
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see JFrame
 *
 */
public class Interface extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Icones e Imagens
	private ImageIcon fundo = new ImageIcon("imagens/JustiçaEleitoralPB.jpg");
	private ImageIcon iconePersonalizado = new ImageIcon("icones/logo2.png" );  
	private ImageIcon cadastrarIco = new ImageIcon("icones/cadastrar.png" );  
	private ImageIcon editarIco = new ImageIcon( "icones/editar.png" ); 
	private ImageIcon excluirIco = new ImageIcon( "icones/excluir.png" ); 
	private ImageIcon consultarIco = new ImageIcon("icones/consultar.png" ); 
	private ImageIcon configuracoesIco = new ImageIcon("icones/configuracao.png" ); 
	private ImageIcon cadastrarPesquisaIco = new ImageIcon( "icones/cadastrarPesquisa.png" );
	private ImageIcon relatorioIco = new ImageIcon( "icones/relatorio.png" );
	private ImageIcon apuracaoIco = new ImageIcon( "icones/apuracao.png" );
	private ImageIcon sobreIco = new ImageIcon( "icones/sobre.png" );
	private ImageIcon creditosIco = new ImageIcon( "icones/creditos.png" );
	private ImageIcon databaseIco = new ImageIcon( "icones/database.png" );
	private ImageIcon partidoIco = new ImageIcon( "icones/partido.png" );
	private ImageIcon cargoIco = new ImageIcon( "icones/cargo.png" );
	
	//Barra de menu
	private JMenuBar menuBar = new JMenuBar();

	//Menus disponiveis
	private JMenu arquivo = new JMenu("Arquivo");
	private JMenu candidatos = new JMenu("Candidatos");
	private JMenu cargos = new JMenu("Cargos");
	private JMenu partidos = new JMenu("Partidos");
	private JMenu pesquisar = new JMenu("Pesquisar");
	private JMenu apuracao = new JMenu("Apuração");
	private JMenu ajuda = new JMenu("Ajuda");
	private JMenu sair1 = new JMenu("Sair");

	//Itens para Arquivo
	private JMenuItem configuracoes = new JMenuItem("Configurações",configuracoesIco);
	private JMenuItem PopDatabase = new JMenuItem("Popular Bando de Dados",databaseIco);
	private JMenuItem PopDatabasePesquisa = new JMenuItem("Popular Bando de Dados com Pesquisas",databaseIco);
	private JMenuItem sair2 = new JMenuItem("Sair");

	//Itens para Candidatos
	private JMenuItem cadastrar = new JMenuItem("Cadastrar",cadastrarIco);
	private JMenuItem consultar = new JMenuItem("Consultar",consultarIco);
	private JMenuItem editar = new JMenuItem("Editar",editarIco);
	private JMenuItem excluir = new JMenuItem("Excluir",excluirIco);
	
	//Itens para Cargo
	private JMenuItem cadastrarCargo = new JMenuItem("Cadastrar",cargoIco);
	private JMenuItem excluirCargo = new JMenuItem("Excluir",excluirIco);
	
	//Itens para Partido
	private JMenuItem cadastrarPartido = new JMenuItem("Cadastrar",partidoIco);
	private JMenuItem excluirPartido = new JMenuItem("Excluir",excluirIco);

	//Itens para Pesquisar
	private JMenuItem cadPesqEleitoral = new JMenuItem("Cadastrar Pesq. Eleitoral",cadastrarPesquisaIco);
	private JMenuItem relPesqEleitoral = new JMenuItem("Relatório da Pesq. Eleitoral",relatorioIco);

	//Itens para Apuração
	private JMenuItem apuraEleitoral = new JMenuItem("Apuração Eleitoral",apuracaoIco);

	//Itens para Ajuda
	private JMenuItem creditos = new JMenuItem("Créditos",creditosIco);
	private JMenuItem sobre = new JMenuItem("Sobre",sobreIco);

	//Paineis
	private JPanel painel1 = new JPanel();

	//Rótulos
	private JLabel fundoLabel;
	private JLabel dataLabel = new JLabel();
	private  JLabel horaLabel = new JLabel();
	
	//Database
	private ConfiguracoesBD configuracoesBD = new ConfiguracoesBD();

	//Verifica hora
	/**
	 * Verifica se a funcionalidade de horário para votação legal está ativada
	 */
	public static boolean horarioAtivado;// Alterado pela função CONFIGURAÇÕES

	//Fonte
	private Font fonte = new Font("Arial", Font.BOLD, 20);
	private Font fonte2 = new Font("Arial", Font.BOLD, 14);

	//Thread para o relógio
	private Timer timer;

	//Instancia objeto da classe DataHora para mostrar no Frame a Data e a Hora atual do sistema
	private Data MostraData = new Data();

	//Botões
	private JButton votar = new JButton("Simular Votação");

	
	/** 
	 * Construtor default que cria toda interface gráfica da tela principal
	 */
	public Interface(){
		super();
		horarioAtivado = configuracoesBD.statusHoraAtivado();
		

		//------------------------------ICONE DA JANELA-----------------------------
		setIconImage( iconePersonalizado.getImage() );

		//-----------------------SETANDO ATRIBUTOS DO JFRAME------------------------
		setTitle("Eleições TSI 2012"); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(600,335));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		//-----------------------------------MENU BAR-------------------------------
		//Adicionando Menus disponiveis
		menuBar.add(arquivo); 
		menuBar.add(candidatos);
		menuBar.add(cargos);
		menuBar.add(partidos);
		menuBar.add(pesquisar);
		menuBar.add(apuracao);
		menuBar.add(ajuda);
		menuBar.add(sair1);

		//Adicionando itens do Menu Arquivo
		arquivo.add(PopDatabase);
		arquivo.add(PopDatabasePesquisa);
		arquivo.add(configuracoes);
		arquivo.add(new JSeparator());
		arquivo.add(sair2);

		//Adicionando itens do Menu Candidato
		candidatos.add(cadastrar);
		candidatos.add(editar);
		candidatos.add(excluir);
		candidatos.add(consultar);
		
		//Adicionando itens do Menu Cargo
		cargos.add(cadastrarCargo);
		cargos.add(excluirCargo);
		
		//Adicionando itens do Menu Partido
		partidos.add(cadastrarPartido);
		partidos.add(excluirPartido);

		//Adicionando itens do Menu Pesquisar
		pesquisar.add(cadPesqEleitoral);
		pesquisar.add(relPesqEleitoral);

		//Adicionando itens do Menu Apuração
		apuracao.add(apuraEleitoral);

		//Adicionando itens do Menu Ajuda
		ajuda.add(creditos);
		ajuda.add(sobre);

		//Seta no frame o menuBar
		setJMenuBar(menuBar);

		//---------------------------------PLANO DE FUNDO------------------------------
		//carrega a imagem passando o nome da mesma  
		fundoLabel = new JLabel(fundo);

		painel1.add(fundoLabel);

		painel1.setBackground(Color.WHITE);

		//-------------------------------EVENTOS DOS BOTOES----------------------------
		TratadorEventoInterface tradorEventos = new TratadorEventoInterface(this);

		sair1.addMouseListener(tradorEventos);

		//Arquivos
		arquivo.setMnemonic('a');
		configuracoes.addActionListener(tradorEventos);
		PopDatabase.addActionListener(tradorEventos);
		PopDatabasePesquisa.addActionListener(tradorEventos);
		sair2.addActionListener(tradorEventos);

		//Candidatos
		candidatos.setMnemonic('c');
		cadastrar.addActionListener(tradorEventos);
		consultar.addActionListener(tradorEventos);
		editar.addActionListener(tradorEventos);
		excluir.addActionListener(tradorEventos);

		//Cargo
		cargos.setMnemonic('r');
		cadastrarCargo.addActionListener(tradorEventos);
		excluirCargo.addActionListener(tradorEventos);
		
		//Partido
		partidos.setMnemonic('t');
		cadastrarPartido.addActionListener(tradorEventos);
		excluirPartido.addActionListener(tradorEventos);
		
		//Pesquisar
		pesquisar.setMnemonic('p');
		cadPesqEleitoral.addActionListener(tradorEventos);
		relPesqEleitoral.addActionListener(tradorEventos);

		//Apuração
		apuracao.setMnemonic('u');
		apuraEleitoral.addActionListener(tradorEventos);

		//Ajuda
		ajuda.setMnemonic('j');
		creditos.addActionListener(tradorEventos);
		sobre.addActionListener(tradorEventos);

		//-----------------------------MOSTRANDO DATA E HORA--------------------------
		dataLabel.setFont(fonte);
		horaLabel.setFont(fonte);

		MostraData.dataAtual();
		dataLabel.setText(MostraData.getData() + " - Horário de Brasília:");

		horaLabel.setForeground(Color.RED);

		disparaRelogio();

		painel1.add(dataLabel,BorderLayout.SOUTH);
		painel1.add(horaLabel,BorderLayout.SOUTH);

		//-----------------------------BOTÃO SIMULAR VOTAÇÃO----------------------------
		votar.addActionListener(tradorEventos);

		votar.setFocusable(false);

		votar.setBackground(Color.GRAY);
		votar.setForeground(Color.WHITE);

		votar.setFont(fonte2);

		if(votar.isEnabled())
			votar.setToolTipText("Iniciar Simulação de Votação");
		else
			votar.setToolTipText("Cadastre Candidatos para liberar a Simulação");

		painel1.add(votar,BorderLayout.SOUTH);

		//-------------------------------ADICIONANDO PAINEL----------------------------
		add(painel1);
	}

	//Dispara o relógio com base na atualização do Timer a cada 1000ms
	
	/**
	 *  Dispara um timer para capturar a hora do sistema
	 *  
	 *  @see GregorianCalendar
	 *  @see Timer
	 */
	public void disparaRelogio() {  
		if (timer == null) {  
			timer = new Timer(1000, new ActionListener() {

				public void actionPerformed(ActionEvent evento) {
					
					//Capturando hora do sistema
					GregorianCalendar calendario = new GregorianCalendar();  
					int h = calendario.get(GregorianCalendar.HOUR_OF_DAY);  
					int m = calendario.get(GregorianCalendar.MINUTE);  
					int s = calendario.get(GregorianCalendar.SECOND);  

					//Mascarando hora do sistema
					String hora =  
							((h < 10) ? "0" : "")  
							+ h  
							+ ":"  
							+ ((m < 10) ? "0" : "")  
							+ m  
							+ ":"  
							+ ((s < 10) ? "0" : "")  
							+ s;  
					
					//Imprimindo
					horaLabel.setText(hora);

					//Verificar se o sistema de horario está ativado
					if(horarioAtivado){
						if(h >= 8 && h < 17)
							votar.setEnabled(true);
						else
							votar.setEnabled(false);
					}else
						votar.setEnabled(true);
				}
			} );  
			timer.setInitialDelay(0);  // Reseta Delay 
			timer.start();  
		} else if (!timer.isRunning()) {  
			timer.restart();  
		}  
	}  

	/** Retorna referência do <code>JMenu</code> arquivo
	 * 
	 * @return referência do <code>JMenu</code> arquivo
	 * 
	 * @see JMenu
	 */
	public JMenu getArquivo() {
		return arquivo;
	}

	/** Retorna referência do <code>JMenu</code> candidatos
	 * 
	 * @return referência do <code>JMenu</code> candidatos
	 * 
	 * @see JMenu
	 */
	public JMenu getCandidatos() {
		return candidatos;
	}

	/** Retorna referência do <code>JMenu</code> pesquisar
	 * 
	 * @return referência do <code>JMenu</code> pesquisar
	 * 
	 * @see JMenu
	 */
	public JMenu getPesquisar() {
		return pesquisar;
	}

	/** Retorna referência do <code>JMenu</code> apuracao
	 * 
	 * @return referência do <code>JMenu</code> apuracao
	 * 
	 * @see JMenu
	 */
	public JMenu getApuracao() {
		return apuracao;
	}

	/** Retorna referência do <code>JMenu</code> sair1
	 * 
	 * @return referência do <code>JMenu</code> sair1
	 * 
	 * @see JMenu
	 */
	public JMenu getSair1() {
		return sair1;
	}

	/** Retorna referência do <code>JMenuItem</code> configuracoes
	 * 
	 * @return referência do <code>JMenuItem</code> configuracoes
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getConfiguracoes() {
		return configuracoes;
	}

	/** Retorna referência do <code>JMenuItem</code> sair2
	 * 
	 * @return referência do <code>JMenuItem</code> sair2
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getSair2() {
		return sair2;
	}

	/** Retorna referência do <code>JMenuItem</code> cadastrar
	 * 
	 * @return referência do <code>JMenuItem</code> cadastrar
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getCadastrar() {
		return cadastrar;
	}

	/** Retorna referência do <code>JMenuItem</code> consultar
	 * 
	 * @return referência do <code>JMenuItem</code> consultar
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getConsultar() {
		return consultar;
	}

	/** Retorna referência do <code>JMenuItem</code> editar
	 * 
	 * @return referência do <code>JMenuItem</code> editar
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getEditar() {
		return editar;
	}

	/** Retorna referência do <code>JMenuItem</code> excluir
	 * 
	 * @return referência do <code>JMenuItem</code> excluir
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getExcluir() {
		return excluir;
	}

	/** Retorna referência do <code>JMenuItem</code> cadPesqEleitoral
	 * 
	 * @return referência do <code>JMenuItem</code> cadPesqEleitoral
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getCadPesqEleitoral() {
		return cadPesqEleitoral;
	}

	/** Retorna referência do <code>JMenuItem</code> relPesqEleitoral
	 * 
	 * @return referência do <code>JMenuItem</code> relPesqEleitoral
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getRelPesqEleitoral() {
		return relPesqEleitoral;
	}

	/** Retorna referência do <code>JMenuItem</code> apuraEleitoral
	 * 
	 * @return referência do <code>JMenuItem</code> apuraEleitoral
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getApuraEleitoral() {
		return apuraEleitoral;
	}

	/** Retorna referência do <code>JMenuItem</code> creditos
	 * 
	 * @return referência do <code>JMenuItem</code> creditos
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getCreditos() {
		return creditos;
	}

	/** Retorna referência do <code>JMenuItem</code> sobre
	 * 
	 * @return referência do <code>JMenuItem</code> sobre
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getSobre() {
		return sobre;
	}

	/** Retorna referência do <code>JButton</code> votar
	 * 
	 * @return referência do <code>JButton</code> votar
	 * 
	 * @see JButton
	 */
	public JButton getVotar() {
		return votar;
	}

	/** Retorna referência do <code>JMenuItem</code> PopDatabase
	 * 
	 * @return referência do <code>JMenuItem</code> PopDatabase
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getPopDatabase() {
		return PopDatabase;
	}
	
	/** Retorna referência do <code>JMenuItem</code> PopDatabasePesquisa
	 * 
	 * @return referência do <code>JMenuItem</code> PopDatabasePesquisa
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getPopDatabasePesquisa() {
		return PopDatabasePesquisa;
	}

	/** Retorna referência do <code>JMenuItem</code> cadastrarCargo
	 * 
	 * @return referência do <code>JMenuItem</code> cadastrarCargo
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getCadastrarCargo() {
		return cadastrarCargo;
	}

	/** Retorna referência do <code>JMenuItem</code> excluirCargo
	 * 
	 * @return referência do <code>JMenuItem</code> excluirCargo
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getExcluirCargo() {
		return excluirCargo;
	}

	/** Retorna referência do <code>JMenuItem</code> cadastrarPartido
	 * 
	 * @return referência do <code>JMenuItem</code> cadastrarPartido
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getCadastrarPartido() {
		return cadastrarPartido;
	}

	/** Retorna referência do <code>JMenuItem</code> excluirPartido
	 * 
	 * @return referência do <code>JMenuItem</code> excluirPartido
	 * 
	 * @see JMenuItem
	 */
	public JMenuItem getExcluirPartido() {
		return excluirPartido;
	}		
}
