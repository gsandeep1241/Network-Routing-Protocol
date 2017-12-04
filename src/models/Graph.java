package models;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	List<List<Integer>> V;
	List<List<Integer>> W;
	int size;
	
	public Graph(int numberOfVertices) {
		this.size = numberOfVertices;
		this.V = new ArrayList<>();
		this.W = new ArrayList<>();
		for(int i=0; i <= numberOfVertices; i++) {
			this.V.add(new ArrayList<>());
			this.W.add(new ArrayList<>());
		}
	}
	
	public void addEdge(int u, int v, int w) {
		this.V.get(u).add(v);
		this.V.get(v).add(u);
		
		this.W.get(u).add(w);
		this.W.get(v).add(w);
	}
	
	public int size() {
		return this.size;
	}
	
	public VertexInfo getVertexInfo(int u) {
		VertexInfo vertexInfo = new VertexInfo(this.V.get(u), this.W.get(u));
		return vertexInfo;
	}
	
	public List<Edge> getAllEdges(){
		List<Edge> edges = new ArrayList<>();
		for(int i=1; i < V.size(); i++) {
			int u = i;
			for(int j=0; j < V.get(i).size(); j++) {
				int v = V.get(i).get(j);
				if(v > u) {
					int w = W.get(i).get(j);
					edges.add(new Edge(u,v,w));
				}
			}
		}
		return edges;
	}
}
