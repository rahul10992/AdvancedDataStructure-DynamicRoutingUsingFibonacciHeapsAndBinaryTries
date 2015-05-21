package fibonacciHeap;

import java.util.HashMap;



public class FibonacciHeap {
	
	Node min;
	int noNodes;
	
	FibonacciHeap(){
		noNodes=0;
		min=null;
	}
	
	/**@insert operation
	 * if no. of nodes is 0, make min=n;
	 * else add it as a neighbor and see if it is the min.
	 * works for all no. of nodes.
	 */
	public void insert(Node n){
		
		if(noNodes==0){
			min=n;
			min.left=min;
			min.right=min;
		}
		else{
			Node temp=min.left;
			min.left=n;
			n.right=min;
			temp.right=n;
			n.left=temp;
			
			if(n.data<min.data){
				//System.out.println("updating min");
				min=n;
			}
		}
		n.resetChildCut();
		noNodes++;
	}
	
	/**@printTopLevelDll operation
	 * prints the topl level linked list.
	 */
	public void printTopLevelDll(){
		Node temp=min;
		System.out.println("******************starting printing***********");
		if(min==null){
			System.out.println("SORRY. BUT THE HEAP IS EMPTY!");
		}
		else{
			System.out.println("min value: "+temp.data + " min name: "+ temp.data + " min degree: "+ temp.degree);
			temp=temp.right;
			while(temp!=min){
				System.out.println("value: "+temp.data+" name: "+ temp.name + " degree: "+ temp.degree);
				temp=temp.right;
			}
		
		}

		System.out.println("******************ending printing***********");
	}
	
	/**@removeMin operation
	 * take care. Tread carefully.
	 * many cases to consider. Need generalized code.
	 * returns the value that has been deleted.
	 */
	public Node removeMin(){
		//first check if the min node is null
		if(min==null){
			System.out.println("Accessing an empty fibonacciHeap! returning null!");
			return null;
		}
		Node result=min;
		if(min.left==min){//if the min is the only element in the top level LL
			
			if(min.degree==0){//if the no. of children is 0
				min=null;
			}
			else{//children > 0
				min=min.children.get(0);
				min.parent=null;
				Node temp=min.right;
				Node end=min;
				
				while(temp!=end){//updating min and making parents as null
					temp.parent=null;
					if(min.data<temp.data){
						min=temp;
					}
					temp=temp.right;
				}
				pairwiseCombine();
				
			}
		}
		else{//if more than 1 element in top level LL
			if(min.degree==0){//no need to update parent ptrs
				min.left.right=min.right;
				min.right.left=min.left;
				
			}
			else{//need to update parent ptrs
				Node first=min.children.get(0);
				//int lastInd=min.children.size()-1;
				Node last=min.children.get((min.children.size()-1));
				
				min.left.right=first;
				first.left=min.left;
				last.right=min.right;
				min.right.left=last;
				
				first.parent=null;//updating parent ptrs
				while(first!=last.right){
					first.parent=null;
					first=first.right;
				}
				
				
			}
			
			min=min.right;
			updateMinValue();//needed. ensures the right min value at the end of it all. amortized cost helps reduce the complexity.
			pairwiseCombine();
		}
	noNodes--;
	return result;
		
	}
	
	/**@pairwiseCombine operation
	 * go through the set of nodes to decide which one to merge and which not to.
	 * stay frosty.
	 */
	public void pairwiseCombine(){
		
		HashMap<Integer, Node> degrees=new HashMap<Integer, Node>();
		Node iter=min.right;
		Node end=min;
		Node temp;
		int count=1;
		while(iter!=end){
			count++;
			iter=iter.right;
		}
		
		iter=min;
		degrees.put(iter.degree, iter);
		iter=iter.right;
		count--;
		
		for(int i=0;i<count;i++){
			
			while(degrees.containsKey(iter.degree)){
				
				temp=degrees.remove(iter.degree);
				temp.resetChildCut();
				iter.resetChildCut();
				if(temp.data<=iter.data){
					//merge them with temp on top and position them where iter currently is.
					//System.out.println("making "+temp.data+ " the parent of "+iter.data);
					//System.out.println("making "+temp.name+ " the parent of "+iter.name);
					//Node anotherTemp=temp;
					temp.left.right=temp.right;
					temp.right.left=temp.left;
					temp.left=iter.left;
					iter.left.right=temp;
					temp.right=iter.right;
					iter.right.left=temp;
					temp.addChild(iter);
					iter=temp;
				}
				else{
					//still merge them with iter on top.
					//System.out.println("making "+iter.data+ " the parent of "+temp.data);
					//System.out.println("making "+iter.name+ " the parent of "+temp.name);
					temp.left.right=temp.right;
					temp.right.left=temp.left;
					iter.addChild(temp);
				}
			
			}
			degrees.put(iter.degree, iter);
			iter=iter.right;
			
		}
		
		while(min.parent!=null){
			min=min.parent;
		}
		
	}
	
