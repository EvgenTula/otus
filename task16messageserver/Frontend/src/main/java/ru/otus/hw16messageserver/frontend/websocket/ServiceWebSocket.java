package ru.otus.hw16messageserver.frontend.websocket;

import com.google.gson.*;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.hw16messageserver.frontend.frontendservice.FrontendServiceImpl;
import ru.otus.hw16messageserver.messageserver.messagesystem.FrontendService;
import ru.otus.hw16messageserver.messageserver.messagesystem.SocketWorker;
import ru.otus.hw16messageserver.messageserver.MessageServer;

import java.util.HashMap;
import java.util.logging.Logger;

@WebSocket
public class ServiceWebSocket {

    private static final Logger logger = Logger.getLogger(ServiceWebSocket.class.getName());
    private static final String JSON_CLASS_NAME = "className";
    private static final String JSON_DATA = "data";

    private Session session;
    private HashMap<String, ServiceWebSocket> clientsMap;
    private String uuid;

    private FrontendServiceImpl frontendService;

    public ServiceWebSocket(HashMap<String, ServiceWebSocket> clientMap, FrontendServiceImpl frontendService) {
        this.clientsMap = clientMap;
        this.frontendService = frontendService;
        //this.socketWorker.init();
        //this.messageServer.messageSystemContext.getMessageSystem().sendDataClient();
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        //this.frontendService.se
        /*this.socketWorker.send(data);*/
        //this.messageServer.messageSystemContext.getMessageSystem().saveData(data);
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        logger.info("Client connected");
        setSession(session);

        uuid = this.frontendService.addClient(this);
        this.frontendService.sendUserList(uuid);
        /*
        clientsMap.put(uuid,this);

        Message message = new MessageClientConnect(
                this.messageServer.messageSystemContext.getDbServiceAddress(),
                this.messageServer.messageSystemContext.getFrontend(),
                uuid);

        Gson gson = new Gson();//createGsonWithFilter();
        //logger.info((MessageToFrontend.class.getName() + " => " + gson.toJson(message)));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_CLASS_NAME, MessageClientConnect.class.getName());
        jsonObject.put(JSON_DATA,gson.toJson(message));*/
        //messageServer.send();

        //messageServer.socketWorkerFontend.send(jsonObject.toString());
        /*
        messageServer.socketWorkerFontend.send(MessageToFrontend.class.getName());
        messageServer.socketWorkerFontend.send(gson.toJson(message));
        */
        //this.socketWorker.send(uuid);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        clientsMap.remove(uuid);
        //this.messageServer.messageSystemContext.getMessageSystem().removeClient(uuid);
        logger.info("Client disconnected");
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return this.session;
    }

    private Gson createGsonWithFilter() {
        return new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                boolean shouldSkipField = fieldAttributes.getDeclaredClass().equals(SocketWorker.class);
                return shouldSkipField;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).create();
    }
}
