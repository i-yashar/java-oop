package Lab;

import java.util.ArrayList;
import java.util.List;

public class StackOfStrings {
    private List<String> data;

    public StackOfStrings(){
        this.data = new ArrayList<>();
    }

    public void push(String item){
        data.add(item);
    }

    public String pop(){
        return data.isEmpty() ? null : data.remove(data.size() - 1);
    }

    public String peek(){
        return data.isEmpty() ? null : data.get(data.size() - 1);
    }

    public boolean isEmpty(){
        return data.isEmpty();
    }
}
