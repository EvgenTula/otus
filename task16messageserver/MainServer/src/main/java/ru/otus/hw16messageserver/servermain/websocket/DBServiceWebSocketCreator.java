package ru.otus.hw16messageserver.servermain.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import ru.otus.hw16messageserver.servermain.SocketWorker;

import java.util.HashMap;

public class DBServiceWebSocketCreator implements WebSocketCreator {

    private HashMap<String, DBServiceWebSocket> clientsMap;
    private SocketWorker socketWorker;
    public DBServiceWebSocketCreator(SocketWorker socketWorker) {
        clientsMap = new HashMap<>();
        this.socketWorker = socketWorker;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        DBServiceWebSocket webSocket = new DBServiceWebSocket(clientsMap,socketWorker);
        return webSocket;
    }
}