	/**@insertForDecreaseKey operation
	 * This operation just enters the node into the top level. Nothing else.
	 * you will have to remove parents and change degrees.
	 */
	public void insertForDecreaseKey(Node n){
		
		Node temp=min.left;
		min.left=n;
		n.right=min;
		temp.right=n;
		n.left=temp;
		n.parent=null;
		
		if(n.data<=min.data){
			//System.out.println("updating min");
			min=n;
		}
		n.resetChildCut();
		
	}
	
	/**@decreaseKey operation
	 * This operation reduces a value only if it will be lesser than the already given value.
	 * it returns true if the value has been reduced. Else it returns false.
	 * it needs another operation to remove the value from the arrayList and to enter it into the top level LL. 
	 * which will be similar to the insert except the no. of nodes will stay the same.
	 * cascade cut is also done here.
	 */
	public boolean decreaseKey(Node n, int amount){
		if(n.data>amount){
			n.data=amount;
			if(n.parent!=null){//check if the node is a root and you might have to update min only. nothing else.
				if(n.data<n.parent.data){
					Node par=n.parent;
					if(n.left!=n){
						n.left.right=n.right;
						n.right.left=n.left;
					}
					par.degree--;
					par.children.remove(n);
					insertForDecreaseKey(n);
					
					while(par.childCut==true && par.parent!=null){
						Node temp=par;//CASCADE CUT BEING DONE HERE
						par=par.parent;
						if(temp.left!=temp){
							temp.left.right=temp.right;
							temp.right.left=temp.left;
						}
						par.degree--;
						par.children.remove(temp);
						insertForDecreaseKey(temp);
					}
					if(par.parent!=null){
						par.childCut=true;
					}
					
				}
			}
			return true;
		}
		else{
			return false;
		}
		
	}
	
	/**@cascadeCut operation
	 * check if the node's childcut property is enabled.
	 * if it is, then insert into top level list. and reset the property, else set it.
	 * NOT USED!
	 */
	private void cascadeCut(Node temp) {
		// TODO Auto-generated method stub
		if(temp.childCut==true){
			temp.resetChildCut();
			Node par=temp.parent;
			par.degree--;
			if(temp.left==temp){
				par.children.remove(0);
			}
			else{
				temp.right.left=temp.left;
				temp.left.right=temp.right;
				par.children.remove(temp);
			}
			insertForDecreaseKey(temp);
			
			while(par!=null){
				if(par.childCut==true){
					//insertForDecreaseKey(par);
				}
				
			}
			
		}
		else{
			temp.childCut=true;
		}
	}

	private void meld(FibonacciHeap f){//melds two fibonacciHeaps
		
		if(f==null){
			
		}else if(this.min==null){
			min=f.min;
		}else{
			Node firstthis=min;
			Node lastthis=min.left;
			Node ffirst=f.min;
			Node flast=f.min.left;
			
			firstthis.left=flast;
			flast.right=firstthis;
			lastthis.right=ffirst;
			ffirst.left=lastthis;
		}
		
	}
	
	/**@updateMin operation
	 * NEEDS THE MIN TO BE IN THE TOP LEVEL LINKED LIST
	 * gets the right min value.
	 */
	public void updateMinValue(){
		Node temp=min.right;
		Node end=min;
		while(temp!=end){
			if(temp.data<min.data){
				min=temp;
			}
			temp=temp.right;
		}
		
	}

}
