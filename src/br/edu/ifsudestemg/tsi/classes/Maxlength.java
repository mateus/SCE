package br.edu.ifsudestemg.tsi.classes;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


/**Determina limite de caracteres
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see PlainDocument
 *
 */
public class Maxlength extends PlainDocument{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int limite; // Máximo de caracteres permitidos 
	
	
	/**Construtor que determina limite de caracteres
	 * 
	 * @param maximoCaracteres <code>int</code> com máximo de caracteres desejados
	 */
	public Maxlength(int maximoCaracteres) {
		limite = maximoCaracteres;
	}
	
		// Método chamado quando se tenta inserir um texto 
	    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {  
	
	    	// Calcula quantos caracteres podem ser inseridos antes de atingir o limite  
	        int sobra = limite - getLength();  

	        // Calcula o comprimento máximo da string que pode ser inserida, sem que o limite seja quebrado.   
	        int comprimento = (sobra > str.length()) ? str.length() : sobra;  
	
	        // Isso faz com que a string resultante seja realmente inserida 
	        super.insertString(offs, str.substring(0, comprimento), a);  
	    }  
}
