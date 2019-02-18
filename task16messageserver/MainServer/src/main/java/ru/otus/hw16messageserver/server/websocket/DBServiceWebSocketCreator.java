package ru.otus.hw16messageserver.server.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import ru.otus.hw16messageserver.server.SocketWorker;
import ru.otus.hw16messageserver.server.messageserver.MessageServer;

import java.util.HashMap;

public class DBServiceWebSocketCreator implements WebSocketCreator {

    private HashMap<String, DBServiceWebSocket> clientsMap;
    private MessageServer messageServer;
    public DBServiceWebSocketCreator(MessageServer messageServer) {
        clientsMap = new HashMap<>();
        this.messageServer = messageServer;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        DBServiceWebSocket webSocket = new DBServiceWebSocket(clientsMap,messageServer);
        return webSocket;
    }
}
