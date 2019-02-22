package ru.otus.hw16messageserver.server.messageserver;

import ru.otus.hw16messageserver.server.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.MessageSystemContext;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.MessageSystemSocketServer;
import ru.otus.hw16messageserver.server.ProcessRunner;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.SocketWorker;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

/*
Фронт это веб сервер + вебсокет + фронендсервайс

Еще раз в контексте 2 адреса и ИНТЕРФЕЙС системы сообщений.
Т.е. new MssageSystemContext(frontAdderss, dbAddress, new MessageSystemSocketClient())
MessageSystemSocket messageSystemSocketClient = new MessageSystemSocketClient();
new MssageSystemContext(frontAdderss, dbAddress, messageSystemSocketClient)
И это делаем в каждом сервисе-jar.
А в главном MessageSystemSocket messageSystemSocketServer = new MessageSystemSocketServer();

Адреса остаются. Только за ними сокеты будут

*/

public class MessageServer {

    private static final String HOST = "localhost";

    private static final int MESSAGESERVER_PORT = 8091;
    private static final String MESSAGESERVER_START_COMMAND = "java -jar ../MessageServer/target/messageserver-1.0.jar";

    private static final int DBSERVER_PORT = 8092;
    private static final String DBSERVER_START_COMMAND = "java -jar ../DBServer/target/dbserver.jar";

    private static final int FRONTEND_PORT = 8093;
    private static final String FRONTEND_START_COMMAND = "java -jar ../FrontendService/target/frontend.jar";

    private static final Logger logger = Logger.getLogger(MessageServer.class.getName());

    public MessageSystemContext messageSystemContext;

    //public SocketWorker socketWorkerFontend;
    //public SocketWorker socketWorkerDBService;


    public MessageServer() {
        logger.info("MessageServer started");

        /*
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("HOST",HOST);
        jsonObject.put("MESSAGESERVER_PORT",MESSAGESERVER_PORT);
        jsonObject.put("DBSERVER_PORT",DBSERVER_PORT);
        jsonObject.put("FRONTEND_PORT",FRONTEND_PORT);*/



        messageSystemContext = new MessageSystemContext(
                new MessageSystemSocketServer(MESSAGESERVER_PORT),
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

}
