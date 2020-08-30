package it.polito.tdp.extflightdelays.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.extflightdelays.model.Airline;
import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Arco;
import it.polito.tdp.extflightdelays.model.Flight;

public class ExtFlightDelaysDAO {

	public List<Airline> loadAllAirlines() {
		String sql = "SELECT * from airlines";
		List<Airline> result = new ArrayList<Airline>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Airline(rs.getInt("ID"), rs.getString("IATA_CODE"), rs.getString("AIRLINE")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Airport> loadAllAirports() {
		String sql = "select * from airports order by airports.`AIRPORT` ASC";
		List<Airport> result = new ArrayList<Airport>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Airport airport = new Airport(rs.getInt("ID"), rs.getString("IATA_CODE"), rs.getString("AIRPORT"),
						rs.getString("CITY"), rs.getString("STATE"), rs.getString("COUNTRY"), rs.getDouble("LATITUDE"),
						rs.getDouble("LONGITUDE"), rs.getDouble("TIMEZONE_OFFSET"));
				result.add(airport);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Flight> loadAllFlights() {
		String sql = "SELECT * FROM flights";
		List<Flight> result = new LinkedList<Flight>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Flight flight = new Flight(rs.getInt("ID"), rs.getInt("AIRLINE_ID"), rs.getInt("FLIGHT_NUMBER"),
						rs.getString("TAIL_NUMBER"), rs.getInt("ORIGIN_AIRPORT_ID"),
						rs.getInt("DESTINATION_AIRPORT_ID"),
						rs.getTimestamp("SCHEDULED_DEPARTURE_DATE").toLocalDateTime(), rs.getDouble("DEPARTURE_DELAY"),
						rs.getDouble("ELAPSED_TIME"), rs.getInt("DISTANCE"),
						rs.getTimestamp("ARRIVAL_DATE").toLocalDateTime(), rs.getDouble("ARRIVAL_DELAY"));
				result.add(flight);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
		public void getAereoporti(Map<Integer,Integer> m) {
			String sql = "select distinct f.`DESTINATION_AIRPORT_ID` as id , count(f.`ID`) as cnt " + 
					"from flights as f " + 
					"group by f.`DESTINATION_AIRPORT_ID`" ;
			try {
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				ResultSet rs = st.executeQuery();
	
				while (rs.next()) {
					if(!m.containsKey(rs.getInt("id"))) {
						m.put(rs.getInt("id"), rs.getInt("cnt")) ;
					}
					else {
						int valore = m.get(rs.getInt("id"));
						m.put(rs.getInt("id"), valore+rs.getInt("cnt"));
					
				}
			
				}
			}	
			catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Errore connessione al database");
				throw new RuntimeException("Error Connection Database");
			}
		
		
	}
		public void getAereoporti2(Map<Integer,Integer> m) {
			String sql = "select distinct f.`ORIGIN_AIRPORT_ID` as id, count(f.`ID`) as cnt " + 
					"from flights as f " + 
					"group by f.`ORIGIN_AIRPORT_ID`" ;
			try {
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				ResultSet rs = st.executeQuery();
	
				while (rs.next()) {
					if(!m.containsKey(rs.getInt("id"))) {
						m.put(rs.getInt("id"), rs.getInt("cnt")) ;
					}
					else {
						int valore = m.get(rs.getInt("id"));
						m.put(rs.getInt("id"), valore+rs.getInt("cnt"));
					
				}
			
				}
			}	
			catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Errore connessione al database");
				throw new RuntimeException("Error Connection Database");
			}
		
		
	}
		
		public List<Arco> getArchi(){
			String sql = "select f.`ORIGIN_AIRPORT_ID` as ap, f.`DESTINATION_AIRPORT_ID` as aa , avg(f.`ELAPSED_TIME`) as avg " + 
					"from flights as f " + 
					"group by f.`ORIGIN_AIRPORT_ID`, f.`DESTINATION_AIRPORT_ID`" ;
			
			List<Arco> archi = new LinkedList<Arco>();
			
			try {
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				ResultSet rs = st.executeQuery();

				while (rs.next()) {
					Arco arco = new Arco(rs.getInt("ap"), rs.getInt("aa"), rs.getDouble("avg")) ;
					archi.add(arco);
				}

				conn.close();
				return archi;

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Errore connessione al database");
				throw new RuntimeException("Error Connection Database");
			}
		}
}