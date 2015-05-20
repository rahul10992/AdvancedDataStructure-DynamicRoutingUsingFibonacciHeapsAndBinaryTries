package triesStructure;

import java.util.ArrayList;
import java.util.HashMap;

public class Vertex {
	
	public int data;
	public int name;
	public boolean visited;
	public int previousNodeIndex;
	public String ipaddr="";
	public String binIpaddr="";
	//adjacency list will be with the node's identity.
	ArrayList<Integer> adjacencyList=new ArrayList<Integer>();
	//the hashmap will store the node's identity and the corresponding weight.
	HashMap<Integer, Integer> mapWeights=new HashMap<Integer, Integer>();
	BinaryTries router=new BinaryTries();
	
	public Vertex(){
		visited=false;
		data=32608;
		previousNodeIndex=-1;
		router=new BinaryTries();
	}
	
	/*public void addInAdj(int v1,int wt){
		adjacencyList.add(v1);
		mapWeights.put(v1, wt);
	}*/

}
