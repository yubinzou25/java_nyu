
public class GravityCalculator {

	public static double calculatePosition(int t, int v, int x) {
		double a = -9.81;
		double xt = 0.5 * a * Math.pow(t,2) + v * t + x;
		return xt;
	}
	
	public static void main(String[] args) {
		int t = 10;
		int v = 0;
		int x = 0;
		double final_position = calculatePosition(x,v,t);
		System.out.println("final_posisiton:"+final_position);
		
		
	}
}
