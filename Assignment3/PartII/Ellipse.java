
public class Ellipse {
	private static int nextId = 0;
	private int id;
	private double semiMajorAxis;
	private double semiMinorAxis;
	public Ellipse(){
	    this.id = this.nextId;
	    this.semiMajorAxis = 2.0;
	    this.semiMinorAxis = 1.0;
	    this.nextId++;
    }
    public Ellipse(double a,double b){
        this.id = this.nextId;
        this.semiMajorAxis = a;
        this.semiMinorAxis = b;
    }
    public double getSemiMajorAxis(){
	    return (this.semiMajorAxis );
    }
    public double getSemiMinorAxis(){
	    return (this.semiMinorAxis );
    }
    public double getArea(){
	    return (this.semiMinorAxis*this.semiMajorAxis*Math.PI);
    }
    public int getID(){
	    return (this.id);
    }
    public static void main(String[] args){
	    Ellipse E1 = new Ellipse();
	    System.out.println("E1 ID:"+E1.id+" semiMajorAxis:"+E1.semiMajorAxis+
                " semiMinorAxis:"+E1.semiMinorAxis+" Area:"+E1.getArea());
	    Ellipse E2 = new Ellipse(3.0,4.0);
        System.out.println("E2 ID:"+E2.id+" semiMajorAxis:"+E2.semiMajorAxis+
                " semiMinorAxis:"+E2.semiMinorAxis+" Area:"+E2.getArea());
    }


}
