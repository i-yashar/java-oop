import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Bag {
    private Map<String, LinkedHashMap<String, Long>> bag;
    private long capacity;
    private long gold = 0;
    private long gems = 0;
    private long cash = 0;

    public Bag(long capacity){
        this.bag = new LinkedHashMap<String, LinkedHashMap<String, Long>>();
        this.capacity = capacity;
    }

    private boolean isWithinCapacity(long count){
        return capacity >= (this.bag.values().stream().map(Map::values).flatMap(Collection::stream).mapToLong(e -> e).sum() + count);
    }

    private boolean isWithinCapacity(String type, long count){
        return count <= this.bag.get(type).values().stream().mapToLong(e -> e).sum();
    }

    private long getTypeCount(String type){
        return this.bag.get(type).values().stream().mapToLong(e -> e).sum();
    }

    public Map<String, LinkedHashMap<String, Long>> getBag() {
        return bag;
    }

    private boolean isEligible(String type, long count){
        switch (type) {
            case "Gem":
                return performTypeCheck(type, "Gold", count);
            case "Cash":
                return performTypeCheck(type, "Gem", count);
        }

        return true;
    }

    private void fillEmptySpots(String type, String name){
        if (!this.bag.containsKey(type)) {
            this.bag.put((type), new LinkedHashMap<String, Long>());
        }

        if (!this.bag.get(type).containsKey(name)) {
            this.bag.get(type).put(name, 0L);
        }
    }

    private boolean performTypeCheck(String type, String secondType, long count){
        if (!this.bag.containsKey(type)) {
            if (this.bag.containsKey(secondType)) {
                if (!isWithinCapacity("Gold", count)) {
                    return false;
                }
            } else {
                return false;
            }
        } else if (!isWithinCapacity(secondType, getTypeCount(type) + count)) {
            return false;
        }

        return true;
    }

    private void addTreasure(String type, String name, long count){
        this.bag.get(type).put(name, this.bag.get(type).get(name) + count);
        if (type.equals("Gold")) {
            gold += count;
        } else if (type.equals("Gem")) {
            gems += count;
        } else if (type.equals("Cash")) {
            cash += count;
        }
    }

    public void fillBag(String type, String name, long count){
        if(type == null || !this.isWithinCapacity(count))
            return;

        if(!this.isEligible(type, count))
            return;

        this.fillEmptySpots(type, name);

        this.addTreasure(type, name, count);
    }

    public void printBag(){
        for (Map.Entry<String, LinkedHashMap<String, Long>> entry : bag.entrySet()) {
            Long totalValue = entry.getValue().values().stream().mapToLong(l -> l).sum();

            System.out.printf("<%s> $%s%n", entry.getKey(), totalValue);

            entry.getValue().entrySet().stream().sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey())).forEach(i -> System.out.println("##" + i.getKey() + " - " + i.getValue()));
        }
    }
}
