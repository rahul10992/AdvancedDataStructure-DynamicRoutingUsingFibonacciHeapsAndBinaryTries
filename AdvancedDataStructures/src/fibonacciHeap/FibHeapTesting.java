package fibonacciHeap;

public class FibHeapTesting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FibonacciHeap fb=new FibonacciHeap();
		Node[] arr=new Node[11];
		for(int i=0;i<=10;i++){
			arr[i]=new Node(i,i);
			fb.insert(arr[i]);
		}
		
		fb.printTopLevelDll();
		fb.removeMin();
		fb.printTopLevelDll();
		fb.decreaseKey(arr[7], 2);
		fb.printTopLevelDll();
		
		
	}

}
