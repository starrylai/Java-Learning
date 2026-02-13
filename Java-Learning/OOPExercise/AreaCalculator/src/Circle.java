public class Circle implements Shape{
    private double radius;

    public Circle(double radius){
        this.radius = radius;
    }

    @Override
    public double calcArea(){
        return Math.PI*Math.pow(radius,2);
    }

    @Override
    public String toString(){
        return String.format("圆形：半径=%.2f",radius);
    }
}
