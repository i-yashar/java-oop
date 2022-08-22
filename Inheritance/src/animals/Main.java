package animals;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//
//        List<Animal> animals = new ArrayList<>();
//
//        while(true){
//            String input = reader.readLine();
//
//            if(input.equals("Beast!"))
//                break;
//
//            String[] tokens = reader.readLine().split("\\s+");
//
//            addAnimals(input, tokens, animals);
//        }
//
//        printAnimals(animals);
    }

//    private static void printAnimals(List<Animal> animals) {
//        for (Animal animal : animals) {
//            System.out.println(animal.getClass().getSimpleName());
//            System.out.println(animal);
//            animal.produceSound();
//        }
//    }
//
//    public static void addAnimals(String type, String[] info, List<Animal> animals){
//        String name = info[0];
//        int age = Integer.parseInt(info[1]);
//        String gender = info.length == 3 ? info[2] : null;
//
//        switch (type){
//            case "Cat":
//                try{
//                    Cat cat = new Cat(name, age, gender);
//                    animals.add(cat);
//                } catch (IllegalArgumentException exception){
//                    System.out.println(exception.getMessage());
//                }
//                break;
//            case "Kitten":
//                try{
//                    Kitten kitten = new Kitten(name, age);
//                    animals.add(kitten);
//                } catch(IllegalArgumentException exception){
//                    System.out.println(exception.getMessage());
//                }
//                break;
//            case "Tomcat":
//                try{
//                    Tomcat tomcat = new Tomcat(name, age);
//                    animals.add(tomcat);
//                } catch (IllegalArgumentException exception){
//                    System.out.println(exception.getMessage());
//                }
//                break;
//            case "Frog":
//                try{
//                    Frog frog = new Frog(name, age, gender);
//                    animals.add(frog);
//                } catch (IllegalArgumentException exception){
//                    System.out.println(exception.getMessage());
//                }
//                break;
//            case "Dog":
//                try{
//                    Dog dog = new Dog(name, age, gender);
//                    animals.add(dog);
//                } catch (IllegalArgumentException exception){
//                    System.out.println(exception.getMessage());
//                }
//                break;
//        }
//    }
}
