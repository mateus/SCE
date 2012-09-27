package br.edu.ifsudestemg.tsi.classes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;

/** Cria um novo layout para o JButton
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see JButton
 *
 */
public class Botao extends JButton{

	private static final long serialVersionUID = 9032198251140247116L;

	boolean mouseIn = false;
	Color corBotao;
	Color corBotaoSelecionado;

	/** Construtor Sobrecarregado passando o nome e o tipo do botão
	 * 0 - Botões pretos
	 * 1 - Botões confirma
	 * 2 - Botões corrige
	 * 3 - Botões branco
	 * 
	 * @param nome <code>String</code> com nome do botão
	 * @param tipo <code>int</code> com tipo do botão
	 */
	public Botao(String nome,int tipo) {
		super(nome);
		setBorderPainted(false);
		setContentAreaFilled(false);

		Toolkit toolkit = Toolkit.getDefaultToolkit();  
		try{
			File file = new File("imagens/mao.png");  
			Image image = ImageIO.read(file);  
			Cursor c = toolkit.createCustomCursor(image, new Point(this.getX(), this.getY()), "cursor"); 
			setCursor(c);
		}
		catch (Exception e) {
		}

		switch (tipo) {
		//Botoes pretos
		case 0:
			corBotao = new Color(55, 55, 55);
			corBotaoSelecionado = new Color(30,30,30);
			break;

			//Confirma
		case 1:
			corBotao = new Color(117, 210, 136);
			corBotaoSelecionado = new Color(100,190,115);
			break;

			//Corrige
		case 2:
			corBotao = new Color(250, 150, 0);
			corBotaoSelecionado = new Color(240,130,0);
			break;

			//Branco
		case 3:
			corBotao = new Color(245, 245, 245);
			corBotaoSelecionado = new Color(220,220,220);
			break;

		}
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		if (getModel().isPressed()) {
			g.setColor(corBotaoSelecionado);
			g2.fillRect(3, 3, getWidth() - 6, getHeight() - 6);
		}else
		{
			g.setColor(corBotao);
			g2.fillRect(3, 3, getWidth() - 6, getHeight() - 6);
		}

		super.paintComponent(g);

		if (mouseIn)
			g2.setColor(Color.BLACK);
		else
			g2.setColor(new Color(20, 20, 20));

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(1.2f));
		g2.draw(new RoundRectangle2D.Double(1, 1, (getWidth() - 3),(getHeight() - 3), 5, 5));
		g2.setStroke(new BasicStroke(1.5f));
		g2.drawLine(4, getHeight() - 3, getWidth() - 4, getHeight() - 3);

		g2.dispose();
	}
}
