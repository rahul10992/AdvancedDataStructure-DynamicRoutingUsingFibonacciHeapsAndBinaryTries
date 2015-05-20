package triesStructure;

import java.util.ArrayList;

public class Node {
	
	int name;
	int data;
	int degree;
	Node parent;
	ArrayList<Node> children;
	Node left;
	Node right;
	boolean childCut;
	
	
	Node(int n, int d){
		this.name=n;
		this.data=d;
		degree=0;
		childCut=false;
		parent=null;
		children=new ArrayList<Node>();
	}
	Node(){
		degree=0;
		childCut=false;
		parent=null;
		children=new ArrayList<Node>();
	}
	
	void addChild(Node n){

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
