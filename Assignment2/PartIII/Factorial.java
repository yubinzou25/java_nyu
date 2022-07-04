import java.util.Scanner;
public class Factorial{
	public static int Factorialnum(int inputN){
		int FactorialProdut = 1;
		int count = 0;
		do {
		count++;
		FactorialProdut = FactorialProdut*count;
		}while(count < inputN);
		return FactorialProdut;
	
	}

	public static void main(String[] args) {
		System.out.println("Please input the number N to calculate the factorial of N:");
		Scanner in = new Scanner(System.in);
		int inputN = in.nextInt();
		int FactorialProdut = Factorialnum(inputN);
		System.out.println("The factorial produt of "+inputN+" is:"+FactorialProdut);

	}
}
