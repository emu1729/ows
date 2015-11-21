
public class Problem2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 1;
		int a = 1;
		int b = 1;
		int x = 0;
		do {
			a = b;
			b = n;
			n = a + b;
			if (n % 2 == 0){
				x = n + x;
			}
		} while (n < 4000000);
		System.out.print(x);
	}

}
