package triesStructure;

import java.util.ArrayList;

public class BinaryTries {
	
	TreeNode root=new TreeNode();
	//ArrayList<TreeNode> nodeList=new ArrayList<TreeNode>();
	
	public void insert(String ip, int data){
		//System.out.println("entering insert");
		//TreeNode iter=new TreeNode();
		TreeNode iter=root;
		
		for(int i=0;i<ip.length()-1;i++){//go upto 31
			int bin=Integer.parseInt(ip.charAt(i)+"");//take ith character
			//System.out.println("bin: "+bin);
			if(bin==0){//go left
				if(iter.left!=null){
					iter=iter.left;
				}
				else{//make new node if you are about to fall off.
					TreeNode temp=new TreeNode();
					temp.data=0;
					temp.parent=iter;
					iter.left=temp;
					iter=iter.left;
					//iter.right=temp;
				}
			}
			else if(bin==1){//go right
				if(iter.right!=null){
					iter=iter.right;
				}
				else{//make new node to not fall off
					TreeNode temp=new TreeNode();
					temp.data=1;
					temp.parent=iter;
					iter.right=temp;
					iter=iter.right;
				}
			}
			else{//you come here then its really really messed up. what did you do? The horror!
				System.out.println("ERROR!!! in tries insert");
				System.exit(1);
			}
		}
		//last bit do it all again.
		int bin=Integer.parseInt(ip.charAt(ip.length()-1)+"");
		
		if(bin==0){//me go left. make leaf. give data. 
			TreeNode tempNode=new TreeNode();
			tempNode.data=data;
			tempNode.parent=iter;
			tempNode.isleaf=true;
			iter.left=tempNode;
		}
		else if(bin==1){
			TreeNode tempNode=new TreeNode();
			tempNode.data=data;
			tempNode.parent=iter;
			tempNode.isleaf=true;
			iter.right=tempNode;
		}	
	}


	public void compressTrie(TreeNode root){
		//post order traversal
		if(root==null){
			return;
		}
		if(root.left!=null)
			compressTrie(root.left);
		if(root.right!=null)
			compressTrie(root.right);
		
		//3 cases. if right subtree is null
		//if left subtree is null
		//if both the subtrees have the same node.
		if(root.left==null && root.right!=null && root.right.isleaf==true){
			if(root.data==0){
				root.parent.left=root.right;
			}
			else{
				root.parent.right=root.right;
			}
			root.right.parent=root.parent;
		}
		else if(root.left!=null && root.right==null &&  root.left.isleaf==true){
			if(root.data==0){
				root.parent.left=root.left;
			}
			else{
				root.parent.right=root.left;
			}
			root.left.parent=root.parent;
		}
		else if(root.right!=null && root.left!=null && root.left.data==root.right.data && root.left.isleaf==true && root.right.isleaf==true){
			if(root.data==0){
				root.parent.left=root.left;	
			}
			else{
				root.parent.right=root.left;
			}
			root.left.parent=root.parent;
		}
	}
	
	public String traverse(String OpBin){
		TreeNode iter=root;
		int i=0;
		for(i=0;i<OpBin.length();i++){
			if(iter.isleaf==true){
				break;
			}
			else{
				if(Integer.parseInt(OpBin.charAt(i)+"")==0){
					iter=iter.left;
				}
				else{
					iter=iter.right;
				}
			}
		}
		String s=OpBin.substring(0, i);
		//s=iter.data+"";
		return s;
	}
	
}
