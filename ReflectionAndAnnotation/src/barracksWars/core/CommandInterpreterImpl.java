package barracksWars.core;

import barracksWars.core.commands.Command;
import barracksWars.interfaces.*;
import com.sun.security.auth.UnixNumericGroupPrincipal;
import jdk.jshell.spi.ExecutionControl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class CommandInterpreterImpl implements CommandInterpreter {
    private Repository repository;
    private UnitFactory factory;

    public CommandInterpreterImpl(Repository repository, UnitFactory factory){
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public Executable interpretCommand(String[] data, String commandName) {
        Executable executable = null;

        try {
            Class<?> clazz = Class.forName(getCorrectClassName("barracksWars.core.commands.", commandName));
            Constructor<?> ctor = clazz.getDeclaredConstructor();
            executable = (Executable) ctor.newInstance();

            setFields(executable, data);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return executable;
    }

    private void setFields(Executable executable, String[] data) throws IllegalAccessException {
        Field[] executableFields = executable.getClass().getDeclaredFields();
        Field[] localFields = this.getClass().getDeclaredFields();

        for (Field field : executableFields) {
            Inject annotation = field.getAnnotation(Inject.class);
            if(annotation != null){
                field.setAccessible(true);
                if(field.getType() == String[].class){
                    field.set(executable, data);
                } else {
                    for (Field localField : localFields) {
                        if(localField.getType() == field.getType()){
                            field.set(executable, localField.get(this));
                        }
                    }
                }
            }
        }
    }

    private String getCorrectClassName(String path, String commandName) {
        String substring = commandName.substring(1);

        return path + Character.toUpperCase(commandName.charAt(0)) + substring + "Command";
    }
}
