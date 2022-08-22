package controllers;

import interfaces.File;

public class LogFile implements File {
    private StringBuilder builder;

    public LogFile(){
        this.builder = new StringBuilder();
    }
    @Override
    public void write(String text) {
        builder.append(text);
    }

    @Override
    public int getSize() {
        int sum = 0;

        for (int i = 0; i < builder.length(); i++) {
            char ch = builder.charAt(i);

            if(Character.isAlphabetic(ch)){
                sum += ch;
            }
        }

        return sum;
    }
}
