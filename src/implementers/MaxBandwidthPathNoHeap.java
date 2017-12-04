package implementers;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import helpers.StatGenerator;
import helpers.StatGeneratorImpl;
import static helpers.Constants.*;
import models.Graph;
import models.VertexInfo;

public class MaxBandwidthPathNoHeap {
	
	static class CountWrapper {
		int count;
	}

	public static void execute(Graph graph, int s, int t) {
		
		StatGenerator statGenerator = new StatGeneratorImpl();
		Instant start = Instant.now();
		int size = graph.size();
		int[] status = new int[size+1];
		int[] bandwidth = new int[size+1];
		int[] DAD = new int[size+1];
		
		initialize(graph, status, bandwidth, DAD, size, s);
		
		while(status[t] != 1) {
			int v = getVertexWithLargestBW(bandwidth, status, size);
			status[v] = 1;
			
			VertexInfo vertexInfo = graph.getVertexInfo(v);
			List<Integer> edges = vertexInfo.getVertices();
			List<Integer> weights = vertexInfo.getWeights();
			
			for(int i=0; i < edges.size(); i++) {
				int w = edges.get(i);
				int weight = weights.get(i);
				
				if(status[w] == -1) {
					status[w] = 0;
					bandwidth[w] = (int)Math.min(bandwidth[v], weight);
					DAD[w] = v;
				}else if(status[w] == 0) {
					if(bandwidth[w] < (int)Math.min(bandwidth[v], weight)) {
						bandwidth[w] = (int)Math.min(bandwidth[v], weight);
						DAD[w] = v;
					}
				}
			}
		}
		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);
		String message = MAX_BANDWIDTH_VALUE_NO_HEAP;
		statGenerator.generateEndStats(DAD, s, t, message, bandwidth, timeElapsed);
	}
	
	private static int getVertexWithLargestBW(int[] bandwidth, int[] status, int size) {
		int maxVal = bandwidth[0];
		int maxInd = 0;
		for(int i=1; i < size; i++) {
			if(status[i] == 0 && bandwidth[i] > maxVal) {
				maxVal = bandwidth[i];
				maxInd = i;
			}
		}
		return maxInd;
	}
	
	private static void initialize(Graph graph, int[] status, int[] bandwidth, int[] DAD, int size, int s) {
		Arrays.fill(status, -1);
		Arrays.fill(bandwidth, 0);
		Arrays.fill(DAD, 0);
		
		status[s] = 1;
		VertexInfo info = graph.getVertexInfo(s);
		
		List<Integer> edges = info.getVertices();
		List<Integer> weights = info.getWeights();
		
		for(int i=0; i < edges.size(); i++) {
			int v = edges.get(i);
			int weight = weights.get(i);
			
			status[v] = 0;
			bandwidth[v] = weight;
			DAD[v] = s;
		}
	}
}
