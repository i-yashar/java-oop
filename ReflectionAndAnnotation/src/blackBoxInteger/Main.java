package blackBoxInteger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Class<BlackBoxInt> clazz = BlackBoxInt.class;

        Constructor<BlackBoxInt> ctor = clazz.getDeclaredConstructor();
        ctor.setAccessible(true);

        BlackBoxInt blackBoxInt = ctor.newInstance();

        while(true){
            String[] tokens = reader.readLine().split("[_]");

            if(tokens[0].equals("END"))
                break;

            int value = Integer.parseInt(tokens[1]);
            Method method;

            switch (tokens[0]){
                case "add":
                    method = clazz.getDeclaredMethod("add", int.class);
                    break;
                case "subtract":
                    method = clazz.getDeclaredMethod("subtract", int.class);
                    break;
                case "multiply":
                    method = clazz.getDeclaredMethod("multiply", int.class);
                    break;
                case "divide":
                    method = clazz.getDeclaredMethod("divide", int.class);
                    break;
                case "leftShift":
                    method = clazz.getDeclaredMethod("leftShift", int.class);
                    break;
                case "rightShift":
                    method = clazz.getDeclaredMethod("rightShift", int.class);
                    break;
                default:
                    method = null;
            }
            method.setAccessible(true);
            method.invoke(blackBoxInt, value);
            Field innerValue = blackBoxInt.getClass().getDeclaredField("innerValue");
            innerValue.setAccessible(true);
            System.out.println(innerValue.get(blackBoxInt));
        }
    }
}
