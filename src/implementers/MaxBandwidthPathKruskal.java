package implementers;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import helpers.StatGenerator;
import helpers.StatGeneratorImpl;
import static helpers.Constants.*;
import models.Edge;
import models.Graph;
import models.MaxHeapForEdges;
import models.VertexInfo;

public class MaxBandwidthPathKruskal {

	public static void execute(Graph graph, List<Integer> source, List<Integer> destination) {
		
		if(source.size() != destination.size()) {
			return;
		}
		int total = source.size();
		
		StatGenerator statGenerator = new StatGeneratorImpl();
		Instant startAverage = Instant.now();
		int vertices = graph.size();
		List<Edge> edges = graph.getAllEdges();
		int size = edges.size();
		
		MaxHeapForEdges maxHeap = new MaxHeapForEdges(size);
		for(int i=0; i < size; i++) {
			maxHeap.insert(edges.get(i));
		}
		
		Graph MST = new Graph(vertices);
		
		int[] parent = new int[vertices+1];
		int[] rank = new int[vertices+1];
		for(int i=0; i < size; i++) {
			
			Edge e = maxHeap.extractMax();
			
			int u = e.getU();
			int v = e.getV();
			int w = e.getW();
			
			if(!isCycle(u, v, parent)) {
				MST.addEdge(u, v, w);
				union(u, v, parent, rank);
			}
		}
		
		for(int i=0; i < total; i++) {
			int s = source.get(i);
			int t = destination.get(i);

			statGenerator.generateStartMessage(s, t);
			int[] DAD = new int[vertices+1];
			int[] bandwidth = new int[vertices+1];
			bandwidth[s] = Integer.MAX_VALUE;
			boolean[] visited = new boolean[vertices+1];
			generateMaxBWPath(MST, DAD, s, t, bandwidth, visited);
			
			String message = MAX_BANDWIDTH_VALUE_KRUSKAL;
			statGenerator.generateEndStats(DAD, s, t, message, bandwidth, null);
		}
		Instant endAverage = Instant.now();
		Duration timeElapsed = Duration.between(startAverage, endAverage);
		statGenerator.getAverageTime(timeElapsed, total);
	}
	
	private static boolean generateMaxBWPath(Graph MST, int[] DAD, int s, int t, int[] bandwidth, boolean[] visited) {
		visited[s] = true;
		if(s == t) {
			return true;
		}
		VertexInfo vertexInfo = MST.getVertexInfo(s);
		List<Integer> edges = vertexInfo.getVertices();
		List<Integer> weights = vertexInfo.getWeights();
		
		for(int i=0; i < edges.size(); i++) {
			int v = edges.get(i);
			if(!visited[v]) {
				bandwidth[v] = (int)Math.min(bandwidth[s], weights.get(i));
				DAD[v] = s;
				if(generateMaxBWPath(MST, DAD, v, t, bandwidth, visited)) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	private static boolean isCycle(int u, int v, int[] parent) {
		int parentU = find(u, parent);
		int parentV = find(v, parent);
		
		if(parentU == parentV) {
			return true;
		}else {
			return false;
		}
	}
	
	private static int find(int u, int[] parent) {
		if(parent[u] == 0) {
			return u;
		}
		int par = find(parent[u], parent);
		parent[u] = par;
		
		return par;
		
	}
	
	private static void union(int u, int v, int[] parent, int[] rank) {
		int parentU = find(u, parent);
		int parentV = find(v, parent);
		
		if(rank[parentU] < rank[parentV]) {
			parent[parentU] = parentV;
		}else if(rank[parentU] > rank[parentV]) {
			parent[parentV] = parentU;
		}else {
			parent[parentU] = parentV;
			rank[parentV]++;
		}
	}
}
