package triesStructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Dijkstras {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//FibonacciHeap fb=new FibonacciHeap();
		String path=args[0];
		int src=Integer.parseInt(args[1]);
		int dest=Integer.parseInt(args[2]);
		
		BufferedReader br=new BufferedReader(new FileReader(path));
		System.out.println("The input file is in "+path+"\nwith a solution wanted from\nnode "+src+" to node "+dest);
		
		String input=new String();
		input=br.readLine();
		if(input==""){
			System.out.println("the first line doesn't have enough information. Exiting");
			System.exit(1);
		}
		String[] stats=input.split(" ");
		int numVertices=Integer.parseInt(stats[0]);
		int numEdges=Integer.parseInt(stats[1]);
		Vertex[] vertices=new Vertex[numVertices];
		System.out.println("Number of vertices: "+numVertices+" number of edges: "+numEdges);
		
		input=br.readLine();
		for(int i=0;i<numVertices;i++){
			vertices[i]=new Vertex();
			vertices[i].name=i;
		}
		//adjacency lists populated!!
		while(input!=null){
			if(!input.equals("")){
				String[] ip=input.split(" ");
				int v1=Integer.parseInt(ip[0]);
				int v2=Integer.parseInt(ip[1]);
				int wt=Integer.parseInt(ip[2]);
				
				//System.out.println("v1: "+v1+" v2: "+v2+" wt: "+wt);
				vertices[v1].adjacencyList.add(v2);
				vertices[v1].mapWeights.put(v2, wt);
				vertices[v2].adjacencyList.add(v1);
				vertices[v2].mapWeights.put(v1, wt);
				
			
			}
			input=br.readLine();
		}
		br.close();
		
		letsDijkstra(src, dest, vertices);
		
			
	}
	
	public static void letsDijkstra(int src, int dest, Vertex[] vertices){
		//System.out.println("entering dijkstra");
		FibonacciHeap fb=new FibonacciHeap();
		HashMap<Integer,Node> nodeRepository=new HashMap<Integer, Node>();
		//vertices names have been set. only data fields should change now.
		int prevNode=src;
		Node source=new Node(src, 0);
		vertices[src].data=0;
		System.out.println(fb.removeMin());
		fb.insert(source);
		nodeRepository.put(src, source);
		
		for(int i=0;i<vertices[src].adjacencyList.size();i++){
			
			Node temp=new Node();
			temp.name=vertices[src].adjacencyList.get(i);
			temp.data=vertices[src].mapWeights.get(temp.name);
			vertices[temp.name].data=temp.data;//updating the vertex data
			vertices[temp.name].previousNodeIndex=prevNode;
			fb.insert(temp);
			nodeRepository.put(temp.name, temp);
		}
		//fb.printTopLevelDll();
		Node x=fb.removeMin();
		nodeRepository.remove(x.name);
		System.out.println("deleted node "+x.name+" with value "+x.data);
		vertices[src].visited=true;//every removed node has its vertex set to visited.
		prevNode=fb.min.name;
		//fb.printTopLevelDll();
		
		
		/**As long as fb has nodes, take the min one. add its adjacency list to the heap.
		 * if the node has been visited, then chuck it.
		 * else, check if it is already in the heap (check in node repository) if it is there, 
		 * do a decreaseKey
		 * else just enter it in the fb heap.
		 * update the vertex data along with this.
		 */
		while(fb.noNodes>0){
			Node min=fb.min;
			if(min==null){
				System.out.println("min is null. Should not have reached here");
				System.exit(1);
			}
			
			Vertex vtxMin=vertices[min.name];
			for(int i=0;i<vtxMin.adjacencyList.size();i++){
				
				Node temp=new Node();
				int tempName=vtxMin.adjacencyList.get(i);
				temp.name=tempName;
				temp.data=vtxMin.mapWeights.get(tempName)+vtxMin.data;
				
				if(vertices[temp.name].visited==false){//check if the node has been visited.
					if(nodeRepository.containsKey(temp.name)){//check if the node is already present.
						Node existing=nodeRepository.get(temp.name);
						if(existing.data>temp.data){
							//do a decrease key
							prevNode=fb.min.name;
							vertices[temp.name].data=temp.data;//update the vertex value.
							vertices[temp.name].previousNodeIndex=prevNode;
							fb.decreaseKey(existing, temp.data);
						}
						else{
							//chuck it. the decrease key isn't required.
						}
					}
					else{//node hasn't been visited and it isn't in the repository either.
						// so we enter it into the node repository
						nodeRepository.put(temp.name, temp);
						fb.insert(temp);
						vertices[temp.name].data=temp.data;
						vertices[temp.name].previousNodeIndex=prevNode;
					}
				}
				else{
					//chuck. the node has been visited. doesnt matter.
				}
			}
			
			
			x=fb.removeMin();
			vertices[x.name].visited=true;
			//vertices[x.name].previousNodeIndex=prevNode;
			//prevNode=x.name;
			nodeRepository.remove(x.name);
			
			//System.out.println("deleted node "+x.name+" with value "+x.data);
			
		}
		
		int temp=vertices[dest].previousNodeIndex;
		System.out.println(dest+" data "+vertices[dest].data);
		while(temp!=src){
			System.out.println(temp+" data "+vertices[temp].data);
			
			temp=vertices[temp].previousNodeIndex;
		}
		System.out.println(src);
		System.out.println(vertices[dest].data);
		}

}
