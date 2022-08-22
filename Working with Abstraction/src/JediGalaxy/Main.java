import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] dimensions = readArray(scanner, " ");

        Matrix starMatrix = new Matrix(dimensions[0], dimensions[1]);
        starMatrix.fill();

        long sum = 0;

        while (true)
        {
            String command = scanner.nextLine();

            if(command.equals("Let the Force be with you")) break;

            int[] goodCoordinates = readArray(command, " ");

            command = scanner.nextLine();

            int[] evilCoordinates = readArray(command, " ");

            starMatrix.moveEvil(evilCoordinates);

            sum += starMatrix.collectStars(goodCoordinates);
        }

        System.out.println(sum);


    }

    private static int[] readArray(Scanner scanner, String pattern) {
        return Arrays.stream(scanner.nextLine().split(pattern)).mapToInt(Integer::parseInt).toArray();
    }

    private static int[] readArray(String command, String pattern) {
        return Arrays.stream(command.split(pattern)).mapToInt(Integer::parseInt).toArray();
    }
}