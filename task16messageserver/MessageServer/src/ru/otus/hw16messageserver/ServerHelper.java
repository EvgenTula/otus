package ru.otus.hw16messageserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemImpl;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemContext;
import ru.otus.hw16messageserver.websocket.DBServiceWebSocketServlet;


public class ServerHelper {

    public static Server createServer(int port) {
        final String PUBLIC_HTML = "public_html";
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        MessageSystemImpl messageSystem = new MessageSystemImpl();
        MessageSystemContext messageSystemContext = new MessageSystemContext(messageSystem);

        //DBService dbService = DBHelper.createDBService(messageSystemContext,new Address("dbService"));
        //FrontendService frontendService = new FrontendServiceImpl(messageSystemContext,new Address("frontendService"));
        /*
        messageSystemContext.setDBServiceAddress(dbService.getAddress());
        messageSystemContext.setFrontendServiceAddress(frontendService.getAddress());
        messageSystem.addMember(dbService);
        messageSystem.addMember(frontendService);

        MessageServer messageServerMain = new MessageServer();
        */
        context.addServlet(new ServletHolder(new DBServiceWebSocketServlet(/*frontendService*/)),
                "/dbwebsocket");
        Server server = new Server(port);
        server.setHandler(new HandlerList(resourceHandler, context));

        return server;
    }
}
