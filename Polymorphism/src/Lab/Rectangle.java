package Lab;

public class Rectangle extends Shape{
    private Double width;
    private Double height;

    public Rectangle(Double width, Double height){
        this.width = width;
        this.height = height;
    }

    public Double getHeight() {
        return height;
    }

    public Double getWidth() {
        return width;
    }

    @Override
    public Double calculatePerimeter() {
        if(getPerimeter() == null){
            setPerimeter(2 * this.width + 2 * this.height);
        }
        return getPerimeter();
    }

    @Override
    public Double calculateArea() {
        if(getArea() == null){
            setArea(this.width * this.height);
        }
        return getArea();
    }
}
