package Lab;

import java.util.ArrayList;
import java.util.Random;

public class RandomArrayList extends ArrayList<String> {
    private Random random;

    public RandomArrayList(){
        this.random = new Random();
    }

    public Object getRandomElement(){
        int index = random.nextInt(size());

        return super.remove(index);
    }
}
