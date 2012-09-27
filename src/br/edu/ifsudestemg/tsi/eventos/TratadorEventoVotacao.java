package br.edu.ifsudestemg.tsi.eventos;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import br.edu.ifsudestemg.tsi.gui.TelaVotacao;
import br.edu.ifsudestemg.tsi.persistencia.VotacaoBD;

/**Tratador de eventos para a tela de simular votação
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see ActionListener
 * @see MouseAdapter
 *
 */
public class TratadorEventoVotacao extends MouseAdapter implements ActionListener{

	private TelaVotacao gui;
	private VotacaoBD votacaoBD = new VotacaoBD();
	private ImageIcon imagem;

	/** Construtor default
	 * @param gui <code>TelaVotacao</code> com a referência da tela de simular votação
	 * cargos para votação
	 * 
	 * @see TelaVotacao
	 */
	public TratadorEventoVotacao(TelaVotacao gui) {
		super();
		this.gui = gui;
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent evento) {

		//Instancia variavel para som dos botões
		AudioClip somClick = null;
		try {
			somClick = Applet.newAudioClip(new File("sons/somUrna2.wav").toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		AudioClip somClick2 = null;
		try {
			somClick2 = Applet.newAudioClip(new File("sons/somUrna.wav").toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		//Botão BRANCO
		if(evento.getSource() == gui.getBotaoBranco()){

			if(gui.getNumeroFieldLabel().getText().isEmpty()){

				gui.getNumeroLabel().setVisible(false);
				gui.getNumeroFieldLabel().setVisible(false);
				gui.getNomeLabel().setVisible(false);
				gui.getNomeFieldLabel().setText("");
				gui.getPartidoLabel().setVisible(false);
				gui.getPartidoFieldLabel().setText("");

				gui.getEspacoes().setVisible(true);
				gui.getMensagemVotoEmBranco().setVisible(true);
				gui.getPainelMensagemDefault().setVisible(true);
				gui.getMensagemDefaultLabel().setVisible(true);
				imagem = new ImageIcon("imagens//transparente.png");
				imagem.setImage(imagem.getImage().getScaledInstance(120, 140, 100));
				gui.getImagemCandidato().setIcon(imagem);
				gui.getImagemCandidato().setBorder(null);

				gui.getMensagemTituloLabel().setText(
						"<html><font size = 4>TREINAMENTO</font>" +
								"<br>" +
								"<font size = 4><b>SEU VOTO PARA</b></font>" +
						"</html>");

				gui.setVotoBrancoAtivo(true);
			}
		}

		//Botão CORRIGE
		if(evento.getSource() == gui.getBotaoCorrige()){

			gui.getNumeroLabel().setVisible(false);
			gui.getNumeroFieldLabel().setText("");
			gui.getNumeroFieldLabel().setVisible(true);
			imagem = new ImageIcon("imagens//transparente.png");
			imagem.setImage(imagem.getImage().getScaledInstance(120, 140, 100));
			gui.getImagemCandidato().setIcon(imagem);
			gui.getImagemCandidato().setBorder(null);

			gui.getNomeLabel().setVisible(false);
			gui.getNomeFieldLabel().setText("");

			gui.getNumeroErrado1().setVisible(false);
			gui.getNumeroErrado2().setVisible(false);

			gui.getPartidoLabel().setVisible(false);
			gui.getPartidoFieldLabel().setText("");

			gui.getEspacoes().setVisible(true);

			gui.getCandidatoInexistente1().setVisible(false);
			gui.getCandidatoInexistente2().setVisible(false);

			gui.getVotoNulo().setVisible(false);

			gui.getMensagemTituloLabel().setText(
					"<html><font size = 4>TREINAMENTO</font>" +
							"<br>" +
							"<font size = 4 color = rgb(228,228,228)> <b>SEU VOTO PARA</b></font>" +
					"</html>");
			gui.getMensagemVotoEmBranco().setVisible(false);
			gui.getPainelMensagemDefault().setVisible(false);

			gui.setVotoBrancoAtivo(false);
			gui.setVotoNuloAtivo(false);
		}

		//Botão CONFIRMA
		if(evento.getSource() == gui.getBotaoConfirma()){

			/*
			 * Libera botao se for voto nulo || voto em branco || chegou no final || 
			 * Libera botao se numero total de digitos foi atingido
			 */
			if( gui.isVotoNuloAtivo() || gui.isVotoBrancoAtivo() || gui.isFimAtivo() ||
					gui.getNumeroFieldLabel().getText().length() == Integer.parseInt(
							gui.getCargosParaVotacaoOrdenados().get(gui.getContadorCargos()).get(1) ) ){

				if(gui.isVotoBrancoAtivo()){
					System.out.println("\nVoto em branco");
					votacaoBD.insereVoto("BRANCO", "0", gui.getCargoLabel().getText());
				}
				else
					if(gui.isVotoNuloAtivo()){
						System.out.println("\nVoto nulo");
						votacaoBD.insereVoto("NULO", "1", gui.getCargoLabel().getText());
					}else{
						System.out.println("Voto para: " + gui.getNomeFieldLabel().getText() + " - " + gui.getNumeroFieldLabel().getText());
						votacaoBD.insereVoto(
								gui.getNomeFieldLabel().getText(), 
								gui.getNumeroFieldLabel().getText(),
								gui.getCargoLabel().getText());
					}
				
				if(gui.getContadorCargos() < gui.getCargosParaVotacaoOrdenados().size()-1){
					somClick.play();
					gui.setContadorCargos(gui.getContadorCargos()+1);
					gui.getCargoLabel().setText(
							gui.getCargosParaVotacaoOrdenados().get(gui.getContadorCargos()).get(0));
				}
				else{
					somClick2.play();
					gui.setFimAtivo(true);
				}

				//RESETA TUDO
				gui.getNumeroLabel().setVisible(false);
				gui.getNumeroFieldLabel().setText("");
				gui.getNumeroFieldLabel().setVisible(true);
				imagem = new ImageIcon("imagens//transparente.png");
				imagem.setImage(imagem.getImage().getScaledInstance(120, 140, 100));
				gui.getImagemCandidato().setIcon(imagem);
				gui.getImagemCandidato().setBorder(null);

				gui.getNomeLabel().setVisible(false);
				gui.getNomeFieldLabel().setText("");

				gui.getNumeroErrado1().setVisible(false);
				gui.getNumeroErrado2().setVisible(false);

				gui.getPartidoLabel().setVisible(false);
				gui.getPartidoFieldLabel().setText("");

				gui.getEspacoes().setVisible(true);

				gui.getCandidatoInexistente1().setVisible(false);
				gui.getCandidatoInexistente2().setVisible(false);

				gui.getVotoNulo().setVisible(false);

				gui.getMensagemTituloLabel().setText(
						"<html><font size = 4>TREINAMENTO</font>" +
								"<br>" +
								"<font size = 4 color = rgb(228,228,228)> <b>SEU VOTO PARA</b></font>" +
						"</html>");
				gui.getMensagemVotoEmBranco().setVisible(false);
				gui.getPainelMensagemDefault().setVisible(false);

				gui.setVotoBrancoAtivo(false);
				gui.setVotoNuloAtivo(false);
				
				//Tela de FIM
				if(gui.isFimAtivo()){
					gui.getNumeroFieldLabel().setVisible(false);
					gui.getCargoLabel().setVisible(false);
					gui.getFim().setVisible(true);
					gui.getPainelMensagemDefault().setVisible(true);
					gui.getPainelMensagemDefault().setBorder(null);
					gui.getMensagemDefaultLabel().setVisible(false);
					gui.getVotou().setVisible(true);
					
					int op;
					op = JOptionPane.showConfirmDialog(null, 
							"Deseja continuar com a votação ?" , "Atenção" , JOptionPane.YES_NO_OPTION);

					//Caso tenha clicado em SIM
					if(op != 1){	
						gui.setFimAtivo(false);
						gui.getNumeroFieldLabel().setVisible(true);
						gui.setContadorCargos(0);
						gui.getCargoLabel().setVisible(true);
						gui.getCargoLabel().setText(
								gui.getCargosParaVotacaoOrdenados().get(gui.getContadorCargos()).get(0));
						gui.getFim().setVisible(false);
						gui.getPainelMensagemDefault().setVisible(false);
						gui.getPainelMensagemDefault().setBorder(
								BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
						gui.getMensagemDefaultLabel().setVisible(false);
						gui.getVotou().setVisible(false);
					}else
						gui.dispose();
				}

			}
		}

		//Botões numéricos
		if(verificaBotao((JButton)evento.getSource()) && !gui.isVotoBrancoAtivo()){

			//Limita digitos
			if(gui.getNumeroFieldLabel().getText().length() < Integer.parseInt(
					gui.getCargosParaVotacaoOrdenados().get(gui.getContadorCargos()).get(1) )){
				gui.getNumeroFieldLabel().setText( 
						gui.getNumeroFieldLabel().getText() + 
						( (JButton)evento.getSource() ).getText());
			}

			//Verifica Partido
			if(gui.getNumeroFieldLabel().getText().length() == 2){
				//Verifica se existe o partido com a legenda
				if( votacaoBD.consultaPartido(gui.getNumeroFieldLabel().getText()) != null ){
					gui.getPartidoLabel().setVisible(true);
					gui.getPartidoFieldLabel().setVisible(true);
					gui.getNumeroLabel().setVisible(true);
					gui.getPartidoFieldLabel().setText(
							votacaoBD.consultaPartido(gui.getNumeroFieldLabel().getText()));
					gui.getPainelMensagemDefault().setVisible(true);
					gui.getMensagemDefaultLabel().setVisible(true);
					
					gui.getMensagemTituloLabel().setText(
							"<html><font size = 4>TREINAMENTO</font>" +
									"<br>" +
									"<font size = 4><b>SEU VOTO PARA</b></font>" +
							"</html>");
				}
				//Numero foi invalido
				else{
					gui.getNumeroLabel().setVisible(true);
					gui.getNumeroErrado1().setVisible(true);
					gui.getNumeroErrado2().setVisible(true);
					gui.getEspacoes().setVisible(false);
					gui.getVotoNulo().setVisible(true);

					gui.getPainelMensagemDefault().setVisible(true);
					gui.getMensagemDefaultLabel().setVisible(true);
					
					gui.getMensagemTituloLabel().setText(
							"<html><font size = 4>TREINAMENTO</font>" +
									"<br>" +
									"<font size = 4><b>SEU VOTO PARA</b></font>" +
							"</html>");

					gui.setVotoNuloAtivo(true);
				}
			}

			//Verifica candidato
			if(gui.getNumeroFieldLabel().getText().length() == Integer.parseInt(
					gui.getCargosParaVotacaoOrdenados().get(gui.getContadorCargos()).get(1) )){
				
				//Verifica se o nome existe
				if(votacaoBD.consultaNome( gui.getNumeroFieldLabel().getText(), 
						gui.getCargoLabel().getText() ) != null ){

					gui.getNomeLabel().setVisible(true);
					StringTokenizer nome = new StringTokenizer( 
							votacaoBD.consultaNome( gui.getNumeroFieldLabel().getText(), 
									gui.getCargoLabel().getText() ) );	
					String nomeCandidato = "";
					int i = 0;
					while (nome.hasMoreTokens() && i < 2 ) {
						nomeCandidato += nome.nextToken() + " ";
						i++;
					}

					gui.getNomeFieldLabel().setText(nomeCandidato);	
					gui.getPainelMensagemDefault().setVisible(true);
					gui.getMensagemDefaultLabel().setVisible(true);
					
					gui.getMensagemTituloLabel().setText(
							"<html><font size = 4>TREINAMENTO</font>" +
									"<br>" +
									"<font size = 4><b>SEU VOTO PARA</b></font>" +
							"</html>");

					//Adiciona imagem
					BufferedImage fundo = null;
					try {
						fundo = ImageIO.read(new File(votacaoBD.consultaImagem(
								gui.getNumeroFieldLabel().getText(), 
								gui.getCargoLabel().getText())));
					} catch (IOException e) {
						e.printStackTrace();
					}

					imagem = toGrayscale(fundo);
					imagem.setImage(imagem.getImage().getScaledInstance(120, 140, 100)); 
					gui.getImagemCandidato().setBorder(BorderFactory.createLineBorder(Color.BLACK));
					gui.getImagemCandidato().setIcon(imagem);

				}
				//Candidato inexistente
				else{
					if( votacaoBD.consultaPartido(
							gui.getNumeroFieldLabel().getText().substring(0, 2)) != null ){
						gui.getCandidatoInexistente1().setVisible(true);
						gui.getCandidatoInexistente2().setVisible(true);
						gui.getPainelMensagemDefault().setVisible(true);
						gui.getMensagemDefaultLabel().setVisible(true);
						gui.getEspacoes().setVisible(false);
						gui.getVotoNulo().setVisible(true);

						gui.getMensagemTituloLabel().setText(
								"<html><font size = 4>TREINAMENTO</font>" +
										"<br>" +
										"<font size = 4><b>SEU VOTO PARA</b></font>" +
								"</html>");

						gui.setVotoNuloAtivo(true);
					}
				}
			}
		}
	}

	/** Verifica qual botão foi clicado no painel
	 * 
	 * @param botao <code>JButton</code> com o botão clicado
	 * 
	 * @return <code>true</code> ao validar clique no botão
	 */
	public boolean verificaBotao(JButton botao){
		for(int i = 0; i < gui.getBotaoNumerico().length ; i++){
			if(botao == gui.getBotaoNumerico()[i]){
				return true;
			}
		}
		return false;
	}

	/**  
	 * Converte a imagem para escala de cinza.  
	 *   
	 * @param image Imagem a ser convertida  
	 * @return a Imagem convertida.  
	 */    
	private static ImageIcon toGrayscale(BufferedImage image) {    
		BufferedImage output = new BufferedImage(image.getWidth(),    
				image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);    
		Graphics2D g2d = output.createGraphics();    
		g2d.drawImage(image, 0, 0, null);    
		g2d.dispose(); 
		ImageIcon icon = new ImageIcon(output.getScaledInstance(output.getWidth(), output.getHeight(), 10000));
		return icon;  
	} 
}
