package controllers;

import enums.ReportLevel;
import interfaces.Appender;
import interfaces.Layout;

public class ConsoleAppender extends AbstractAppender {
    public ConsoleAppender(ReportLevel reportLevel, Layout layout){
        super(reportLevel, layout);
    }

    @Override
    protected String getType() {
        return "ConsoleAppender";
    }

    @Override
    public void append(String date, ReportLevel reportLevel, String message) {
        System.out.println(this.getLayout().format(date, reportLevel, message));
        super.incrementAppendsCount();
    }
}
