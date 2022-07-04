package PartI;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Iterator;

public class MaxFinder<T extends Number> {
    private ArrayList<T> list;

    public MaxFinder(){
     this.list = new ArrayList<>();
    }
    public void add(T t){
        this.list.add(t);
    }
    public void add(ArrayList<? extends Number> new_list){

        for(Object o : new_list){
            this.list.add((T) o);
        }
    }
    public T max(){
        try{
        if (this.list==null||this.list.size()==0)
            throw new NullPointerException();}
        catch (NullPointerException npe){npe.printStackTrace();}
        T max_num = this.list.get(0);
        for(T o : this.list){
            if (Compare(max_num,o)<0){
                max_num = o;
            }
        }
        return max_num;
    }
    public int Compare(T n1,T n2){
        long l1 = n1.longValue();
        long l2 = n2.longValue();
        if (l1 != l2)
            return (l1 < l2 ? -1 : 1);
        return Double.compare(n1.doubleValue(), n2.doubleValue());
    }


    public static  void main (String [] args) {
        MaxFinder<Number> max_finder = new MaxFinder<>();
        ArrayList<Number> new_list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Number d = Math.random();
            System.out.println(d);
            new_list.add(d);
        }
        for (int i = 0; i < 10; i++) {
            Double d = Math.random();
            System.out.println(d);
            max_finder.add(d);
        }
        max_finder.add(new_list);
        System.out.println("The max is: "+max_finder.max());
    }

}
