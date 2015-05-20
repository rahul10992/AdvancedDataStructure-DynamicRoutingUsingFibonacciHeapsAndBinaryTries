package triesStructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class routing {
	static ArrayList<Integer> result=new ArrayList<Integer>();
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String path1=new String(args[0]);
		String path2=new String(args[1]);
		int src=Integer.parseInt(args[2]);
		int dest=Integer.parseInt(args[3]);
		
		System.out.println("The input file is in "+path1+" \nand path "+path2+"\nwith a solution wanted from\nnode "+src+" to node "+dest);
		
		BufferedReader br=new BufferedReader(new FileReader(path1));
		
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
		
		input=null;
		int marker=0;
		br=new BufferedReader(new FileReader(path2));
		input=br.readLine();
		
		while(input!=null){
			//System.out.println(input);
			if(!input.equals("")){
				//System.out.println(marker);
				vertices[marker].ipaddr=input;
				
				String[] partialIp=input.split("\\.");
				
				for(int i=0;i<4;i++){
					Integer tempInt;
					tempInt=(Integer.parseInt(partialIp[i]));
					partialIp[i]=Integer.toBinaryString(tempInt);
		
					if(partialIp[i].length()<8){
						int temp=8-partialIp[i].length();
						for(int j=0;j<temp;j++){
							partialIp[i]="0"+partialIp[i];
						}
					}
				}
				for(int i=0;i<4;i++){
					vertices[marker].binIpaddr+=partialIp[i];
				}
				marker++;
			}
			input=br.readLine();
		}
		br.close();
		System.out.println("exiting while");
		
		//Vertex start=vertices[src];
		letsDijkstra(src, dest, vertices);
		
		//ArrayList<Integer> result=new ArrayList<Integer>();
		//result.add(dest);
		System.out.println("first results received");
		int temp=vertices[dest].previousNodeIndex;
		//System.out.println(dest+" data "+vertices[dest].data);
		//result.add(temp);
		//while(temp!=src){
			//System.out.println(temp+" data "+vertices[temp].data);
		//	result.add(temp);
		//	temp=vertices[temp].previousNodeIndex;
		//}
		//System.out.println(src);
		//result.add(src);
		ArrayList<Integer> firstResult=result;
		System.out.println("printing the arrayList");
		for(int i=0;i<firstResult.size()-1;i++){
			System.out.println(firstResult.get(i));
		}
		
		for(int i=result.size()-1;i>0;i--){
			
			reInitialize(vertices);
			Vertex iter=vertices[result.get(i)];
			int x=iter.name;
			letsDijkstra(x, dest, vertices);
			for(int j=0;j<vertices.length;j++){
				
				if(vertices[i]!=iter){
					String ipStr=new String();
					ipStr=vertices[i].ipaddr;
					Vertex temp1=new Vertex();
					temp1=vertices[i];
					while(vertices[temp1.previousNodeIndex].previousNodeIndex!=-1){
						temp1=vertices[temp1.previousNodeIndex];
					}
					iter.router.insert(ipStr, temp1.name);
					
				}
			}
			iter.router.compressTrie(iter.router.root);
			System.out.print(iter.router.traverse(vertices[dest].binIpaddr)+" ");
			
		}
	}
	
	public static void reInitialize(Vertex[] vertices){
		
		for(int i=0;i<vertices.length;i++){
			vertices[i].data=32608;
			vertices[i].visited=false;
			vertices[i].previousNodeIndex=-1;
		}
	}
	
	public static void letsDijkstra(int src, int dest, Vertex[] vertices){
		//System.out.println("entering dijkstra");
		FibonacciHeap fb=new FibonacciHeap();
		HashMap<Integer,Node> nodeRepository=new HashMap<Integer, Node>();
		//vertices names have been set. only data fields should change now.
		int prevNode=src;
		Node source=new Node(src, 0);
		vertices[src].data=0;
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
		//System.out.println(dest+" data "+vertices[dest].data);
		result.add(src);
		while(temp!=src){
			//System.out.println(temp+" data "+vertices[temp].data);
			result.add(temp);
			temp=vertices[temp].previousNodeIndex;
			
		}
		//System.out.println(src);
		result.add(src);
		//System.out.println(vertices[dest].data);
		}

}
