
public class Prob1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x = 0;
		
		for(int n = 1; n < 1000; n++){
			if (n % 3 == 0){
				x = x + n;
			} else if (n % 5 == 0){
				x = x + n;
			} else if (n % 15 == 0){
				x = x - n;
			}
		}
		System.out.print("Value of x is " + x);
	}

}
