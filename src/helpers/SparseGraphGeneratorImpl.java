package helpers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import static helpers.Constants.*;
import models.Graph;

public class SparseGraphGeneratorImpl implements SparseGraphGenerator{

	@Override
	public Graph generate() {
		
		StatGenerator statGenerator = new StatGeneratorImpl();
		
		int vertices = NUMBER_OF_VERTICES;
		int edges = vertices*(SPARSE_GRAPH_DEGREE/2);
		Random random = new Random();
		
		Graph sparseGraph = new Graph(vertices);
		
		int maxWeight = MAX_WEIGHT;
		HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
		makeGraphConnected(sparseGraph, vertices, map, maxWeight);
		
		int rem = edges-vertices;
		
		while(rem > 0) {
			int u = random.nextInt(vertices)+1;
			int v = random.nextInt(vertices)+1;
			int w = random.nextInt(maxWeight)+1;
			
			if(u == v || map.get(u).contains(v) || map.get(v).contains(u)) {
				continue;
			}else {
				sparseGraph.addEdge(u, v, w);
				map.get(u).add(v);
				map.get(v).add(u);
				rem--;
			}
		}
		
		statGenerator.generateGraphStats(GRAPH_TYPE_SPARSE, vertices, edges, maxWeight);
		return sparseGraph;
	}
	
	private void makeGraphConnected(Graph g, int vertices, HashMap<Integer, HashSet<Integer>> map, int maxWeight) {
		
		int[] helperArray = new int[vertices];
		for(int i=0; i < vertices; i++) {
			helperArray[i] = i+1;
		}
		
		Random random = new Random();
		Collections.shuffle(Arrays.asList(helperArray));
		
		for(int i=0; i < vertices; i++) {
			int u = helperArray[i];
			int v = helperArray[(i+1)%vertices];
			int w = random.nextInt(maxWeight)+1;
			g.addEdge(u,v,w);
			
			if(map.get(u) == null) {
				HashSet<Integer> set = new HashSet<>();
				set.add(v);
				map.put(u, set);
			}else {
				map.get(u).add(v);
			}
			
			if(map.get(v) == null) {
				HashSet<Integer> set = new HashSet<>();
				set.add(u);
				map.put(v, set);
			}else {
				map.get(v).add(u);
			}
		}
	}
}
