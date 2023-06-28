package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;






public class Model {
	
	private Graph<Airport,DefaultWeightedEdge> grafo; 
	private ExtFlightDelaysDAO dao;
	private Map<Integer, Airport> idMap;
	
	public Model () {
		dao = new ExtFlightDelaysDAO();
		idMap = new HashMap<Integer,Airport>();
		dao.loadAllAirports(idMap);
	}
	
	public void creaGrafo(int x) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		List<Airport> listaAirport = new ArrayList<Airport>();
		listaAirport = dao.getVertici(x, idMap);
		
		Graphs.addAllVertices(grafo, listaAirport);
		
		
		for(Adiacenza a : this.dao.getAdiacenze(idMap)) {
			
			try {
			Graphs.addEdge(this.grafo, a.getA1(), 
					a.getA2(), a.getPeso());
			}catch (Throwable t) {
	            // Ignora eventuali errori durante l'aggiunta degli archi
	        }

		}
		System.out.println("# VERTICI: " + this.grafo.vertexSet().size());
		System.out.println("# ARCHI: " + this.grafo.edgeSet().size());
	}
	
	public List<Airport> getPercorso(Airport a1, Airport a2){
		List<Airport> percorso = new ArrayList<>();
		BreadthFirstIterator<Airport,DefaultWeightedEdge> it =
				new BreadthFirstIterator<>(this.grafo,a1);
		
		Boolean trovato = false;
		//visito il grafo
		while(it.hasNext()) {
			Airport visitato = it.next();
			
			if(visitato.equals(a2))
				trovato = true;
			
		}
		
		//ottengo il percorso
		if(trovato){
		percorso.add(a2);
		Airport step = it.getParent(a2);
		while(!step.equals(a1)) {
			percorso.add(0,step);
			step = it.getParent(step);
			
		}
		percorso.add(0,a1);
		return percorso;
		
		}else {
			return null;
		}
	
	}
	
	public Set<Airport> getVertici() {
		if(grafo != null)
			return grafo.vertexSet();
		
		return null;
	}

	public int getNVertici() {
		if(grafo != null)
			return grafo.vertexSet().size();
		
		return 0;
	}
	
	public int getNArchi() {
		if(grafo != null)
			return grafo.edgeSet().size();
		
		return 0;
	}
}



















