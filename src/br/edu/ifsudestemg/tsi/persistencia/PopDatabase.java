package br.edu.ifsudestemg.tsi.persistencia;

import javax.swing.JOptionPane;

/**Popula o banco de dados para executar testes no sistema
 * 
 * @author Mateus Ferreira
 * @author Vinicius Lehmann
 *
 */
public class PopDatabase extends Database{

	/**Construtor default
	 * 
	 */
	public PopDatabase() {
		super();
		populaBanco();
	}

	/**Popula o banco de dados com candidatos, cargos e partidos
	 * 
	 */
	public void populaBanco(){
		abreConexao();
		
		int op;
		op = JOptionPane.showConfirmDialog(null, "Atenção: Deseja deletar todos os registros armazenados \nno " +
				"bando de dados atualmente ? Desta forma será garantida \na inserção dos novos registros. " , 
				"Atenção" , JOptionPane.YES_NO_OPTION);

		//Caso tenha clicado em SIM
		if(op != 1){
			gravar("TRUNCATE TABLE candidato CASCADE");
			gravar("TRUNCATE TABLE cargo CASCADE");
			gravar("TRUNCATE TABLE partido CASCADE");
		}

		//Cargos
		gravar("INSERT INTO cargo VALUES('PRESIDENTE',2)");
		gravar("INSERT INTO cargo VALUES('DEPUTADO FEDERAL',4)");
		gravar("INSERT INTO cargo VALUES('DEPUTADO ESTADUAL',5)");
		
		//Partidos
		gravar("INSERT INTO partido VALUES('PDT',12)");
		gravar("INSERT INTO partido VALUES('PT',13)");
		gravar("INSERT INTO partido VALUES('PMDB',15)");
		gravar("INSERT INTO partido VALUES('PV',43)");
		gravar("INSERT INTO partido VALUES('PSDB',45)");
		gravar("INSERT INTO partido VALUES('PCdoB',65)");
		
		
		//PRESIDENTE 
		gravar("INSERT INTO candidato VALUES('Presidente 01','PDT',12,'PRESIDENTE','ImagensCandidatos/f5.png')");
		gravar("INSERT INTO candidato VALUES('Presidente 02','PT',13,'PRESIDENTE','ImagensCandidatos/f22.png')");
		gravar("INSERT INTO candidato VALUES('Presidente 03','PMDB',15,'PRESIDENTE','ImagensCandidatos/f4.png')");
		gravar("INSERT INTO candidato VALUES('Presidente 04','PV',43,'PRESIDENTE','ImagensCandidatos/f16.png')");
		gravar("INSERT INTO candidato VALUES('Presidente 05','PSDB',45,'PRESIDENTE','ImagensCandidatos/mig29.png')");
		gravar("INSERT INTO candidato VALUES('Presidente 06','PCdoB',65,'PRESIDENTE','ImagensCandidatos/su27.png')");

		//DEPUTADO FEDERAL
		gravar("INSERT INTO candidato VALUES('DepFed 01','PDT',1201,'DEPUTADO FEDERAL','ImagensCandidatos/18.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 02','PDT',1202,'DEPUTADO FEDERAL','ImagensCandidatos/b2.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 03','PDT',1203,'DEPUTADO FEDERAL','ImagensCandidatos/f16.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 04','PDT',1204,'DEPUTADO FEDERAL','ImagensCandidatos/f22.png')");
		
		gravar("INSERT INTO candidato VALUES('DepFed 05','PT',1301,'DEPUTADO FEDERAL','ImagensCandidatos/f5.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 06','PT',1302,'DEPUTADO FEDERAL','ImagensCandidatos/a1amx.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 07','PT',1303,'DEPUTADO FEDERAL','ImagensCandidatos/18.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 08','PT',1304,'DEPUTADO FEDERAL','ImagensCandidatos/f16.png')");
		
		gravar("INSERT INTO candidato VALUES('DepFed 09','PMDB',1501,'DEPUTADO FEDERAL','ImagensCandidatos/f16.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 10','PMDB',1502,'DEPUTADO FEDERAL','ImagensCandidatos/b2.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 11','PMDB',1503,'DEPUTADO FEDERAL','ImagensCandidatos/f22.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 12','PMDB',1504,'DEPUTADO FEDERAL','ImagensCandidatos/f4.png')");
		
		gravar("INSERT INTO candidato VALUES('DepFed 13','PV',4301,'DEPUTADO FEDERAL','ImagensCandidatos/f16.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 14','PV',4302,'DEPUTADO FEDERAL','ImagensCandidatos/b2.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 15','PV',4303,'DEPUTADO FEDERAL','ImagensCandidatos/f22.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 16','PV',4304,'DEPUTADO FEDERAL','ImagensCandidatos/f4.png')");
		
		gravar("INSERT INTO candidato VALUES('DepFed 17','PSDB',4501,'DEPUTADO FEDERAL','ImagensCandidatos/f16.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 18','PSDB',4502,'DEPUTADO FEDERAL','ImagensCandidatos/b2.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 19','PSDB',4503,'DEPUTADO FEDERAL','ImagensCandidatos/f22.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 20','PSDB',4504,'DEPUTADO FEDERAL','ImagensCandidatos/f4.png')");
		
		gravar("INSERT INTO candidato VALUES('DepFed 21','PCdoB',6501,'DEPUTADO FEDERAL','ImagensCandidatos/f16.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 22','PCdoB',6502,'DEPUTADO FEDERAL','ImagensCandidatos/b2.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 23','PCdoB',6503,'DEPUTADO FEDERAL','ImagensCandidatos/f22.png')");
		gravar("INSERT INTO candidato VALUES('DepFed 24','PCdoB',6504,'DEPUTADO FEDERAL','ImagensCandidatos/f4.png')");
		
		//DEPUTADO ESTADUAL
		gravar("INSERT INTO candidato VALUES('DepEst 01','PDT',12001,'DEPUTADO ESTADUAL','ImagensCandidatos/18.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 02','PDT',12002,'DEPUTADO ESTADUAL','ImagensCandidatos/b2.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 03','PDT',12003,'DEPUTADO ESTADUAL','ImagensCandidatos/f16.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 04','PDT',12004,'DEPUTADO ESTADUAL','ImagensCandidatos/f22.png')");
		
		gravar("INSERT INTO candidato VALUES('DepEst 05','PT',13001,'DEPUTADO ESTADUAL','ImagensCandidatos/f5.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 06','PT',13002,'DEPUTADO ESTADUAL','ImagensCandidatos/a1amx.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 07','PT',13003,'DEPUTADO ESTADUAL','ImagensCandidatos/18.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 08','PT',13004,'DEPUTADO ESTADUAL','ImagensCandidatos/f16.png')");
		
		gravar("INSERT INTO candidato VALUES('DepEst 09','PMDB',15001,'DEPUTADO ESTADUAL','ImagensCandidatos/f16.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 10','PMDB',15002,'DEPUTADO ESTADUAL','ImagensCandidatos/b2.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 11','PMDB',15003,'DEPUTADO ESTADUAL','ImagensCandidatos/f22.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 12','PMDB',15004,'DEPUTADO ESTADUAL','ImagensCandidatos/f4.png')");
		
		gravar("INSERT INTO candidato VALUES('DepEst 13','PV',43001,'DEPUTADO ESTADUAL','ImagensCandidatos/f16.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 14','PV',43002,'DEPUTADO ESTADUAL','ImagensCandidatos/b2.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 15','PV',43003,'DEPUTADO ESTADUAL','ImagensCandidatos/f22.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 16','PV',43004,'DEPUTADO ESTADUAL','ImagensCandidatos/f4.png')");
		
		gravar("INSERT INTO candidato VALUES('DepEst 17','PSDB',45001,'DEPUTADO ESTADUAL','ImagensCandidatos/f16.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 18','PSDB',45002,'DEPUTADO ESTADUAL','ImagensCandidatos/b2.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 19','PSDB',45003,'DEPUTADO ESTADUAL','ImagensCandidatos/f22.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 20','PSDB',45004,'DEPUTADO ESTADUAL','ImagensCandidatos/f4.png')");
		
		gravar("INSERT INTO candidato VALUES('DepEst 21','PCdoB',65001,'DEPUTADO ESTADUAL','ImagensCandidatos/f16.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 22','PCdoB',65002,'DEPUTADO ESTADUAL','ImagensCandidatos/b2.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 23','PCdoB',65003,'DEPUTADO ESTADUAL','ImagensCandidatos/f22.png')");
		gravar("INSERT INTO candidato VALUES('DepEst 24','PCdoB',65004,'DEPUTADO ESTADUAL','ImagensCandidatos/f4.png')");
		
		fechaConexao();
	}


}
