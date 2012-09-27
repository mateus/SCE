package br.edu.ifsudestemg.tsi.classes;

import java.util.GregorianCalendar;

/**Manipula o dia, mes e ano atuais
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 * 
 * @see GregorianCalendar
 *
 */
public class Data {

	private String dia,mes,ano;
	private String data = "";

	/**
	 * Retorna <code>String</code> com dia atual
	 * 
	 * @return Uma <code>String</code> com o dia atual
	 */
	public String getDia() {
		return dia;
	}

	/**
	 * Retorna <code>String</code> com mês atual
	 * 
	 * @return Uma <code>String</code> com o mês atual
	 */
	public String getMes() {
		return mes;
	}

	/**
	 * Retorna <code>String</code> com ano atual
	 * 
	 * @return Uma <code>String</code> com o ano atual
	 */
	public String getAno() {
		return ano;
	}
	/**
	 * Retorna <code>String</code> com a data formatada
	 * 
	 * @return Uma <code>String</code> com o a data formatada
	 */
	public String getData() {
		return data;
	}

	/**
	 * Formata a data atual
	 */
	public void dataAtual(){
		GregorianCalendar calendario = new GregorianCalendar();  
		dia = "" + calendario.get(GregorianCalendar.DAY_OF_MONTH);  
		int m = calendario.get(GregorianCalendar.MONTH);  
		ano = "" + calendario.get(GregorianCalendar.YEAR);  

		switch (m)
		{
		case 0: mes = "Janeiro"; break;
		case 1: mes = "Fevereiro"; break;
		case 2: mes = "Março"; break;
		case 3: mes = "Abril"; break;
		case 4: mes = "Maio"; break;
		case 5: mes = "Junho"; break;
		case 6: mes = "Julho"; break;
		case 7: mes = "Agosto"; break;
		case 8: mes = "Setembro"; break;
		case 9: mes = "Outubro"; break;
		case 10: mes = "Novembro"; break;
		case 11: mes = "Dezembro"; break;       
		}
		data = dia + " de " + mes + " de " + ano;
	}

}
