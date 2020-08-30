package it.polito.tdp.extflightdelays.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	
	private ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
	private Map<Integer,Airport> mappaAereoporti;
	private Graph<Integer,DefaultWeightedEdge> grafo;
	private Map<Integer,Integer> mappa;
	
	public Model() {
		this.mappaAereoporti= new HashMap<Integer,Airport>();
		this.mappa = new TreeMap<Integer,Integer>();
	}
	
	
	public void creaGrafo(int valore) {
		
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao.getAereoporti(mappa);
		this.dao.getAereoporti2(mappa);
		List<Integer> vertici = new LinkedList<Integer>();
		for(Integer i : this.mappa.keySet()) {
			if(this.mappa.get(i)>valore) {
				vertici.add(i) ;
			}
		}
		if(vertici!=null) {
		Graphs.addAllVertices(this.grafo, vertici);
		for(Arco arc : this.dao.getArchi()) {
			if(this.grafo.containsVertex(arc.getAa()) && this.grafo.containsVertex(arc.getAp())) {
				DefaultWeightedEdge edge = this.grafo.getEdge(arc.getAp(), arc.getAa()) ;
				if(edge==null) {
					Graphs.addEdgeWithVertices(this.grafo, arc.getAp(), arc.getAa(), arc.getPeso()) ;
				}
				else {
					double pv = this.grafo.getEdgeWeight(edge) ;
					this.grafo.setEdgeWeight(edge, (pv+arc.getPeso())/2);
				}
			}
		}
		System.out.println();
		}
	/*	else {
			System.out.println("Non è possibile creare il grafo!") ;
		} */
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public List<Airport> getAereoporti(){
		List<Airport> aereoportiFiltrati = new LinkedList<Airport>();
		for(Airport a: this.dao.loadAllAirports()) {
			if(this.mappa.containsKey(a.getId())) {
				aereoportiFiltrati.add(a);
			}
		}
		return aereoportiFiltrati;
	}
	
	public List<AirportWeight> getAdiacenti(Airport a){
		List<AirportWeight> l = new LinkedList<AirportWeight>();
		List<Integer> adia = new LinkedList<Integer>(Graphs.neighborListOf(this.grafo, a.getId()));
		for(Integer i : adia) {
			int peso = (int) this.grafo.getEdgeWeight(this.grafo.getEdge(a.getId(), i)) ;
			AirportWeight aw = new AirportWeight(i,peso);
			l.add(aw);
		}
		Collections.sort(l);
		return l;
	}
	


}
