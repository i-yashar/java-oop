import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        long capacity = Long.parseLong(scanner.nextLine());
        String[] input = scanner.nextLine().split("\\s+");

        Bag bag = new Bag(capacity);

        for (int i = 0; i < input.length; i += 2) {
            String name = input[i];
            long count = Long.parseLong(input[i + 1]);
            String type = getTreasureType(name);

            bag.fillBag(type, name, count);
        }

        bag.printBag();
    }

    private static String getTreasureType(String name) {
        String type = null;

        if (name.length() == 3) {
            type = "Cash";
        } else if (name.toLowerCase().endsWith("gem")) {
            type = "Gem";
        } else if (name.toLowerCase().equals("gold")) {
            type = "Gold";
        }

        return type;
    }
}