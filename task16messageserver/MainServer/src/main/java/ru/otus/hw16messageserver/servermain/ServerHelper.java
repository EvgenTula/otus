package ru.otus.hw16messageserver.servermain;

import ru.otus.hw16messageserver.servermain.websocket.DBServiceWebSocketServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;


public class ServerHelper {

    private static final int MESSAGESERVER_PORT = 8091;
    private static final String MESSAGESERVER_START_COMMAND = "java -jar ../MessageServer/target/messageserver-1.0.jar " + MESSAGESERVER_PORT;

    private static final int DBSERVER_PORT = 8092;
    private static final String DBSERVER_START_COMMAND = "java -jar ../L16.1.2-client/target/client.jar";

    private static final int FRONTEND_PORT = 8093;
    private static final String FRONTEND_START_COMMAND = "java -jar ../Frontend/target/frontend-1.0.jar " + FRONTEND_PORT;
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    public static Server createServer(int port) {
        logger.log(Level.INFO, "Server starting...");
        final String PUBLIC_HTML = "public_html";
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        ProcessRunner processRunner = new ProcessRunner();

        try {
            logger.log(Level.INFO, "Message server starting...");
            processRunner.start(MESSAGESERVER_START_COMMAND);
            //logger.log(Level.INFO, processRunner.getOutput());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        try {
            logger.log(Level.INFO, "Frontend starting...");
            processRunner.start(FRONTEND_START_COMMAND);
            //logger.log(Level.INFO, processRunner.getOutput());
        } catch (IOException e) {
            e.printStackTrace();
        }
        */



        /*
        try {
            logger.log(Level.INFO, "DBServer starting...");
            processRunner.start(DBSERVER_START_COMMAND);
            //logger.log(Level.INFO, processRunner.getOutput());
        } catch (IOException e) {
            e.printStackTrace();
        }
        */


        /*
        MessageServer messageServer = new MessageServer(MESSAGESERVER_PORT);
        MessageSystemImpl messageSystem = new MessageSystemImpl();
        MessageSystemContext messageSystemContext = new MessageSystemContext(messageSystem);
        */

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
