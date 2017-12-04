package models;

import java.util.List;

public class VertexInfo {

	List<Integer> v;
	List<Integer> w;
	
	public VertexInfo(List<Integer> v, List<Integer> w) {
		this.v = v;
		this.w = w;
	}
	
	public List<Integer> getVertices(){
		return this.v;
	}
	
	public List<Integer> getWeights(){
		return this.w;
	}
}
