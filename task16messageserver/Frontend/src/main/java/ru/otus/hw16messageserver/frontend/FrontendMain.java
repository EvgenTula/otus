package ru.otus.hw16messageserver.frontend;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ru.otus.hw16messageserver.frontend.frontendservice.FrontendService;
import ru.otus.hw16messageserver.frontend.frontendservice.FrontendServiceImpl;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.SocketWorker;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.logging.Level;

public class FrontendMain {

    private static final Logger logger = Logger.getLogger(FrontendMain.class.getName());



    public static void main(String[] args) throws IOException {
        /*
        JSONParser jsonParser = new JSONParser();

        JSONObject params = (JSONObject) jsonParser.parse(Arrays.toString(args));
        String className = (String) jsonObject.get("className");
        String gsonData = (String) jsonObject.get("data");
        jsonObject.put("HOST",HOST);
        jsonObject.put("MESSAGESERVER_PORT",MESSAGESERVER_PORT);
        jsonObject.put("DBSERVER_PORT",DBSERVER_PORT);
        jsonObject.put("FRONTEND_PORT",FRONTEND_PORT);
        params.get("")
        logger.info(Arrays.toString(args));
        */
        SocketWorker socketWorker = new SocketWorker(new Socket("localhost",8091));
        socketWorker.init();
        socketWorker.send("ping1");
        socketWorker.send("ping2");
        socketWorker.send("ping3");


        int port = Integer.valueOf(args[0]);
        logger.info("Frontend started on port " + port);
        FrontendService frontendService = new FrontendServiceImpl(port);
        frontendService.start();

    }
}
