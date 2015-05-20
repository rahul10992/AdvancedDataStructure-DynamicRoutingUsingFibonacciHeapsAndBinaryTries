package triesStructure;

public class BinaryTriesTesting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BinaryTries bt=new BinaryTries();
		
		TreeNode t=new TreeNode();
		t.data=99999;
		t.ip="0000011111";
		TreeNode d=new TreeNode();
		d.data=88888;
		d.ip="0101010101";
		
		bt.insert(d.ip, 88888);
		bt.insert(t.ip, 99999);
		
		String s=bt.traverse(d.ip);
		System.out.println(s+"has been removed");
		
		bt.compressTrie(bt.root);
		s=bt.traverse(d.ip);
		System.out.println(s+"has been removed");
		
	}

}
