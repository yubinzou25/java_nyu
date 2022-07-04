import java.util.Objects;

public class Holiday {
	private String name;
	private int day;
	private String month;

	public Holiday(String name,int day, String month){
		this.name = name;
		this.day = day;
		this.month = month;
	}
	public static boolean inSameMonth(Holiday h1,Holiday h2){
		return (Objects.equals(h1.month,h2.month));
	}
	public static double avgDate(Holiday[] holidays){
		double total_day = 0;
		for (Holiday h:holidays ){
			total_day += h.day;
		}
		return (total_day/holidays.length);
	}
	public String getName(){
		return (this.name);
	}
	public void setName(String name){
		this.name = name;
	}
	public String getMonth(){
		return (this.month);
	}
	public void setMonth(String month){
		this.month = month;
	}
	public int getDay(){
		return (this.day);
	}
	public void setDay(int day){
		this.day = day;
	}
	public String toString(){
		return (this.name+":"+this.month+" "+this.day);
	}


	public static void main(String[] args) {
		
		Holiday[] holidays = new Holiday[5];
		holidays[0] = new Holiday("May Day", 1, "May");
		holidays[1] = new Holiday("Independence Day", 4, "July");
		System.out.println(holidays[0].toString());
		System.out.println(holidays[1].toString());
		System.out.println(avgDate(holidays));
		
	}
}
