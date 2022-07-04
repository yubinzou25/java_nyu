import java.util.Scanner;
public class LoopSum {

	public static int sum100() {
		int sum = 0;
		int count = 0;
		do{
			count++;
			sum = sum + count;
		}while(count<100);
	
		return sum;
	}
	
	public static int sumN(int N) {

		int sum = 0;
		int count = 0;
		do{
			count++;
			sum = sum + count;
		}while(count<N);

		return sum;
	}
	
	public static void main(String[] args) {
		System.out.println("input N number to calculate the sum of integers from 1 to N");
		Scanner in = new Scanner(System.in);
		int inputN = in.nextInt();
		int SUM100 = sum100();
		int SUMN = sumN(inputN);
		System.out.println("The sum of the integers 1 through 100 is:"+SUM100);
		System.out.println("The sum of the integers 1 through "+ inputN + " is:"+SUMN);



	}
	
}
