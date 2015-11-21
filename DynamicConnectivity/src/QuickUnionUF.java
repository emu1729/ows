import java.util.Scanner;

public class QuickUnionUF {
	private int[] id;
	private int[] sz;
	
	public QuickUnionUF(int N){
		id = new int[N];
		sz = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
			sz[i] = 1;
		}
	}
	
	private int root(int i)
	{
		while (i != id[i]) {
			id[i] = id[id[i]];
			i = id[i];
		}
		return i;
	}
	
	public boolean connected(int p, int q)
	{
		return root(p) == root(q);
	}
	
	public void union(int p, int q)
	{
		int i = root(p);
		int j = root(q);
		if (i == j) return;
		if (sz[i] < sz[j]) {
			id [i] = j; sz[j] += sz[i];
		}
		else{
			id[j]=i; sz[i] += sz[j];
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println("Please input an array value N.");
		Scanner scanner = new Scanner (System.in);
		String nStr = scanner.nextLine();
		int num = Integer.parseInt(nStr);
		
		QuickUnionUF myUnion = new QuickUnionUF(num);
		
		myUnion.union(3,5);
		myUnion.union(4,7);
		myUnion.union(0,3);
		
		System.out.print("Check whether (0,5) is connected:");
		if (myUnion.connected(0, 5))
			System.out.println("true");
		else 
			System.out.println("false");

	}
}
