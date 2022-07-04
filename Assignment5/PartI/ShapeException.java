public class ShapeException extends Exception{
    private String shapeName;
    public ShapeException(String shapeName){
        super("Invalid Shape Name" + shapeName);
        this.shapeName = shapeName;
    }
    public String getInvalidName(){
        return this.shapeName;
    }
}
