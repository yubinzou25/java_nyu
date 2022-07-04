package fruit;

import java.util.Objects;

public class Citrus extends Fruit {
    private String taste;
    public Citrus(){
        this.taste = "sour";
    }
    public Citrus(String taste,String color,boolean rotten){
        super(color,rotten);
        this.taste = taste;
    }
    public String getTaste(){
        return this.taste;
    }
    public  void setTaste(String taste){
        this.taste = taste;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Citrus)) return false;
        if (!super.equals(o)) return false;
        Citrus citrus = (Citrus) o;
        return Objects.equals(taste, citrus.taste);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), taste);
    }

    @Override
    public String toString() {
        return "Citrus{" +
                "taste='" + taste + '\'' +
                "} " + super.toString();
    }
}
