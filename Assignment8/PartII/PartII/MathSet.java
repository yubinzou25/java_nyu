package PartII;

import javafx.util.Pair;
import java.util.HashSet;
import java.util.Set;

public class MathSet<E> extends HashSet<E> {

	public Set<E> intersection(Set<E> s2) {
		Set<E> inter_set = new HashSet<>();
		for (E e:s2){
			if (this.contains(e)){
				inter_set.add(e);
			}
		}
		return inter_set;
	}
	public Set<E> union(Set<E> s2) {
		Set<E> union_set = (Set<E>) this.clone();
		for (E e:s2){
			if (!(union_set.contains(e))){
				union_set.add(e);
			}
		}
		return union_set;
	}

	public <T> Set<Pair<E,T>> cartesianProduct(Set<T> s2) {
		Set<Pair<E,T>> cart_set = new HashSet<>();
		for (T t:s2){
			for (E e:this){
				cart_set.add(new Pair<E,T>(e,t));
			}
		}
		return cart_set;
	}
	public static void main(String[] args) {
		// create two MathSet objects of the same type.
		// See if union and intersection works.
		
		// create another MathSet object of a different type
		// calculate the cartesian product of this set with one of the
		// above sets
		MathSet<Object> set1 = new MathSet<>();
		set1.add(1);
		set1.add(2);
		set1.add(3);
		MathSet<Object> set2 = new MathSet<>();
		set2.add(3);
		set2.add(4);
		set2.add(5);
		System.out.println(set1.intersection(set2));
		System.out.println(set1.union(set2));
		System.out.println(set1.cartesianProduct(set2));
	}
}
