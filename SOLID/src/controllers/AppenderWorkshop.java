package controllers;

import enums.ReportLevel;
import interfaces.Appender;
import interfaces.AppenderFactory;
import interfaces.Layout;

public class AppenderWorkshop implements AppenderFactory {
    @Override
    public Appender produce(String type, ReportLevel reportLevel, Layout layout) {
        return switch (type){
            case "ConsoleAppender" -> new ConsoleAppender(reportLevel, layout);
            case "FileAppender" -> new FileAppender(reportLevel, layout);
            default -> throw new IllegalStateException("Illegal appender parameter");
        };
    }
}
