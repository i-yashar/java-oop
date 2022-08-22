package CustomException;

public class Student {
    private String name;
    private String email;

    public Student(String name, String email) throws InvalidPersonNameException{
        setName(name);
        setEmail(email);
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setName(String name) throws InvalidPersonNameException{
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);

            if(!Character.isAlphabetic(ch)){
                throw new InvalidPersonNameException("Invalid student name");
            }
        }
    }
}
