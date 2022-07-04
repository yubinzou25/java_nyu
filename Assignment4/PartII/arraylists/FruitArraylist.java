package arraylists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

import fruit.*;


public class FruitArraylist {
	public static double averageSourness(ArrayList<Fruit> fruitArrayList){
		Iterator iterator = fruitArrayList.iterator();
		double total =0.0;
		int count = 0;
		while(iterator.hasNext()){
			Object fruit = iterator.next();
			if (fruit instanceof Lemon){
				total+=((Lemon) fruit).getSourness();
				count++;
			}
		}
		return total/count;
	}
	public static void removeMatching(ArrayList<Fruit> fruitArrayList, Fruit matchObject){
		Iterator iterator = fruitArrayList.iterator();
		while (iterator.hasNext()){
			Object fruit = iterator.next();
			if (matchObject.equals(fruit)){
				iterator.remove();
			}
		}
	}
	public static Fruit RottenApple(ArrayList<Fruit> fruitArrayList){
		Iterator iterator = fruitArrayList.iterator();
		while (iterator.hasNext()){
			Object fruit = iterator.next();
			if (fruit instanceof Apple){
				Apple apple = (Apple) fruit;
				if(apple.isRotten()&& Objects.equals(apple.getColor(),"green"))
					return apple;
			}
		}
		return null;
	}
	public static void PrintOut(ArrayList<Fruit> fruitArrayList){
		Iterator iterator = fruitArrayList.iterator();
		while (iterator.hasNext()){
			System.out.println(iterator.next());
			}
		}
	public static void PrintOutSame(ArrayList<Fruit> fruitArrayList,Fruit matchObject){
		Iterator iterator = fruitArrayList.iterator();
		while (iterator.hasNext()){
			Object fruit = iterator.next();
			if(fruit==matchObject)
				System.out.println(fruit);
		}
	}
	public static void PrintOutEqual(ArrayList<Fruit> fruitArrayList,Fruit matchObject){
		Iterator iterator = fruitArrayList.iterator();
		while (iterator.hasNext()){
			Object fruit = iterator.next();
			if(fruit.equals(matchObject))
				System.out.println(fruit);
		}
	}

	public static void main(String[] args) {
		
		// this ArrayList MUST be parameterized
		ArrayList<Fruit> fruitArrayList = new ArrayList();
		fruitArrayList.add(new Apple("sweet","crisp ","red",false));
		fruitArrayList.add(new Apple("tart","soft","green",true));
		fruitArrayList.add(new Apple("tart","soft","green",true));
		Random rand = new Random();
		for (int i=0;i<3;i++){ fruitArrayList.add(new Lemon(rand.nextInt(101),"sour",false)); }
		fruitArrayList.add(new Orange("mandarin","sweet",true));
		fruitArrayList.add(new Orange("mandarin","sweet",true));
		// this is the variable you should retain to compare
		// to the other objects in the arraylist
		System.out.println("Lemon's average sourness is:"+averageSourness(fruitArrayList));
		Fruit rottenGreenApple1 = RottenApple(fruitArrayList);
		System.out.println("The removed matching objects:"+rottenGreenApple1);
		System.out.println("The objects are equal (in value) to the matching objects:");
		PrintOutEqual(fruitArrayList,rottenGreenApple1);
		System.out.println("The objects are same to the matching objects:");
		PrintOutSame(fruitArrayList,rottenGreenApple1);
		removeMatching(fruitArrayList,rottenGreenApple1);
		System.out.println("The remaining objects:");
		PrintOut(fruitArrayList);



	}
}
