package ru.otus.hw16messageserver.dbserver;

import java.io.IOException;
import java.net.Socket;
import ru.otus.hw16messageserver.dbserver.dbservice.DBServerImpl;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemContext;
import ru.otus.hw16messageserver.messageserver.messagesystem.SocketWorker;


public class DBServerMain {

    public static void main(String[] args) throws IOException {

        String messageSystemHost = args.length > 0? args[0]: MessageSystemContext.DEFAULT_MESSAGE_SYSTEM_HOST;
        int messageSystemPort = args.length > 1? Integer.parseInt(args[1]): MessageSystemContext.DEFAULT_MESSAGE_SYSTEM_PORT;
        String frontendAddressValue = args.length > 2? args[2]: MessageSystemContext.DEFAULT_FRONTEND_ADDRESS_VALUE;
        String dbServerAddressValue = args.length > 3? args[3]: MessageSystemContext.DEFAULT_DB_SERVER_ADDRESS_VALUE;

        MessageSystemContext messageSystemContext = new MessageSystemContext(
                frontendAddressValue,
                dbServerAddressValue,
                new SocketWorker(new Socket(messageSystemHost,messageSystemPort)));
        DBServerImpl dbServer = new DBServerImpl(messageSystemContext);
        dbServer.start();
    }
}
