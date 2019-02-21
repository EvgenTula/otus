package ru.otus.hw16messageserver.frontend;

import ru.otus.hw16messageserver.frontend.frontendservice.FrontendServiceImpl;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.FrontendService;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.MessageClientConnect;

import java.io.IOException;
import java.util.logging.Logger;

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

/*
        SocketWorker socketWorker = new SocketWorker(new Socket("localhost",8091));
        socketWorker.init();
        */
/*
        Address frontend = new Address("localhost",8093);
        Address dbserver = new Address("localhost",8092);
        Message msg = new MessageToFrontend(frontend,dbserver,"the force awakens");

        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("className", MessageToFrontend.class.getName());
        jsonObject.put("data",gson.toJson(msg));
        socketWorker.send(jsonObject.toString());
*/


        //messageServer.send();

        //messageServer.socketWorkerFontend.send(jsonObject.toString());
        /*
        socketWorker.send("ping1");
        socketWorker.send("ping2");
        socketWorker.send("ping3");
        */

        int port = Integer.valueOf(args[0]);
        logger.info("Frontend started on port " + port);
        FrontendService frontendService = new FrontendServiceImpl(port);
        frontendService.start();

    }
}
