package CustomException;

public class TestCustomException {
    public static void main(String[] args) {
        try{
            new Student("Chavdar", "email@email.com");
        } catch (InvalidPersonNameException e){
            System.out.println(e.getMessage());
        }

        new Student("Chavdar", "email@email.com");
    }
}
