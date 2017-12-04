package models;

public class MaxHeapForEdges {
	private Edge[] heapArray;
	private int size;
	
	public MaxHeapForEdges(int capacity) {
		this.heapArray = new Edge[capacity+1];
		
		this.size=0;
	}
	
	public Edge getMax() {
		return heapArray[1];
	}
	
	public Edge extractMax() {
		Edge edge = heapArray[1];
		
		swap(heapArray, 1, size);
		
		size--;
		heapifyDown(1);
		
		return edge;
	}
	
	public void insert(Edge edge) {
		int ind = size+1;
		heapArray[ind] = edge;
		size++;
		
		heapifyUp(ind);
	}
	
	public boolean isEmpty() {
		return (size == 0);
	}
	
	
	private void heapifyUp(int index) {
		int parent = parent(index);
		while(parent != 0 && heapArray[parent].getW() < heapArray[index].getW()) {
			
			swap(heapArray, index, parent);
			
			index = parent;
			parent = parent(parent);
		}
	}
	
	private void heapifyDown(int index) {
		int leftChild = leftChild(index);
		int rightChild = rightChild(index);
		
		int max = heapArray[index].getW();
		int next = index;
		if(leftChild <= size && heapArray[leftChild].getW() > max) {
			max = heapArray[leftChild].getW();
			next = leftChild;
		}
		if(rightChild <= size && heapArray[rightChild].getW() > max) {
			max = heapArray[rightChild].getW();
			next = rightChild;
		}
		
		if(next == index) {
			return;
		}else {
			swap(heapArray, index, next);
			heapifyDown(next);
		}
	}
	
	private void swap(Edge[] arr, int i, int j) {
		Edge temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	private int leftChild(int ind) {
		return ind*2;
	}
	
	private int rightChild(int ind) {
		return ind*2+1;
	}
	
	private int parent(int ind) {
		return ind/2;
	}
}
