package ru.otus.hw16messageserver.messageserver;

import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemSocketServer;

import java.io.IOException;
import java.util.logging.Logger;

public class MessageServer {

    private static final String HOST = "localhost";
    private static final int MESSAGESERVER_PORT = 8091;

    private static final int DBSERVER_PORT = 8092;
    private static final String DBSERVER_START_COMMAND = "java -jar ../DBServer/target/dbserver.jar";

    private static final int FRONTEND_PORT = 8093;
    private static final String FRONTEND_START_COMMAND = "java -jar ../Frontend/target/frontend.jar";

    //private static final int WEBSERVER_PORT = 8090;

    private static final Logger logger = Logger.getLogger(MessageServer.class.getName());

    public static void main(String[] args) {
        new MessageServer();
    }

    public MessageServer() {
        logger.info("MessageServer started");

        MessageSystemSocketServer messageSystemSocketServer = new MessageSystemSocketServer(MESSAGESERVER_PORT);
        messageSystemSocketServer.start();

        processRun(FRONTEND_START_COMMAND, HOST + " " + MESSAGESERVER_PORT);
        processRun(DBSERVER_START_COMMAND, HOST + " " + MESSAGESERVER_PORT);
    }

    private void processRun(String cmd, String params) {
        try {
            ProcessRunner processRunner = new ProcessRunner();
            processRunner.start(cmd + " " + params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
