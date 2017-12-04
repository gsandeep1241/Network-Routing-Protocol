package helpers;

import java.time.Duration;

public class StatGeneratorImpl implements StatGenerator{

	private void printPath(int[] DAD, int s, int t) {
		if(t == s) {
			System.out.print(t); return;
		}
		
		printPath(DAD, s, DAD[t]);
		System.out.print(" -> " + t);
	}

	@Override
	public void generateEndStats(int[] DAD, int s, int t, String message, int[] bandwidth, Duration timeElapsed) {
		System.out.println(message + ": " + bandwidth[t]);
		this.printPath(DAD, s, t);
		System.out.println();

		//System.out.println("********************************************************************");
		if(timeElapsed != null) {
			System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
			System.out.println();
			System.out.println("********************************************************************");
		}else {
			System.out.println("--------------------------");
		}
	}

	@Override
	public void generateGraphStats(String type, int vertices, int edges, int maxWeight) {
		//System.out.println("Graph Statistics: ");
		System.out.println("This is a " + type + " graph.");
		
		System.out.println("Number of vertices: " + vertices);
		System.out.println("Number of edges: " + edges);
		System.out.println("Edges chosen to be less than or equal to " + maxWeight + " units.");
		
		System.out.println();
		
	}

	@Override
	public void getAverageTime(Duration timeElapsed, int size) {
		System.out.println("Average Time taken for " + size + " instances: "+ (int)timeElapsed.toMillis()/size +" milliseconds");
		System.out.println("********************************************************************");
	}

	@Override
	public void generateStartMessage(int s, int t) {
		System.out.println("Finding the shortest path between s= " + s + ", t= " + t);
		
	}

}
