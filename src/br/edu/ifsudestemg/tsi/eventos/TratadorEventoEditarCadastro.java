package br.edu.ifsudestemg.tsi.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.edu.ifsudestemg.tsi.candidato.Candidato;
import br.edu.ifsudestemg.tsi.classes.Maxlength;
import br.edu.ifsudestemg.tsi.gui.TelaEditarCadastro;
import br.edu.ifsudestemg.tsi.persistencia.CargoBD;
import br.edu.ifsudestemg.tsi.persistencia.PartidoBD;

/**Tratador de eventos para a tela de editar cadastros de candidatos
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see MouseAdapter
 * @see ActionListener
 * @see KeyListener
 * @see ItemListener
 *
 */
public class TratadorEventoEditarCadastro extends MouseAdapter implements ActionListener,KeyListener,ItemListener {

	TelaEditarCadastro gui;
	private ImageIcon imagem;
	private File ArqImagem = null; 
	private Candidato candidato = new Candidato();
	private CargoBD cargoBD = new CargoBD();
	private PartidoBD partidoBD = new PartidoBD();

	/** Construtor default
	 * @param gui <code>TelaEditarCadastro</code> com a referência da tela de editar cadastros de candidatos
	 * 
	 * @see TelaEditarCadastro
	 */
	public TratadorEventoEditarCadastro(TelaEditarCadastro gui) {
		super();
		this.gui = gui;
	}

	public void itemStateChanged(ItemEvent evento) {
		
		if(evento.getSource() == gui.getCargoCB() || evento.getSource() == gui.getPartidosCB() ){
			
			//Verifica o campo Cargo e seta o tamanho dele para o campo numero
			gui.getNumeroField().setDocument(
				new Maxlength(
				cargoBD.consultaDigitos(
						gui.getCargoCB().getItemAt(gui.getCargoCB().getSelectedIndex()))));

			//Verifica o campo Partido e seta a legenda do partido no campo numero
			gui.getNumeroField().setText("" + partidoBD.consultaLegenda(
					gui.getPartidosCB().getItemAt(
							gui.getPartidosCB().getSelectedIndex())));
		}
		
	}

