package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println(model.getNercList());
		
		
		
		
		
		/*
		 * List<PowerOutage> lista = model.getPowerOutagesList("MAAC");
		PowerOutage po = lista.get(0);
		System.out.println(po);
		LocalDateTime p = po.getData_inizio().toLocalDateTime();
		LocalDateTime pp = po.getData_fine().toLocalDateTime();
		System.out.println(p);
		System.out.println(p.until(pp, ChronoUnit.MINUTES) + " " + p.getYear());
		System.out.println(po.getData_fine().toString());
		
		}
		
		System.out.println("\n");
		PowerOutageDAO dao = new PowerOutageDAO();
		List<PowerOutage> lista = dao.getPowerOutages("MAIN");
		for(PowerOutage po: lista) {
			System.out.println(po);
		}
		
			List<PowerOutage> prova = model.trovaEventi("HECO", 4 , 200);
		for( PowerOutage po : prova) {
			System.out.println(po);
		}
		 */
		
		
		
	
	}

}
