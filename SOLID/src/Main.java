import controllers.AppenderWorkshop;
import controllers.ConsoleAppender;
import controllers.LayoutWorkshop;
import controllers.SimpleLayout;
import core.EngineImpl;
import enums.ReportLevel;
import interfaces.Appender;
import interfaces.AppenderFactory;
import interfaces.Layout;
import interfaces.LayoutFactory;
import logger.Logger;
import logger.MessageLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        var reader = new BufferedReader(new InputStreamReader(System.in));
        Logger logger = new MessageLogger();
        new EngineImpl(reader, logger).run();
        System.out.println(logger);

    }
}
