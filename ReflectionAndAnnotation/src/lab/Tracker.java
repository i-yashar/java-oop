package lab;

import lab.Author;

import java.lang.reflect.Method;

public class Tracker {
    public static void printMethodsByAuthor(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getAnnotation(Author.class) != null)
                System.out.println(method.getAnnotation(Author.class).name() + ": " + method.getName());
        }
    }
}
