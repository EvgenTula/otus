package ru.otus.hw15messagesystem;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import ru.otus.hw15messagesystem.websocket.DBServiceWebSocketServlet;

public class ServerHelper {

    public static Server createServer(int port) {
        final String PUBLIC_HTML = "public_html";
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(DBServiceWebSocketServlet.class,"/dbwebsocket");
        Server server = new Server(port);
        server.setHandler(new HandlerList(resourceHandler, context));
        return server;
    }
}
