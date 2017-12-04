package helpers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import static helpers.Constants.*;
import models.Graph;

public class DenseGraphGeneratorImpl implements DenseGraphGenerator{

	@Override
	public Graph generate() {
		
		StatGenerator statGenerator = new StatGeneratorImpl();
		int vertices = NUMBER_OF_VERTICES;
		Random random = new Random();
		int maxWeight = MAX_WEIGHT;
		
		Graph denseGraph = new Graph(vertices);
		HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
		makeGraphConnected(denseGraph, vertices, map, maxWeight);
		int edges = 0;
		
		
		for(int i=1; i <=vertices; i++) {
			int definedProbability = random.nextInt(5)+8;
			//this keeps the probability between 8 and 12 for adding the edges in one direction.
			//when the edge is looked at in the other direction, the probability essentially is doubled
			//thus the average adjacency lies approximately between 16% to 24% of total vertices
			for(int j=1; j <= vertices; j++) {
				if(i == j) {
					continue;
				}
				int chance = random.nextInt(100)+1;
				if(chance <= definedProbability && !map.get(i).contains(j) && !map.get(j).contains(i)) {
					int w = random.nextInt(maxWeight)+1;
					denseGraph.addEdge(i, j, w);
					map.get(i).add(j);
					map.get(j).add(i);
					edges++;
				}
			}
		}
		
		
		statGenerator.generateGraphStats(GRAPH_TYPE_DENSE, vertices, edges, maxWeight);
		return denseGraph;
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