	//VALIDA CAMPOS PARA LIBERAR O BOTAO CADASTRAR----------
	public void keyReleased(KeyEvent evento) {
		if(!gui.getNomeCandidatoField().getText().isEmpty() && !gui.getNumeroField().getText().isEmpty()){
			gui.getAlterar().setEnabled(true);
		}
		else
			gui.getAlterar().setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		//Botao cadastrar
		if(evento.getSource() == gui.getAlterar()){

			try {	
				//recebe valores dos campos fornecidos
				candidato.setNome(gui.getNomeCandidatoField().getText());
				candidato.setPartido(gui.getPartidosCB().getItemAt(gui.getPartidosCB().getSelectedIndex()));
				candidato.setNumero(gui.getNumeroField().getText());
				candidato.setCargo(gui.getCargoCB().getItemAt(gui.getCargoCB().getSelectedIndex()));
				
				//Verifica se trocou a imagem
				if (ArqImagem == null){
					candidato.setDiretorioImagem(gui.getDiretorioAntigo());
				}else
					candidato.setDiretorioImagem("ImagensCandidatos/"+ArqImagem.getName());

				//valida legenda do numero
				if( partidoBD.consultaLegenda(
						gui.getPartidosCB().getItemAt(
								gui.getPartidosCB().getSelectedIndex())) != Integer.parseInt( 
										candidato.getNumero().substring(0, 2))){

					JOptionPane.showMessageDialog(null, "Legenda não está correta","Erro",
							JOptionPane.ERROR_MESSAGE);

				}
				//valida quantidade de digitos presentes no campo numero
				else if( gui.getNumeroField().getText().length() != cargoBD.consultaDigitos(candidato.getCargo())){
					JOptionPane.showMessageDialog(null, "Quantidade de dígitos fornecidos não está correta","Erro",
							JOptionPane.ERROR_MESSAGE);
				}
				//valida unicidade da chave NUMERO e CARGO
				else if( candidato.pesquisaPorNumeroCargo(candidato.getNumero(),candidato.getCargo()) )
				{
					JOptionPane.showMessageDialog(null, "Numero já cadastrado para este cargo","Erro",
							JOptionPane.ERROR_MESSAGE);
				}
				//Realiza gravação dos dados no banco de dados
				else  
				{
					candidato.alteraBD(candidato,gui.getNumeroAntigo(),gui.getCargoAntigo());
					gui.dispose();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"\nINFORME O SEGUINTE ERRO AO ANALISTA DE SOFTWARE: "+e.toString(),"Erro",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			//Fechar janela de cadastro
		}

		//Botao limpar
		if(evento.getSource() == gui.getLimpar()){
			//Limpa todos os campos do formulário
			gui.getNomeCandidatoField().setText("");
			gui.getCargoCB().setSelectedIndex(0);
			gui.getPartidosCB().setSelectedIndex(0);
			//Poew o numero correto
			gui.getNumeroField().setText("" + partidoBD.consultaLegenda(
					gui.getPartidosCB().getItemAt(
							gui.getPartidosCB().getSelectedIndex())));
			imagem = new ImageIcon("Imagens/usuario.png");
			gui.getAlterar().setEnabled(false);
			gui.imagemClicavel.setIcon(imagem);
		}
	}

	public void mouseClicked(MouseEvent evento) {

		//Carregar imagem ----------------------------------------------------------------
		if(evento.getSource() == gui.getImagemClicavel()){

			//Janela FileChoose -------------------------------------------
			JFileChooser abrirImagem = new JFileChooser();
			abrirImagem.setFileSelectionMode(JFileChooser.FILES_ONLY);
			abrirImagem.setDialogType(JFileChooser.SAVE_DIALOG);
			abrirImagem.setCurrentDirectory(new File("."));
			abrirImagem.setDialogTitle("Abrir Imagem");
			abrirImagem.setApproveButtonText("Abrir");

			//Filtro
			abrirImagem.setAcceptAllFileFilterUsed(false);
			abrirImagem.addChoosableFileFilter(new FileNameExtensionFilter(
					"Imagem (*.jpg, *.png, *.gif, *.bmp)", "jpg" , "png" , "gif" , "bmp", "jpeg"));

			//Verifica botão cancelar
			if(abrirImagem.showOpenDialog(gui) == JFileChooser.CANCEL_OPTION)
				return;

			//Recebe arquivo aberto e seu diretório
			ArqImagem = abrirImagem.getSelectedFile();
			String nomeArquivo = ArqImagem.getPath();
			File DiretorioImagens = new File("ImagensCandidatos");
			String nomeDiretorioImagens = DiretorioImagens.getPath();

			try {
				// Cria channel na origem
				FileChannel origemChannel = new FileInputStream(nomeArquivo).getChannel();

				// Cria channel no destino
				FileChannel destinoChannel = new FileOutputStream(
						nomeDiretorioImagens+"/"+ArqImagem.getName()).getChannel();

				// Copia conteúdo da origem no destino
				destinoChannel.transferFrom(origemChannel, 0, origemChannel.size());

				// Fecha channels
				origemChannel.close();
				destinoChannel.close();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Imagem não carregada para o sistema","Erro",
						JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Imagem não carregada para o sistema","Erro",
						JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Imagem não carregada para o sistema","Erro",
						JOptionPane.ERROR_MESSAGE);			
			}

			//Adicionando ao objeto imagem a imagem selecionada / Colocando imagem em escala (120x140)
			imagem = new ImageIcon (nomeDiretorioImagens+"/"+ArqImagem.getName()); 
			imagem.setImage(imagem.getImage().getScaledInstance(120, 140, 100));  

			//Enviando para objeto de imagem presente na tela gráfica
			gui.imagemClicavel.setIcon(imagem);

		}		
	}

	@Override
	public void keyPressed(KeyEvent evento) {

	}

	@Override
	public void keyTyped(KeyEvent evento) {

	}

}
