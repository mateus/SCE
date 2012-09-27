package br.edu.ifsudestemg.tsi.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Cria conteúdo gráfico da tela de dados do candidato
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 * @see JDialog
 */
public class TelaDadosCandidato extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Icones
	private ImageIcon iconePersonalizado = new ImageIcon("icones/logo2.png" );
	private ImageIcon candidatoImagem;

	//Paineis
	private JPanel painelCentral = new JPanel();
	
	//Rótulos
	private JLabel dadosLabel = new JLabel();
	private JLabel imagemLabel;
	
	//Fonte
	private Font fonte = new Font("Arial", Font.PLAIN, 16); 
	
	/**
	 * Construtor default que criará todo conteúdo gráfico da tela
	 */ 
	public TelaDadosCandidato(){
		super();
	}
	
	/**
	 * Construtor default que criará todo conteúdo gráfico da tela com conteúdos fornecidos 
	 * 
	 * @param diretorio <code>String</code> com o diretório da imagem do candidato
	 * @param nome <code>String</code> com o nome do candidato
	 * @param cargo <code>String</code> com com o cargo do candidato
	 * @param partido <code>String</code> com com o partido do candidato
	 * @param numero <code>String</code> com com o número do candidato
	 */
	public TelaDadosCandidato(String diretorio, String nome, String cargo,
			String partido, String numero) {
		super();
		
		//----------------------------ADICIONANDO COR BRANCA--------------------------
		painelCentral.setBackground(Color.WHITE);
		
		//-----------------------ADICIONANDO DADOS DO CANDIDATO-----------------------
		dadosLabel.setFont(fonte);
		dadosLabel.setText("<html><b>Nome: </b>"+ nome + 
				"<br><br><b>Cargo:</b> " + cargo + 
				"<br><br><b>Partido: </b>" + partido + 
				"<br><br><b>Número: </b>" + numero + 
				"</html>" );
		
		//-------------------------------ADICIONANDO IMAGEM---------------------------
		candidatoImagem = new ImageIcon(diretorio);
		imagemLabel = new JLabel(candidatoImagem);
		imagemLabel.setBorder(BorderFactory.createTitledBorder(""));
		
		//-----------------------------ADICIONANDO PAINEIS----------------------------
		painelCentral.add(dadosLabel);
		painelCentral.add(imagemLabel);
		add(painelCentral);
		
		//------------------------------ICONE DA JANELA-------------------------------
		setIconImage( iconePersonalizado.getImage() );
		
		//------------------------SETANDO ATRIBUTOS DO JDIALOG------------------------
		setTitle("Dados do Candidato");
		pack();
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);	
	}
}
