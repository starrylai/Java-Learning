import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class AreaCalculatorTest {
    public static void main(String[] args){
        List<Shape> shapes = new ArrayList<>
                (Arrays.asList(
                        new Rect(5,3),
                        new Circle(3),
                        new Triangle(2,4.2)
                ));

        System.out.println("====形状面积计算器====");
        double totalArea = 0;
        for(Shape shape : shapes){
            double area = shape.calcArea();
            totalArea += area;
            System.out.printf("%s -> 面积：%.2f\n", shape, area);
        }

        System.out.println("所有形状的总面积为：%.2f"+totalArea);
    }
}
