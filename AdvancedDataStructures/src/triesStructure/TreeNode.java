package triesStructure;

public class TreeNode {
	
	TreeNode left,right,parent;
	boolean isleaf;
	//int identifier;
	char[] ipaddr=new char[32];
	int data;
	String ip;
	
	TreeNode(){
		left=right=parent=null;
		isleaf=false;
		data=-1;
	}
	
	TreeNode(int d, TreeNode t){
		left=right=null;
		parent=t;
		data=d;
		isleaf=false;
	}

}
