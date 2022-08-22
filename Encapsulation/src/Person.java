import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Person {
    private String name;
    private double money;
    private List<Product> products;

    public Person(String name, double money) {
        setName(name);
        setMoney(money);
        this.products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if(name == null || name.trim().equals("")){
            throw new IllegalArgumentException("Name cannot be empty");
        }

        this.name = name;
    }

    private void setMoney(double money) {
        if(money < 0){
            throw new IllegalArgumentException("Money cannot be negative");
        }

        this.money = money;
    }

    public void buyProduct(Product product){
        if(product.getCost() > this.money){
            throw new IllegalArgumentException(this.name + " can't afford " + product.getName());
        } else {
            this.money -= product.getCost();
            this.products.add(product);
            System.out.println(this.name + " bought " + product.getName());
        }
    }
}
