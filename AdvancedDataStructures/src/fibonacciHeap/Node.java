package fibonacciHeap;

import java.util.ArrayList;

public class Node {
	
	public int name;
	public int data;
	public int degree;
	public Node parent;
	public ArrayList<Node> children;
	public Node left;
	public Node right;
	public boolean childCut;
	
	
	public Node(int n, int d){
		this.name=n;
		this.data=d;
		degree=0;
		childCut=false;
		parent=null;
		children=new ArrayList<Node>();
	}
	public Node(){
		degree=0;
		childCut=false;
		parent=null;
		children=new ArrayList<Node>();
	}
	
	public void addChild(Node n){

		children.add(n);
		int listSize=children.size();
		
		if(listSize==1){
			n.left=n;
			n.right=n;
		}
		else {
			Node temp=children.get(listSize-2);
			temp.right=n;
			n.left=temp;
			temp=children.get(0);
			temp.left=n;
			n.right=temp;
		}
		n.parent=this;
		n.parent.degree=listSize;
	}
	
	public void resetChildCut(){
		this.childCut=false;
	}
}
