package PartIII;

public class Pair<K, V> {

	K key;
	V value;
	
	public Pair(K k, V v) {
		this.key = k;
		this.value = v;		
	}
	
	public K getKey() {
		return key;
	}
	
	public V getValue() {
		return value;
	}
	
	public String toString() {
		return "[Pair: " + key + ", "+ value + "]";
	}
}
