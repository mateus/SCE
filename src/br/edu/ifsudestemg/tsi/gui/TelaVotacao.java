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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import br.edu.ifsudestemg.tsi.classes.Botao;
import br.edu.ifsudestemg.tsi.eventos.TratadorEventoVotacao;
import br.edu.ifsudestemg.tsi.persistencia.VotacaoBD;

/**
 * Cria conteúdo gráfico da tela de votação
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see JDialog
 */
public class TelaVotacao extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Icones
	private ImageIcon iconePersonalizado = new ImageIcon("icones/logo2.png" );
	private ImageIcon banner = new ImageIcon("imagens/BannerJustiçaEleitoralPB.jpg");
	private ImageIcon candidato;

	//Redimencionar
	//Adicionando ao objeto imagem a imagem selecionada / Colocando imagem em escala (120x140)

	//Paineis
	private JPanel painelNumerico = new JPanel(new GridBagLayout());
	private JPanel painelBotoes = new JPanel(new GridBagLayout());
	private JPanel painelDireito = new JPanel(new GridBagLayout());
	private JPanel painelBanner = new JPanel();
	private JPanel painelTela = new JPanel(new BorderLayout());
	private JPanel painelUrna = new JPanel(new GridBagLayout());
	private JPanel painelGeral = new JPanel(new GridBagLayout());
	private JPanel painelMensagemDefault = new JPanel(new GridBagLayout());
	private JPanel painelMensagemTitulo = new JPanel(new GridBagLayout());
	private JPanel painelCentralTela = new JPanel(new BorderLayout());
	private JPanel painelCentralTelaCargo = new JPanel(new GridBagLayout());
	private JPanel painelCentralItens = new JPanel(new GridBagLayout());
	
	//Botão 
	private Botao[] botaoNumerico = new Botao[10];
	private Botao botaoConfirma = new Botao("CONFIRMA",1);
	private Botao botaoCorrige = new Botao("CORRIGE",2);
	private Botao botaoBranco = new Botao("BRANCO",3);

	//Grid
	private GridBagConstraints gbc = new GridBagConstraints();
	private GridBagConstraints gbc2 = new GridBagConstraints();
	
	//Rotulo
	/**
	 * Rótulo para a imagem do candidato
	 */
	public JLabel imagemCandidato = new JLabel();
	private JLabel bannerLabel = new JLabel(banner);
	private JLabel mensagemDefaultLabel = new JLabel(
			"<html>" +
			"Aperte a Tecla: " +
			"<br> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp VERDE para CONFIRMAR este voto" +
			"<br> &nbsp&nbsp&nbsp LARANJA para REINICIAR este voto </html>");
	private JLabel mensagemTituloLabel = new JLabel(
			"<html><font size = 4>TREINAMENTO</font>" +
			"<br>" +
			"<font size = 4 color = rgb(228,228,228)> <b>SEU VOTO PARA</b></font>" +
			"</html>");
	private JLabel cargoLabel = new JLabel();
	private JLabel mensagemVotoEmBranco = new JLabel("" +
			"<html>" +
			"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp " +
			"VOTO EM BRANCO" +
			" &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" +
			"<br><br><br><br><br><br>" +
			"</html>");
	private JLabel espacoes = new JLabel("<html><br><br><br></html>");
	private JLabel numeroLabel = new JLabel("Número: ");
	private JLabel numeroFieldLabel = new JLabel();
	private JLabel nomeLabel = new JLabel("Nome: ");
	private JLabel nomeFieldLabel = new JLabel();
	private JLabel partidoLabel = new JLabel("Partido: ");
	private JLabel partidoFieldLabel = new JLabel();
	private JLabel numeroErrado1 = new JLabel("NÚMERO");
	private JLabel numeroErrado2 = new JLabel("ERRADO");
	private JLabel candidatoInexistente1 = new JLabel("CANDIDATO");
	private JLabel candidatoInexistente2 = new JLabel("INEXISTENTE");
	private JLabel votoNulo = new JLabel("VOTO NULO");
	private JLabel fim = new JLabel("FIM");
	private JLabel votou = new JLabel("VOTOU");
	
	//Fonte
	private Font fonte = new Font("Arial", Font.PLAIN, 14);
	private Font fonte2 = new Font("Arial", Font.PLAIN, 16);
	private Font fonte3 = new Font("Arial", Font.PLAIN, 20);
	private Font fonte4 = new Font("Arial", Font.PLAIN, 24);
	private Font fonte5 = new Font("Arial", Font.PLAIN, 40);
	private Font fonte6 = new Font("Arial", Font.BOLD, 24);
	private Font fonte7 = new Font("Arial", Font.PLAIN, 150);
	private Font fonte8 = new Font("Arial", Font.PLAIN, 35);
	
	//Database
	private VotacaoBD votacaoBD = new VotacaoBD();
	private ResultSet resultado;
	
	//ArrayList
	private ArrayList<ArrayList<String>> cargosParaVotacaoOrdenados = new ArrayList<ArrayList<String>>();
	
	//Contadores
	private int contadorCargos = 0;
	
	//Boolean
	private boolean votoNuloAtivo = false;
	private boolean votoBrancoAtivo = false;
	private boolean fimAtivo = false;
	
	/**
	 * @param cargosParaVotacao ({@code }
	 */
	public TelaVotacao(ArrayList<String> cargosParaVotacao) {
		 
		TratadorEventoVotacao tratadorEvento = new TratadorEventoVotacao(this);

		//Armazena no tabela votacao_cargos os cargos para esta eleição
		votacaoBD.insereCargosParaVotacao(cargosParaVotacao);
		
		//Ordena os cargos deste eleição por quantidade de digitos
		resultado = votacaoBD.consultaCargosCadastrados(); // Já recebe os dados ordenados pelo BD
		
		int i = 0;
		try {
			while(resultado.next())
			{
				cargosParaVotacaoOrdenados.add(new ArrayList<String>());
				cargosParaVotacaoOrdenados.get(i).add( resultado.getString("nome") ); // Adiciona na lista os nomes 
				cargosParaVotacaoOrdenados.get(i).add( resultado.getString("qtdDigitos") ); // Adiciona na lista os digitos 
				i++;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro na consulta ao Banco de Dados");
		} 
				
		//GBC
		gbc.insets = new Insets(5, 5, 5, 5); // Setando espaços para o GridBadLayout 

		//-------------------------------NUMEROS-----------------------------
		for(i=0 ; i < botaoNumerico.length; i++){
			botaoNumerico[i] = new Botao(""+i,0);
			botaoNumerico[i].setFont(new Font("Arial",Font.BOLD,29));
			botaoNumerico[i].setPreferredSize(new Dimension(65,50));
			botaoNumerico[i].setFocusable(false);
			botaoNumerico[i].setForeground(Color.WHITE);
			botaoNumerico[i].setHorizontalAlignment(SwingConstants.LEFT); 
			botaoNumerico[i].addActionListener(tratadorEvento);
		}

		gbc.gridx = 0;
		gbc.gridy = 0;
		painelNumerico.add(botaoNumerico[1],gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		painelNumerico.add(botaoNumerico[2],gbc);
		gbc.gridx = 2;
		gbc.gridy = 0;
		painelNumerico.add(botaoNumerico[3],gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		painelNumerico.add(botaoNumerico[4],gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		painelNumerico.add(botaoNumerico[5],gbc);
		gbc.gridx = 2;
		gbc.gridy = 1;
		painelNumerico.add(botaoNumerico[6],gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		painelNumerico.add(botaoNumerico[7],gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		painelNumerico.add(botaoNumerico[8],gbc);
		gbc.gridx = 2;
		gbc.gridy = 2;
		painelNumerico.add(botaoNumerico[9],gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		painelNumerico.add(botaoNumerico[0],gbc);

		//-----------------------BOTOES CONFIRMA,CORRIGE E BRANCO--------------------
		gbc.anchor = GridBagConstraints.SOUTH;

		gbc.gridx = 0;
		gbc.gridy = 0;

		botaoBranco.setFocusable(false);
		botaoBranco.setPreferredSize(new Dimension(100,50));
		botaoBranco.setFont(fonte);
		botaoBranco.setVerticalAlignment(SwingConstants.TOP); 
		botaoBranco.addActionListener(tratadorEvento);
		painelBotoes.add(botaoBranco,gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;

		botaoCorrige.setFocusable(false);
		botaoCorrige.setPreferredSize(new Dimension(100,50));
		botaoCorrige.setFont(fonte);
		botaoCorrige.setVerticalAlignment(SwingConstants.TOP); 
		botaoCorrige.addActionListener(tratadorEvento);
		painelBotoes.add(botaoCorrige,gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;

		botaoConfirma.setFocusable(false);
		botaoConfirma.setPreferredSize(new Dimension(100,60));
		botaoConfirma.setFont(fonte);
		botaoConfirma.setVerticalAlignment(SwingConstants.TOP); 
		botaoConfirma.setHorizontalAlignment(SwingConstants.LEFT);
		botaoConfirma.addActionListener(tratadorEvento);
		painelBotoes.add(botaoConfirma,gbc);
				
		//---------------------------ADICIOANANDO OS PAINEIS------------------------
		gbc.gridx = 0;
		gbc.gridy = 0;
		painelBanner.add(bannerLabel);
		painelBanner.setBackground(Color.WHITE);
				
		gbc.gridx = 0;
		gbc.gridy = 1;
		painelDireito.add(painelNumerico,gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		painelDireito.add(painelBotoes,gbc);
		
		Color corPretaFundo = new Color(65,65,65);
		painelNumerico.setBackground(corPretaFundo);
		painelBotoes.setBackground(corPretaFundo);
		painelDireito.setBackground(corPretaFundo);
		painelDireito.setBorder(BorderFactory.createRaisedBevelBorder());
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		painelGeral.add(painelDireito,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		painelBanner.setBorder(BorderFactory.createLoweredBevelBorder());
		painelGeral.add(painelBanner,gbc);

		painelTela.setPreferredSize(new Dimension(500,430));
		painelTela.setBorder(BorderFactory.createLoweredBevelBorder());
		
		gbc2.insets = new Insets(3,5,0,370); // Setando espaços para o GridBadLayout
		
		//ADICIOANANDO MENSAGEM TITULO
		mensagemTituloLabel.setFont(fonte2);
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		painelMensagemTitulo.add(mensagemTituloLabel,gbc2);
				
		painelTela.add(painelMensagemTitulo,BorderLayout.NORTH);
		
		//ADICIOANANDO NOME CARGO
		cargoLabel.setText(cargosParaVotacaoOrdenados.get(contadorCargos).get(0).toString());
		cargoLabel.setFont(fonte4);
		
		gbc2.insets = new Insets(10,10,10,10); // Setando espaços para o GridBadLayout
		gbc2.anchor = GridBagConstraints.NORTH;
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		painelCentralTelaCargo.add(cargoLabel,gbc2);
	
		painelCentralTela.add(painelCentralTelaCargo,BorderLayout.NORTH);
				
		//ADICIONANDO NUMERO LABEL
		gbc2.insets = new Insets(5, 5, 5, 5); // Setando espaços para o GridBadLayout
		gbc2.anchor = GridBagConstraints.SOUTHWEST;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		numeroLabel.setFont(fonte3);
		numeroLabel.setVisible(false);
		painelCentralItens.add(numeroLabel,gbc2);
				
	
		gbc2.gridx = 1;
		gbc2.gridy = 0;
		numeroFieldLabel.setFont(fonte5);
		numeroFieldLabel.setPreferredSize(new Dimension(200,45));
		numeroFieldLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		numeroFieldLabel.setVisible(true);
		painelCentralItens.add(numeroFieldLabel,gbc2);
		
		//imagem
		gbc2.anchor = GridBagConstraints.NORTHEAST;
		gbc2.insets = new Insets(15, 5, 5, 30); // Setando espaços para o GridBadLayout
		gbc2.gridx = 2;
		gbc2.gridy = 0;	
		candidato = new ImageIcon("imagens//transparente.png");
		candidato.setImage(candidato.getImage().getScaledInstance(120, 140, 100)); 
		imagemCandidato.setIcon(candidato);
		painelCentralItens.add(imagemCandidato,gbc2);
		
		//ADICIONANDO NOME LABEL
		gbc2.insets = new Insets(10, 5, 5, 5); // Setando espaços para o GridBadLayout
		gbc2.anchor = GridBagConstraints.SOUTHWEST;
		
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		nomeLabel.setFont(fonte3);
		nomeLabel.setVisible(false);
		painelCentralItens.add(nomeLabel,gbc2);
		
		gbc2.insets = new Insets(10, 5, 5, 5); // Setando espaços para o GridBadLayout
		gbc2.gridx = 1;
		gbc2.gridy = 1;
		nomeFieldLabel.setFont(fonte6);
		nomeFieldLabel.setVisible(true);
		painelCentralItens.add(nomeFieldLabel,gbc2);
		
		//numero errado
		gbc2.insets = new Insets(10, 5, 5, 5); // Setando espaços para o GridBadLayout
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		numeroErrado1.setFont(fonte3);
		numeroErrado1.setVisible(false);
		painelCentralItens.add(numeroErrado1,gbc2);
		
		gbc2.gridx = 1;
		gbc2.gridy = 1;
		numeroErrado2.setFont(fonte3);
		numeroErrado2.setVisible(false);
		painelCentralItens.add(numeroErrado2,gbc2);
		
		//candidato inexistente
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		candidatoInexistente1.setFont(fonte3);
		candidatoInexistente1.setVisible(false);
		painelCentralItens.add(candidatoInexistente1,gbc2);
		
		gbc2.insets = new Insets(10, 5, 5, 50); // Setando espaços para o GridBadLayout
		gbc2.gridx = 1;
		gbc2.gridy = 1;
		candidatoInexistente2.setFont(fonte3);
		candidatoInexistente2.setVisible(false);
		painelCentralItens.add(candidatoInexistente2,gbc2);
		
		//ADICIONANDO PARTIDO
		gbc2.insets = new Insets(10, 5, 5, 5); // Setando espaços para o GridBadLayout
		gbc2.gridx = 0;
		gbc2.gridy = 2;
		partidoLabel.setFont(fonte3);
		partidoLabel.setVisible(false);
		painelCentralItens.add(partidoLabel,gbc2);
		
		gbc2.insets = new Insets(10, 5, 5, 150); // Setando espaços para o GridBadLayout
		gbc2.gridx = 1;
		gbc2.gridy = 2;
		partidoFieldLabel.setFont(fonte6);
		partidoFieldLabel.setVisible(true);
		painelCentralItens.add(partidoFieldLabel,gbc2);
		
		gbc2.insets = new Insets(10, 5, 5, 5); // Setando espaços para o GridBadLayout
		gbc2.gridx = 0;
		gbc2.gridy = 3;
		espacoes.setFont(fonte3);
		espacoes.setVisible(true);
		painelCentralItens.add(espacoes,gbc2);
		
		//voto nulo
		gbc2.gridx = 1;
		gbc2.gridy = 3;
		votoNulo.setFont(fonte4);
		votoNulo.setVisible(false);
		painelCentralItens.add(votoNulo,gbc2);
		
		//fim
		gbc2.gridx = 1;
		gbc2.gridy = 2;
		fim.setFont(fonte7);
		fim.setVisible(false);
		painelCentralItens.add(fim,gbc2);
										
		painelCentralTela.add(painelCentralItens,BorderLayout.CENTER);
		
		mensagemVotoEmBranco.setFont(fonte4);//Voto branco
		mensagemVotoEmBranco.setVisible(false); //Voto branco
		painelCentralTela.add(mensagemVotoEmBranco,BorderLayout.SOUTH);//Voto branco
		
		painelTela.add(painelCentralTela,BorderLayout.CENTER);
		
		//-----ADICIOANANDO MENSAGEM DEFAULT
		gbc2.insets = new Insets(5,0,5,260); // Setando espaços para o GridBadLayout
		painelMensagemDefault.add(mensagemDefaultLabel,gbc2);
		
		gbc2.insets = new Insets(5,350,5,5); // Setando espaços para o GridBadLayout
		votou.setVisible(false);
		votou.setFont(fonte8);
		votou.setForeground(Color.GRAY);
		painelMensagemDefault.add(votou,gbc2);

		painelTela.add(painelMensagemDefault,BorderLayout.SOUTH);
		painelMensagemDefault.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		painelMensagemDefault.setVisible(false);
		
		//Cor da tela
		painelMensagemDefault.setBackground(new Color(228,228,228));
		painelTela.setBackground(new Color(228,228,228));
		painelMensagemTitulo.setBackground(new Color(228,228,228));
		painelCentralTelaCargo.setBackground(new Color(228,228,228));
		painelCentralTela.setBackground(new Color(228,228,228));
		painelCentralItens.setBackground(new Color(228,228,228));
		
		//Cor da urna
		painelGeral.setBackground(new Color(220,220,220));
		painelUrna.setBackground(new Color(220,220,220));
				
		gbc.insets = new Insets(15, 25, 15, 20); // Setando espaços para o GridBadLayout
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;
		painelUrna.add(painelTela,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		painelUrna.add(painelGeral,gbc);
		
		add(painelUrna);
		
		//------------------------------ICONE DA JANELA-----------------------------
		setIconImage( iconePersonalizado.getImage() );

		//-----------------------SETANDO ATRIBUTOS DO JFRAME------------------------
		pack();
		setTitle("Votação");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	/** Retorna referência do <code>Botao[]</code> botaoNumerico
	 * 
	 * @return referência do <code>Botao[]</code> botaoNumerico
	 *
	 * @see Botao 
	 */
	public Botao[] getBotaoNumerico() {
		return botaoNumerico;
	}

	/** Retorna referência do <code>Botao</code> botaoConfirma
	 * 
	 * @return referência do <code>Botao</code> botaoConfirma
	 *
	 * @see Botao 
	 */
	public Botao getBotaoConfirma() {
		return botaoConfirma;
	}

	/** Retorna referência do <code>Botao</code> botaoCorrige
	 * 
	 * @return referência do <code>Botao</code> botaoCorrige
	 *
	 * @see Botao 
	 */
	public Botao getBotaoCorrige() {
		return botaoCorrige;
	}

	/** Retorna referência do <code>Botao</code> botaoBranco
	 * 
	 * @return referência do <code>Botao</code> botaoBranco
	 *
	 * @see Botao 
	 */
	public Botao getBotaoBranco() {
		return botaoBranco;
	}

	/** Retorna referência do <code>JLabel</code> mensagemDefaultLabel
	 * 
	 * @return referência do <code>JLabel</code> mensagemDefaultLabel
	 * 
	 * @see JLabel 
	 */
	public JLabel getMensagemDefaultLabel() {
		return mensagemDefaultLabel;
	}

	/** Retorna referência do <code>JLabel</code> mensagemTituloLabel
	 * 
	 * @return referência do <code>JLabel</code> mensagemTituloLabel
	 * 
	 * @see JLabel 
	 */
	public JLabel getMensagemTituloLabel() {
		return mensagemTituloLabel;
	}

	/** Retorna referência do <code>JLabel</code> mensagemVotoEmBranco
	 * 
	 * @return referência do <code>JLabel</code> mensagemVotoEmBranco
	 * 
	 * @see JLabel 
	 */
	public JLabel getMensagemVotoEmBranco() {
		return mensagemVotoEmBranco;
	}

	/** Retorna referência do <code>JPanel</code> painelMensagemDefault
	 * 
	 * @return referência do <code>JPanel</code> painelMensagemDefault
	 * 
	 * @see JPanel 
	 */
	public JPanel getPainelMensagemDefault() {
		return painelMensagemDefault;
	}

	/** Retorna referência do <code>JLabel</code> cargoLabel
	 * 
	 * @return referência do <code>JLabel</code> cargoLabel
	 * 
	 * @see JLabel 
	 */
	public JLabel getCargoLabel() {
		return cargoLabel;
	}

	/** Retorna referência do {@code ArrayList<ArrayList<String>>} cargosParaVotacaoOrdenados
	 * 
	 * @return referência do {@code ArrayList<ArrayList<String>>} cargosParaVotacaoOrdenados
	 * 
	 * @see ArrayList 
	 */
	public ArrayList<ArrayList<String>> getCargosParaVotacaoOrdenados() {
		return cargosParaVotacaoOrdenados;
	}

	/** Retorna referência do <code>int</code> contadorCargos
	 * 
	 * @return referência do <code>int</code> contadorCargos
	 */
	public int getContadorCargos() {
		return contadorCargos;
	}

	/** Adiciona quantos cargos existem para rotacionar entre eles
	 * @param contadorCargos <code>int</code> com o contador de cargos
	 */
	public void setContadorCargos(int contadorCargos) {
		this.contadorCargos = contadorCargos;
	}

	/** Retorna referência do <code>JLabel</code> numeroFieldLabel
	 * 
	 * @return referência do <code>JLabel</code> numeroFieldLabel
	 * 
	 * @see JLabel 
	 */
	public JLabel getNumeroFieldLabel() {
		return numeroFieldLabel;
	}

	/** Retorna referência do <code>JLabel</code> numeroLabel
	 * 
	 * @return referência do <code>JLabel</code> numeroLabel
	 * 
	 * @see JLabel 
	 */
	public JLabel getNumeroLabel() {
		return numeroLabel;
	}

	/** Retorna referência do <code>JLabel</code> nomeLabel
	 * 
	 * @return referência do <code>JLabel</code> nomeLabel
	 * 
	 * @see JLabel 
	 */
	public JLabel getNomeLabel() {
		return nomeLabel;
	}

	/** Retorna referência do <code>JLabel</code> nomeFieldLabel
	 * 
	 * @return referência do <code>JLabel</code> nomeFieldLabel
	 * 
	 * @see JLabel 
	 */
	public JLabel getNomeFieldLabel() {
		return nomeFieldLabel;
	}

	/** Retorna referência do <code>JLabel</code> partidoLabel
	 * 
	 * @return referência do <code>JLabel</code> partidoLabel
	 * 
	 * @see JLabel 
	 */
	public JLabel getPartidoLabel() {
		return partidoLabel;
	}

	/** Retorna referência do <code>JLabel</code> partidoFieldLabel
	 * 
	 * @return referência do <code>JLabel</code> partidoFieldLabel
	 * 
	 * @see JLabel 
	 */
	public JLabel getPartidoFieldLabel() {
		return partidoFieldLabel;
	}

	/** Retorna referência do <code>JLabel</code> numeroErrado1
	 * 
	 * @return referência do <code>JLabel</code> numeroErrado1
	 * 
	 * @see JLabel 
	 */
	public JLabel getNumeroErrado1() {
		return numeroErrado1;
	}
	
	/** Retorna referência do <code>JLabel</code> numeroErrado2
	 * 
	 * @return referência do <code>JLabel</code> numeroErrado2
	 * 
	 * @see JLabel 
	 */
	public JLabel getNumeroErrado2() {
		return numeroErrado2;
	}

	/** Retorna referência do <code>JLabel</code> candidatoInexistente1
	 * 
	 * @return referência do <code>JLabel</code> candidatoInexistente1
	 * 
	 * @see JLabel 
	 */
	public JLabel getCandidatoInexistente1() {
		return candidatoInexistente1;
	}

	/** Retorna referência do <code>JLabel</code> candidatoInexistente2
	 * 
	 * @return referência do <code>JLabel</code> candidatoInexistente2
	 * 
	 * @see JLabel 
	 */
	public JLabel getCandidatoInexistente2() {
		return candidatoInexistente2;
	}

	/** Retorna referência do <code>JLabel</code> votoNulo
	 * 
	 * @return referência do <code>JLabel</code> votoNulo
	 * 
	 * @see JLabel 
	 */
	public JLabel getVotoNulo() {
		return votoNulo;
	}

	/** Retorna referência do <code>JLabel</code> espacoes
	 * 
	 * @return referência do <code>JLabel</code> espacoes
	 * 
	 * @see JLabel 
	 */
	public JLabel getEspacoes() {
		return espacoes;
	}

	/** Retorna referência do <code>JLabel</code> imagemCandidato
	 * 
	 * @return referência do <code>JLabel</code> imagemCandidato
	 * 
	 * @see JLabel 
	 */
	public JLabel getImagemCandidato() {
		return imagemCandidato;
	}

	/** Retorna se o voto do momento é nulo
	 * 
	 * @return <code>true</code> se voto atual é nulo
	 * 
	 */
	public boolean isVotoNuloAtivo() {
		return votoNuloAtivo;
	}

	/** Retorna se o voto do momento é branco
	 * 
	 * @return <code>true</code> se voto atual é branco
	 * 
	 */
	public boolean isVotoBrancoAtivo() {
		return votoBrancoAtivo;
	}

	/** Adiciona o voto do momento como nulo
	 * 
	 * @param votoNuloAtivo <code>boolean</code> com voto nulo
	 */
	public void setVotoNuloAtivo(boolean votoNuloAtivo) {
		this.votoNuloAtivo = votoNuloAtivo;
	}

	/** Adiciona o voto do momento como branco
	 * 
	 * @param votoBrancoAtivo <code>boolean</code> com voto em branco
	 */
	public void setVotoBrancoAtivo(boolean votoBrancoAtivo) {
		this.votoBrancoAtivo = votoBrancoAtivo;
	}

	/** Retorna se chegou ao fim do loop de votação
	 * 
	 * @return <code>true</code> se chegou ao fim do loop de votação
	 * 
	 */
	public boolean isFimAtivo() {
		return fimAtivo;
	}

	/** Adiciona true quando chegar ao final do loop de votação
	 * 
	 * @param fimAtivo <code>boolean</code> ao chegar ao fim do loop de votação
	 */
	public void setFimAtivo(boolean fimAtivo) {
		this.fimAtivo = fimAtivo;
	}

	/** Retorna referência do <code>JLabel</code> fim
	 * 
	 * @return referência do <code>JLabel</code> fim
	 * 
	 * @see JLabel 
	 */
	public JLabel getFim() {
		return fim;
	}

	/** Retorna referência do <code>JLabel</code> votou
	 * 
	 * @return referência do <code>JLabel</code> votou
	 * 
	 * @see JLabel 
	 */
	public JLabel getVotou() {
		return votou;
	}
}
