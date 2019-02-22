package ru.otus.hw16messageserver.frontend;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.hw16messageserver.frontend.frontendservice.FrontendServiceImpl;
import ru.otus.hw16messageserver.frontend.websocket.ServiceWebSocketServlet;

import java.io.IOException;

public class ServerHelper {

    public static Server createServer(int port, FrontendServiceImpl frontendService) throws IOException {
        final String PUBLIC_HTML = "public_html";
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        //MessageServer messageServer = new MessageServer();

        context.addServlet(new ServletHolder(new ServiceWebSocketServlet(frontendService)),
                "/dbwebsocket");
        Server server = new Server(port);
        server.setHandler(new HandlerList(resourceHandler, context));

        return server;
    }
}
