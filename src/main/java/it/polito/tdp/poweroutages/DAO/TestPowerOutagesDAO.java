package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.util.List;

import it.polito.tdp.poweroutages.model.PowerOutage;

public class TestPowerOutagesDAO {

	public static void main(String[] args) {
		
		try {
			Connection connection = ConnectDB.getConnection();
			connection.close();
			System.out.println("Connection Test PASSED");
			
			PowerOutageDAO dao = new PowerOutageDAO() ;
			
			// System.out.println(dao.getNercList()) ;
			/*for( PowerOutage po : dao.getPowerOutages()){
				System.out.println(po);
			}
			 * List<PowerOutage> po = dao.getPowerOutages("MAAC");
			System.out.print(po.get(0));

			 */
			
		} catch (Exception e) {
			System.err.println("Test FAILED");
		}

	}

}
