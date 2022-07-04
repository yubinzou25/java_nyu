import java.util.Scanner;
public class NumberToEnglish {
	private static final String [] Unit={
			"",
			"one",
			"two",
			"three",
			"four",
			"five",
			"six",
			"seven",
			"eight",
			"nine",
			"ten",
			"eleven",
			"twelve",
			"thirteen",
			"fourteen",
			"fifteen",
			"sixteen",
			"seventeen",
			"eighteen",
			"nineteen"
	};
	private static final String [] Decade = {
			"",
			"ten",
			"twenty",
			"thirty",
			"forty",
			"fifty",
			"sixty",
			"seventy",
			"eighty",
			"ninety"
	};
	private static final String [] Thousands = {
			"",
			" thousand",
			" million"
	};
	public static String Digit3ToEnglish(int Digit3){
		String Digit3English = "";
		int hundreds = Digit3/100;
		int decades = Digit3%100;
		int units = decades%20;
		if (decades!=0){
			if (decades>=20){
				Digit3English += Decade[decades/10];
				if(decades%10!=0){
					Digit3English += "-"+Unit[decades%10];
				}
			}
			else {
				Digit3English += Unit[units];
			}
		}
		if (hundreds!=0)
		{
			if(Digit3English.equals("")){
				Digit3English = Unit[hundreds] + " hundred " + Digit3English;
			}
			else {
				Digit3English = Unit[hundreds] + " hundred and " + Digit3English;
			}
		}
		return Digit3English;
	}
	public static String numberToEnglish(int number){
		String sign= "";
		int  Digit3 ;
		String [] Digit3English = new String[3];
		String NumberEnglish = "";
		if(number<0) {
			sign = "negative ";
			number = -number;
		}
		for (int i =0;i<3;i++)
		{
			Digit3 = number%1000;
			number = number/1000;
			Digit3English[i] = Digit3ToEnglish(Digit3);
		}
		for (int j =2;j>=0;j--){
			if (Digit3English[j].equals("")){
				continue;
			}
			else {
				NumberEnglish+=Digit3English[j]+Thousands[j];
			}
			if (NumberEnglish.equals("")==false&&j!=0)
				NumberEnglish+=", ";
		}
		if (NumberEnglish.equals("")) {
			NumberEnglish += "zero";
		}
		return  sign+NumberEnglish;
	}
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a number: " );
		
		int number = in.nextInt();

		if (number>999999999&&number<-999999999)
			throw new IllegalArgumentException("INPUT NUMBER EXCEED RANGE");
		
		System.out.println("The number " + number + " in English is " + NumberToEnglish.numberToEnglish(number));
	
	}
}
