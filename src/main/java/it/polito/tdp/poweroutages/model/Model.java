package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	private List<PowerOutage> best;
	private List<PowerOutage> listaOutages;
	private int minuti;
	private int anni;
	private int maxAffectedPeople;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> getPowerOutagesList(Nerc nerc){
		return podao.getPowerOutages(nerc);
	}
	
	
	public List<PowerOutage> trovaEventi(Nerc nerc, int anni, int ore){
		//inizializzazioni
		List<PowerOutage> parziale = new ArrayList<>();
		this.anni = anni;
		this.maxAffectedPeople = 0;
		this.minuti = ore*60;
		
		PowerOutageDAO dao = new PowerOutageDAO();
		this.listaOutages = dao.getPowerOutages(nerc);
		
		//inizio ricorsione
		cerca(parziale);
		
		return best;
	}

	
	public void cerca( List<PowerOutage> parziale){
		
		//condizione di terminazione>> se restante arriva a 1 solo oggetto
		if( maxAffected(parziale) > this.maxAffectedPeople) {
			this.maxAffectedPeople = maxAffected(parziale);
			this.best = new ArrayList<>(parziale);
		}
		
		for( PowerOutage p : this.listaOutages) {
			if( !parziale.contains(p)) {
				parziale.add(p);
				//controllo le condizioni
				if( rispettaAnni(parziale) && rispettaOreMax(parziale)) {
					this.cerca(parziale);
				}
				//backtracking 
				parziale.remove(p);
				
			}
			
			
			
		}
		
		
	}
	
	private boolean rispettaAnni(List<PowerOutage> parziale) {
		if( parziale.size() >= 2) {
			LocalDateTime primo = parziale.get(0).getData_inizio();
			LocalDateTime ultimo = parziale.get(parziale.size()-1).getData_fine();
			if( (ultimo.getYear() - primo.getYear()) > this.anni){
				return false;
			}
		}
		return true;
	}

	private boolean rispettaOreMax(List<PowerOutage> parziale) {
		int totaleMinuti =0;
		
		for(PowerOutage po : parziale) {
			totaleMinuti+= po.getMinutiOutage();
		}
		if( totaleMinuti <= this.minuti)
			return true;
		return false;
	}

	//ritorna la quantita di persone affette da un power outage
	public int maxAffected(List<PowerOutage> parziale) {
		int affected = 0;
		for( PowerOutage p : parziale) {
			affected += p.getCostumers_affected();
		}
		return affected;
	}

	public int  totOre( List<PowerOutage> lista	) {
		double ore = 0;
		
		for(PowerOutage p : lista) {
			ore += p.getMinutiOutage();
		}
		
		ore = ore / 60;
		return (int) ore;
	}


	
	
}
