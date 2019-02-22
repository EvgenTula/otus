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



MessageServer
1. Поднимаем messageServer
2. Создем серверный сокет
3. Слушаем

FrontServer
1. Поднимаем сервер
2. Создаем сокетный канал на messageServer с очередями in/out
3. Даем его FrontendService-у
4. Посылаем сообщение "Я фронт с таким-то адресом". Т.е. кладем в очередь out

MessageServer
1. Получаем подключение
2. Создаем канал
3. Кладем канал куда-нибудь в MessageSystem. Пока без адреса
4. Канал получает сообщение. Смотрит. Это сообщение о регистрации?
5. Если да то задает себе адрес.
6. Если нет - ищет адрес получателя, достает его канал и кладет в него сообщение

DBServer - по аналогии с FrontServer
Канал это 2 очереди in/out и два потока, которые эти очереди обрабатывают.


*/

public class MessageServer {
//WEB-SERVER 8090
    private static final String HOST = "localhost";

    private static final int MESSAGESERVER_PORT = 8091;
    private static final String MESSAGESERVER_START_COMMAND = "java -jar ../MessageServer/target/messageserver-1.0.jar";

    private static final int DBSERVER_PORT = 8092;
    private static final String DBSERVER_START_COMMAND = "java -jar ../DBServer/target/dbserver.jar";

    private static final int FRONTEND_PORT = 8093;
    private static final String FRONTEND_START_COMMAND = "java -jar ../FrontendService/target/frontend.jar";

private static final int WEBSERVER_PORT = 8090;

    private static final Logger logger = Logger.getLogger(MessageServer.class.getName());

    public MessageSystemContext messageSystemContext;

    //public SocketWorker socketWorkerFontend;
    //public SocketWorker socketWorkerDBService;

    public static void main(String[] args) {
        new MessageServer();
    }

    public MessageServer() {
        logger.info("MessageServer started");

        /*
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("HOST",HOST);
        jsonObject.put("MESSAGESERVER_PORT",MESSAGESERVER_PORT);
        jsonObject.put("DBSERVER_PORT",DBSERVER_PORT);
        jsonObject.put("FRONTEND_PORT",FRONTEND_PORT);*/


        MessageSystemSocketServer messageSystemSocketServer = new MessageSystemSocketServer(MESSAGESERVER_PORT);
        messageSystemSocketServer.start();
        /*
        messageSystemContext = new MessageSystemContext(
                new MessageSystemSocketServer(MESSAGESERVER_PORT),
                new Address(HOST,DBSERVER_PORT),
                new Address(HOST,FRONTEND_PORT));
        */
    }

}
