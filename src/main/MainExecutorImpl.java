package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import helpers.DenseGraphGenerator;
import helpers.DenseGraphGeneratorImpl;
import helpers.SparseGraphGenerator;
import helpers.SparseGraphGeneratorImpl;
import helpers.StatGenerator;
import helpers.StatGeneratorImpl;

import static helpers.Constants.*;
import implementers.MaxBandwidthPathKruskal;
import implementers.MaxBandwidthPathNoHeap;
import implementers.MaxBandwidthPathWithHeap;
import models.Graph;

public class MainExecutorImpl implements MainExecutor{

	@Override
	public void execute() {
		
		Random random = new Random();
		StatGenerator statGenerator = new StatGeneratorImpl();
		
		for(int i=0; i < NUMBER_OF_ITERATIONS; i++) {
			SparseGraphGenerator generator = new SparseGraphGeneratorImpl();
			Graph sparseGraph = generator.generate();
			
			List<Integer> startVertices = new ArrayList<>();
			List<Integer> endVertices = new ArrayList<>();
			
			for(int j=0; j < NUMBER_OF_TEST_CASES; j++) {
				int s = random.nextInt(5000)+1;
				int t = random.nextInt(5000)+1;
				
				statGenerator.generateStartMessage(s, t);
				
				MaxBandwidthPathNoHeap.execute(sparseGraph, s, t);
				
				MaxBandwidthPathWithHeap.execute(sparseGraph, s, t);
				
				startVertices.add(s); endVertices.add(t);	
				
				System.out.println();
			}

			MaxBandwidthPathKruskal.execute(sparseGraph, startVertices, endVertices);
		}
		
		for(int i=0; i < NUMBER_OF_ITERATIONS; i++) {
			DenseGraphGenerator generator = new DenseGraphGeneratorImpl();
			Graph denseGraph = generator.generate();

			List<Integer> startVertices = new ArrayList<>();
			List<Integer> endVertices = new ArrayList<>();
			
			for(int j=0; j < NUMBER_OF_TEST_CASES; j++) {
				int s = random.nextInt(5000)+1;
				int t = random.nextInt(5000)+1;
				
				statGenerator.generateStartMessage(s, t);

				MaxBandwidthPathNoHeap.execute(denseGraph, s, t);
				
				MaxBandwidthPathWithHeap.execute(denseGraph, s, t);
				
				startVertices.add(s); endVertices.add(t);	
				
			}

			MaxBandwidthPathKruskal.execute(denseGraph, startVertices, endVertices);	
			System.out.println();
		}
	}
}
