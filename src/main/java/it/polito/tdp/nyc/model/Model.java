package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	List<City> vertices;
	SimpleWeightedGraph<City, DefaultWeightedEdge> graph;
	NYCDao dao;
	
	
	public Model() {
		vertices = new ArrayList<>();
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao = new NYCDao();
		
		
	}
	
	public void loadNodes(String provider) {
		if(vertices.isEmpty()) {
			this.vertices = dao.getCity(provider); 
		}
	}
	
	public void creaGrafo(String provider) {
		clearGraph();
		loadNodes(provider);
		
		Graphs.addAllVertices(graph, vertices);
		
		System.out.println(this.graph.vertexSet().size());
		
		calcolaArchi();
		
		System.out.println(this.graph.edgeSet().size());

	}
	
	private void clearGraph() {
		// TODO Auto-generated method stub
		vertices = new ArrayList<>();
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	}

	public void calcolaArchi() {
		
		for(City c: vertices) {
			LatLng pos1 = new LatLng(c.getLatitude(), c.getLongitude());
			for(City cc: vertices) {
				LatLng pos2 = new LatLng(cc.getLatitude(), cc.getLongitude());
				double dist = LatLngTool.distance(pos1, pos2, LengthUnit.KILOMETER);
				
				if(dist!=0)
					Graphs.addEdge(graph, c, cc, dist);
				
			}
		}
	}
	
	public List<String> getAdiacenti(String city) {
		City quartiere = null;
		List<String> output = new ArrayList<>();
		for(City c: vertices) {
			if(c.getCity().equals(city)) {
				quartiere = c;
			}
		}
		for(DefaultWeightedEdge e: graph.edgesOf(quartiere)) {
			output.add(Graphs.getOppositeVertex(graph, e, quartiere).getCity()+" "+graph.getEdgeWeight(e));
		}
		
		
		return output;
	}
	
	public int getVertici() {
		return this.graph.vertexSet().size();
	}
	public int getArchi() {
		return this.graph.edgeSet().size();
		
	}
	@SuppressWarnings("unchecked")
	public List<String> getCities(){
		List<String> output = new ArrayList<>();
		for(City c: vertices) {
			output.add(c.getCity());
		}
		return output;
	}
	public List<String> getAllProvider(){
		return this.dao.getAllProvider();
	}
}
