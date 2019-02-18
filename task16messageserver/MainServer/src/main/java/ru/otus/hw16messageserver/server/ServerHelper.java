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

    private static final String HOST = "localhost";

    private static final int MESSAGESERVER_PORT = 8091;
    private static final String MESSAGESERVER_START_COMMAND = "java -jar ../MessageServer/target/messageserver-1.0.jar " + MESSAGESERVER_PORT;


    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    public static Server createServer(int port) throws IOException {
        logger.log(Level.INFO, "Server starting...");
        final String PUBLIC_HTML = "public_html";
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        MessageServer messageServer = new MessageServer();
        /*
        ProcessRunner processRunner = new ProcessRunner();

        try {
            logger.log(Level.INFO, "Message server starting...");
            processRunner.start(MESSAGESERVER_START_COMMAND);
        } catch (IOException e) {
            e.printStackTrace();
        }


        SocketWorker socketWorker = new SocketWorker(new Socket(HOST,MESSAGESERVER_PORT));
        */

        context.addServlet(new ServletHolder(new DBServiceWebSocketServlet(messageServer)),
                "/dbwebsocket");
        Server server = new Server(port);
        server.setHandler(new HandlerList(resourceHandler, context));

        return server;
    }
}
