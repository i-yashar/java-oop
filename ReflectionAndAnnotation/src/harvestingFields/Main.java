package harvestingFields;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException {
		Scanner scanner = new Scanner(System.in);

		Field[] fields = Class.forName("harvestingFields.RichSoilLand").getDeclaredFields();

		while(true){
			String input = scanner.nextLine();

			if(input.equals("HARVEST"))
				break;

			switch (input){
				case "private":
					Arrays.stream(fields).filter(m -> Modifier.isPrivate(m.getModifiers())).forEach(m -> System.out.printf("%s %s %s%n", Modifier.toString(m.getModifiers()), m.getType().getSimpleName(), m.getName()));
					break;
				case "protected":
					Arrays.stream(fields).filter(m -> Modifier.isProtected(m.getModifiers())).forEach(m -> System.out.printf("%s %s %s%n", Modifier.toString(m.getModifiers()), m.getType().getSimpleName(), m.getName()));
					break;
				case "public":
					Arrays.stream(fields).filter(m -> Modifier.isPublic(m.getModifiers())).forEach(m -> System.out.printf("%s %s %s%n", Modifier.toString(m.getModifiers()), m.getType().getSimpleName(), m.getName()));
					break;
				case "all":
					Arrays.stream(fields).forEach(m -> System.out.printf("%s %s %s%n", Modifier.toString(m.getModifiers()), m.getType().getSimpleName(), m.getName()));
					break;
			}
		}
	}
}
