package models;

public class MaxHeapForNodes {
	private Node[] heapArray;
	private int[] helper;
	private int size;
	
	public MaxHeapForNodes(int capacity) {
		this.heapArray = new Node[capacity+1];
		this.helper = new int[capacity+1];
		
		this.size=0;
	}
	
	public Node getMax() {
		return heapArray[1];
	}
	
	public Node extractMax() {
		Node node = heapArray[1];
		int val = heapArray[1].getKey();
		int last = heapArray[size].getKey();
		
		swap(heapArray, 1, size);
		
		helper[val] = size;
		helper[last] = 1;
		size--;
		heapifyDown(1);
		
		return node;
	}
	
	public void insert(Node node) {
		int ind = size+1;
		heapArray[ind] = node;
		helper[node.getKey()] = ind;
		size++;
		
		heapifyUp(ind);
	}
	
	public Node delete(int val1) {
		int index = helper[val1];
		Node node1 = heapArray[index];
		int weight1 = node1.getValue();
		
		Node node2 = heapArray[size];
		int val2 = node2.getKey();
		int weight2 = node2.getValue();
		
		swap(heapArray, index, size);
		
		helper[val1] = size;
		helper[val2] = index;
		size--;
		
		if(weight2 > weight1) {
			heapifyUp(index);
		}else if(weight2 < weight1) {
			heapifyDown(index);
		}
		
		return node1;
	}
	
	private void heapifyUp(int index) {
		int parent = parent(index);
		while(parent != 0 && heapArray[parent].getValue() < heapArray[index].getValue()) {
			int val1 = heapArray[index].getKey();
			int val2 = heapArray[parent].getKey();
			
			swap(heapArray, index, parent);
			
			helper[val1] = parent;
			helper[val2] = index;
			
			index = parent;
			parent = parent(parent);
		}
	}
	
	private void heapifyDown(int index) {
		int leftChild = leftChild(index);
		int rightChild = rightChild(index);
		
		int max = heapArray[index].getValue();
		int next = index;
		if(leftChild <= size && heapArray[leftChild].getValue() > max) {
			max = heapArray[leftChild].getValue();
			next = leftChild;
		}
		if(rightChild <= size && heapArray[rightChild].getValue() > max) {
			max = heapArray[rightChild].getValue();
			next = rightChild;
		}
		
		if(next == index) {
			return;
		}else if(next == leftChild) {
			int val1 = heapArray[index].getKey();
			int val2 = heapArray[leftChild].getKey();
			
			swap(heapArray, index, leftChild);
			helper[val1] = leftChild;
			helper[val2] = index;
			
			heapifyDown(leftChild);
			
		}else {
			int val1 = heapArray[index].getKey();
			int val2 = heapArray[rightChild].getKey();
			
			swap(heapArray, index, rightChild);
			helper[val1] = rightChild;
			helper[val2] = index;
			
			heapifyDown(rightChild);
		}
	}
	
	private void swap(Node[] arr, int i, int j) {
		Node temp = arr[i];
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
