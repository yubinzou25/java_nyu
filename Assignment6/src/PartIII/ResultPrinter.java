package PartIII;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ResultPrinter {

	MathOperation op;
	
	public ResultPrinter(MathOperation op) {
		this.op = op;
	}
	
	public void go(double a, double b) {
		 System.out.println("result is " + op.operation(a,b));
	}

	public static void go(double a, double b, MathOperation op) {
		 System.out.println("result is " + op.operation(a,b));
	}
	
	public static void go(Collection<Pair<Double,Double>> c, MathOperation op) {
		Iterator iterator = c.iterator();
		while(iterator.hasNext()){
			Pair<Double,Double> pair = (Pair<Double, Double>) iterator.next();
			System.out.println("result is "+op.operation(pair.getKey(),pair.getValue()));
		}
	}
	
	public static void main(String[] args) {
		ResultPrinter rp = new ResultPrinter((a,b)->a+b);
		
		rp.go(3.0, 4.0);
		
		ResultPrinter.go(4.0, 2.0, (a,b)->a*b);
		
		ArrayList<Pair<Double,Double>> al = new ArrayList<Pair<Double,Double>>();
		Pair<Double, Double> p = new Pair<Double, Double>(3.0, 4.0);
		al.add(p);
		p = new Pair<Double, Double>(5.0, 6.0);
		al.add(p);
		p = new Pair<Double, Double>(7.0, 8.0);
		al.add(p);
		
		ResultPrinter.go(al, (a,b)->a/b);
		
	}
}
