package br.edu.ifsudestemg.tsi.classes;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**Define um LookAndFeel para a aplicação
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see UIManager
 *
 */
public class Tema {

	//Alterar tema do aplicativo
	/**Altera o tema da aplicação
	 * 
	 * @param nomeTema <code>String</code> com o nome do tempo desejado
	 */
	public static void MudarTema(String nomeTema){
		try {
			for (LookAndFeelInfo tem : UIManager.getInstalledLookAndFeels()) 
				if (nomeTema.equalsIgnoreCase(tem.getName())) {
					UIManager.setLookAndFeel(tem.getClassName());
					break;
				}
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Erro ao carregar Tema");
		}
	}

}
