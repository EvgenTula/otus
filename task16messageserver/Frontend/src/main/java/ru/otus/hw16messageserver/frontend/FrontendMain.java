package ru.otus.hw16messageserver.frontend;

import org.eclipse.jetty.server.Server;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ru.otus.hw16messageserver.frontend.frontendservice.FrontendServiceImpl;
import ru.otus.hw16messageserver.messageserver.messagesystem.FrontendService;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class FrontendMain {

    private static final Logger logger = Logger.getLogger(FrontendMain.class.getName());

    private static final String HOST = "localhost";

    private static final int MESSAGESERVER_PORT = 8091;
    private static final String MESSAGESERVER_START_COMMAND = "java -jar ../MessageServer/target/messageserver-1.0.jar";

    private static final int DBSERVER_PORT = 8092;
    private static final String DBSERVER_START_COMMAND = "java -jar ../DBServer/target/dbserver.jar";

    private static final int FRONTEND_PORT = 8093;
    private static final String FRONTEND_START_COMMAND = "java -jar ../FrontendService/target/frontend.jar";

    private static final int WEBSERVER_PORT = 8090;

    public static void main(String[] args) throws Exception {
/*
        JSONParser jsonParser = new JSONParser();

        JSONObject params = (JSONObject) jsonParser.parse(Arrays.toString(args));
        //String className = (String) jsonObject.get("className");
        //String gsonData = (String) jsonObject.get("data");
        params.put("HOST",HOST);
        params.put("MESSAGESERVER_PORT",MESSAGESERVER_PORT);
        params.put("DBSERVER_PORT",DBSERVER_PORT);
        params.put("FRONTEND_PORT",FRONTEND_PORT);
        params.put("WEBSERVER_PORT",WEBSERVER_PORT);
        logger.info(Arrays.toString(args));
*/

        /*
        Address frontend = new Address("localhost",8093);
        Address dbserver = new Address("localhost",8092);
        */

        int port = Integer.valueOf(args[0]);
        logger.info("Frontend started on port " + port);
        FrontendServiceImpl frontendService = new FrontendServiceImpl(port);
        frontendService.start();

        Server server = ServerHelper.createServer(/*port*/8090, frontendService);
        server.start();
        server.join();

    }
}
