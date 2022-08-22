package ValidPerson;

public class TestPerson {
    public static void main(String[] args) {
        try{
            createNewPerson("I", "Y", 20);
        } catch (IllegalArgumentException ex){
            System.out.println("Exception thrown: " + ex.getMessage());
        }
    }

    private static Person createNewPerson(String firstName, String lastName, int age) {
        return new Person(firstName, lastName, age);
    }
}
