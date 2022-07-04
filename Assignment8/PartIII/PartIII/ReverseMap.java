package PartIII;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ReverseMap {

	public static Map<Object, Set<Object>> getInverted(Map<Object, Object> mp) {
		Map<Object, Set<Object>> rev_map = new HashMap<>();
		for(Map.Entry<Object,Object> entry: mp.entrySet()){
			if (rev_map.containsKey(entry.getValue())){
				rev_map.get(entry.getValue()).add(entry.getKey());
			}
			else{
				Set<Object> new_set = new HashSet<>();
				new_set.add(entry.getKey());
				rev_map.put(entry.getValue(),new_set);
			}
		}
		return rev_map;

	}
	public static void main(String[] args) {
		Map<Object,Object> m = new HashMap<Object,Object>();
		m.put("Key1", new Integer(2));
		m.put("Key2", new Integer(5));
		m.put("Key4", new Integer(2));
		m.put("Key5", new Integer(8));
		m.put("Key6", new Integer(18));
		m.put("Key7", new Integer(24));
		System.out.println("hashmap is " + m);
		
		System.out.println("inverted: " + ReverseMap.getInverted(m));
		

	}

}
