package EnterNumbers;

import java.util.Scanner;
import java.util.stream.IntStream;

public class EnterNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(true){
            String start = scanner.nextLine();
            String end = scanner.nextLine();

            try{
                printNumbers(start, end);
                break;
            } catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private static void printNumbers(String start, String end) {
        int s;
        int e;

        try{
            s = Integer.parseInt(start);
            e = Integer.parseInt(end);
        } catch (NumberFormatException ex){
            throw new IllegalArgumentException("Invalid input, please enter new range", ex);
        }

        if(!(1 < s && s < e && e < 100)){
            throw new IllegalArgumentException("Invalid input, please enter new range");
        }

        IntStream.rangeClosed(s, e).forEach(System.out::println);
    }
}
