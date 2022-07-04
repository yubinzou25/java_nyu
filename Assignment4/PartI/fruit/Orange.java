package fruit;

import java.util.Objects;

public class Orange extends Citrus{
    private String type;
    public Orange() {
        this.type = "mandarin";
    }
    public Orange(String type, String taste, boolean rotten){
        super(taste,"orange",rotten);
        this.type =type;
    }
    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Orange)) return false;
        if (!super.equals(o)) return false;
        Orange orange = (Orange) o;
        return Objects.equals(type, orange.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
    }

    @Override
    public String toString() {
        return "Orange{" +
                "type='" + type + '\'' +
                "} " + super.toString();
    }
}
