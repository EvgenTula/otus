package ru.otus.hw16messageserver.server.messageserver;

import org.json.simple.JSONObject;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.SocketWorker;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.MessageSystemContext;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.MessageSystemImpl;
import ru.otus.hw16messageserver.server.ProcessRunner;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class MessageServer {

    private static final String HOST = "localhost";

    private static final int MESSAGESERVER_PORT = 8091;
    private static final String MESSAGESERVER_START_COMMAND = "java -jar ../MessageServer/target/messageserver-1.0.jar";

    private static final int DBSERVER_PORT = 8092;
    private static final String DBSERVER_START_COMMAND = "java -jar ../DBServer/target/dbserver.jar";

    private static final int FRONTEND_PORT = 8093;
    private static final String FRONTEND_START_COMMAND = "java -jar ../Frontend/target/frontend.jar";

    private static final Logger logger = Logger.getLogger(MessageServer.class.getName());

    public MessageSystemContext messageSystemContext;

    public SocketWorker socketWorkerFontend;


    public MessageServer() {
        logger.info("MessageServer started");

        /*
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("HOST",HOST);
        jsonObject.put("MESSAGESERVER_PORT",MESSAGESERVER_PORT);
        jsonObject.put("DBSERVER_PORT",DBSERVER_PORT);
        jsonObject.put("FRONTEND_PORT",FRONTEND_PORT);*/
        processRun(FRONTEND_START_COMMAND, String.valueOf(FRONTEND_PORT)/*jsonObject.toString()*/);
        processRun(DBSERVER_START_COMMAND, String.valueOf(DBSERVER_PORT)/*jsonObject.toString()*/);

        messageSystemContext = new MessageSystemContext(
                new MessageSystemImpl(MESSAGESERVER_PORT),
                new Address(HOST,DBSERVER_PORT),
                new Address(HOST,FRONTEND_PORT));



        /*
        try {
            socketWorkerFontend = new SocketWorker(new Socket(HOST,FRONTEND_PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
        socketWorkerFontend.init();
        */
    }

    private void processRun(String cmd, String params) {
        try {
            logger.info("Process starting...");
            ProcessRunner processRunner = new ProcessRunner();
            processRunner.start(cmd + " " + params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
