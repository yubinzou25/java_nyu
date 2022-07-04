package fruit;

import java.util.Objects;

public class Apple extends Fruit{
    private String taste;
    private String texture;
    public Apple(){
        this.taste ="good";
        this.texture = "smooth";
    }
    public Apple(String taste,String texture,String color,boolean rotten){
        super(color, rotten);
        this.taste = taste;
        this.texture = texture;
    }
    public String getTaste(){
        return this.taste;
    }
    public void setTaste(String taste){
        this.taste = taste;
    }
    public String getTexture(){
        return this.texture;
    }
    public void setTexture(String texture){
        this.texture = texture;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "taste='" + taste + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Apple)) return false;
        if (!super.equals(o)) return false;
        Apple apple = (Apple) o;
        return Objects.equals(taste, apple.taste) && Objects.equals(texture, apple.texture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), taste, texture);
    }
}
