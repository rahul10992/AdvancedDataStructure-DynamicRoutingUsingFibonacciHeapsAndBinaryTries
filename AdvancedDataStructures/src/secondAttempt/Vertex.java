package secondAttempt;

import java.util.ArrayList;
import java.util.HashMap;

public class Vertex {
	
	public int data;
	public int name;
	public boolean visited;
	public int previousNodeIndex;
	//adjacency list will be with the node's identity.
	public ArrayList<Integer> adjacencyList=new ArrayList<Integer>();
	//the hashmap will store the node's identity and the corresponding weight.
	public HashMap<Integer, Integer> mapWeights=new HashMap<Integer, Integer>();
	
	public Vertex(){
		visited=false;
	}
	
	/*public void addInAdj(int v1,int wt){
		adjacencyList.add(v1);
		mapWeights.put(v1, wt);
	}*/

}
