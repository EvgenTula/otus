package ru.otus.hw16messageserver.server;

import ru.otus.hw16messageserver.server.messageserver.MessageServer;
import ru.otus.hw16messageserver.server.websocket.DBServiceWebSocketServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ServerHelper {

    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    public static Server createServer(int port) throws IOException {
        logger.info("Server starting...");
        final String PUBLIC_HTML = "public_html";
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        MessageServer messageServer = new MessageServer();

        context.addServlet(new ServletHolder(new DBServiceWebSocketServlet(messageServer)),
                "/dbwebsocket");
        Server server = new Server(port);
        server.setHandler(new HandlerList(resourceHandler, context));

        return server;
    }
}
