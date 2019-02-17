package ru.otus.hw16messageserver.servermain.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.logging.Level;
import java.util.logging.Logger;

@WebSocket
public class DBServiceWebSocket {

    private static final Logger logger = Logger.getLogger(DBServiceWebSocket.class.getName());

    private Session session;
    //private FrontendService frontendService;
    private String uuid;

    public DBServiceWebSocket(/*FrontendService frontendService*/) {
        //this.frontendService = frontendService;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        //frontendService.sendSaveUserMessage(data);
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        logger.log(Level.INFO, "Client connected");
        setSession(session);
        //uuid = frontendService.addClient(this);
        //frontendService.sendGetUsersListMessage(uuid);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        //frontendService.removeClient(uuid);
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return this.session;
    }
}
