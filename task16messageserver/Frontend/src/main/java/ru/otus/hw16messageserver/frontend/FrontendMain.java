package ru.otus.hw16messageserver.frontend;

import java.util.logging.Logger;
import java.util.logging.Level;

public class FrontendMain {

    private static final Logger logger = Logger.getLogger(FrontendMain.class.getName());

    public static void main(String[] args) {
        logger.log(Level.INFO, "Frontend started on port " + args[0]);
    }
}
