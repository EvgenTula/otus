package ru.otus.hw16messageserver.servermain.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.hw16messageserver.servermain.SocketWorker;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebSocket
public class DBServiceWebSocket {

    private static final Logger logger = Logger.getLogger(DBServiceWebSocket.class.getName());

    private Session session;
    private HashMap<String, DBServiceWebSocket> clientsMap;
    private String uuid;

    private SocketWorker socketWorker;

    public DBServiceWebSocket(HashMap<String, DBServiceWebSocket> clientMap, SocketWorker socketWorker) {
        this.clientsMap = clientMap;
        this.socketWorker = socketWorker;
        this.socketWorker.init();
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        this.socketWorker.send(data);
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        logger.log(Level.INFO, "Client connected");
        setSession(session);
        UUID randomUUID = UUID.randomUUID();
        uuid = randomUUID.toString();
        clientsMap.put(uuid,this);
        this.socketWorker.send(uuid);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        clientsMap.remove(uuid);
        logger.log(Level.INFO, "Client disconnected");
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return this.session;
    }


}
