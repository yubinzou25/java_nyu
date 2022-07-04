package fruit;

import java.util.Objects;

public class Lemon extends Citrus{
    private int sourness;
    public Lemon(){
        this.sourness = 1;
    }
    public  Lemon(int sourness, String taste, boolean rotten){
        super(taste,"yellow",rotten);
        this.sourness = sourness;
    }
    public int getSourness(){
        return this.sourness;
    }
    public void setSourness(int sourness){
        this.sourness = sourness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lemon)) return false;
        if (!super.equals(o)) return false;
        Lemon lemon = (Lemon) o;
        return sourness == lemon.sourness;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sourness);
    }

    @Override
    public String toString() {
        return "Lemon{" +
                "sourness=" + sourness +
                "} " + super.toString();
    }
}
