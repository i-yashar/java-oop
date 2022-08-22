package Lab;

public class Circle extends Shape{
    private Double radius;

    public Circle(Double radius){
        this.radius = radius;
    }

    public final Double getRadius() {
        return radius;
    }

    @Override
    public Double calculatePerimeter() {
        if(getPerimeter() == null){
            setPerimeter(2 * Math.PI * this.radius);
        }
        return getPerimeter();
    }

    @Override
    public Double calculateArea() {
        if(getArea() == null){
            setArea(Math.pow(this.radius, 2) * Math.PI);
        }
        return getArea();
    }
}
