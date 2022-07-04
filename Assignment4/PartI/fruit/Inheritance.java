package fruit;

import java.util.Objects;

public class Inheritance {

	public static void main(String[] args) {
		// Here's some scratch space to experiment/debug your Fruit objects
		Fruit fruit1 = new Orange();
		Fruit fruit2 = new Lemon();
		System.out.println(fruit1);
		System.out.println(fruit2);
		System.out.println(fruit1.equals(fruit2));
	}

}
