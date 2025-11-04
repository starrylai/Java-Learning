public class Rect implements Shape{
    private double length;
    private double width;

    public Rect(double length, double width){
        this.length = length;
        this.width = width;
    }

    @Override
    public double calcArea() {
        return width*length;
    }

    @Override
    public String toString(){
        return String.format("矩形：长=%.2f,宽=%.2f",length, width);
    }

}
