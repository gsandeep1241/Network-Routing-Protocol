package helpers;

import java.time.Duration;

public interface StatGenerator {
	
	public void generateEndStats(int[] DAD, int s, int t, String message, int[] bandwidth, Duration timeElapsed);
	
	public void generateGraphStats(String type, int vertices, int edges, int maxWeight);
	
	public void getAverageTime(Duration timeElapsed, int size);
	
	public void generateStartMessage(int s, int t);
}
