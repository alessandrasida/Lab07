package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}
			
			st.close();
			res.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	
	public List<PowerOutage> getPowerOutages(Nerc codiceNerc){
		
		List<PowerOutage> powerOutList = new LinkedList<>();
		
		
		String sql = "SELECT id, nerc_id, customers_affected, date_event_began, date_event_finished "
				+ "FROM poweroutages "
				+ "WHERE nerc_id = ? "
				+ "ORDER BY date_event_began ASC ";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, codiceNerc.getId());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				LocalDateTime inizio = rs.getTimestamp("date_event_began").toLocalDateTime();
				LocalDateTime fine = rs.getTimestamp("date_event_finished").toLocalDateTime();
				int durataMalf = (int) inizio.until(fine , ChronoUnit.MINUTES);
				powerOutList.add(new PowerOutage( rs.getInt("id"), codiceNerc , rs.getInt("customers_affected"), inizio,
						fine, durataMalf ));
			}
			
			st.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
		
		return powerOutList;
	}

}
