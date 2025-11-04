public class Triangle implements Shape{
    private double base;//底
    private double height;

    public Triangle(double base, double height){
        this.base = base;
        this.height = height;
    }

    @Override
    public double calcArea(){
        return base*height/2;
    }

    @Override
    public String toString(){
        return String.format("三角形：底=%.2f,高=%.2f",base,height);
    }

}
