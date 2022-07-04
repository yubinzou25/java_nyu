package fruit;

import java.util.Objects;

public class Fruit {
    private String color;
    private boolean rotten;
    private static int next_id;
    private int id;
    public Fruit(){
        this.color = "green";
        this.rotten = false;
        this.id = next_id++;

    }
    public Fruit(String color, boolean rotten){
        this();
        this.color = color;
        this.rotten = rotten;


    }
    public String getColor(){

        return this.color;
    }
    public void setColor(String color){
        this.color = color;
    }
    public boolean isRotten(){
        return this.rotten;
    }
    public void setRotten(boolean rotten){
        this.rotten = rotten;
    }
    public int getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "color='" + color + '\'' +
                ", rotten=" + rotten +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if (o==null || getClass()!= o.getClass()) return false;
        Fruit fruit = (Fruit) o;
        return rotten == fruit.rotten  && Objects.equals(color,fruit.color);
    }


    @Override
    public int hashCode() {
        return Objects.hash(color, rotten, id);
    }
}

