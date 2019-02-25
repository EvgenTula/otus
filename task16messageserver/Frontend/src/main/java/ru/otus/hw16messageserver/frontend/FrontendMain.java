package ru.otus.hw16messageserver.frontend;

import org.eclipse.jetty.server.Server;
import ru.otus.hw16messageserver.frontend.frontendservice.FrontendServiceImpl;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemContext;
import ru.otus.hw16messageserver.messageserver.messagesystem.SocketWorker;

import java.net.Socket;
import java.util.logging.Logger;

public class FrontendMain {

    private static final Logger logger = Logger.getLogger(FrontendMain.class.getName());

    public static void main(String[] args) throws Exception {

        String messageSystemHost = args.length > 0? args[0]: MessageSystemContext.DEFAULT_MESSAGE_SYSTEM_HOST;
        int messageSystemPort = args.length > 1? Integer.parseInt(args[1]): MessageSystemContext.DEFAULT_MESSAGE_SYSTEM_PORT;
        String frontendAddressValue = args.length > 2? args[2]: MessageSystemContext.DEFAULT_FRONTEND_ADDRESS_VALUE;
        String dbServerAddressValue = args.length > 3? args[3]: MessageSystemContext.DEFAULT_DB_SERVER_ADDRESS_VALUE;

        MessageSystemContext messageSystemContext = new MessageSystemContext(
                frontendAddressValue,
                dbServerAddressValue,
                new SocketWorker(new Socket(messageSystemHost,messageSystemPort)));

        FrontendServiceImpl frontendService = new FrontendServiceImpl(messageSystemContext);
        frontendService.start();

        Server server = ServerHelper.createServer(8090, frontendService);
        server.start();
        server.join();

    }
}
