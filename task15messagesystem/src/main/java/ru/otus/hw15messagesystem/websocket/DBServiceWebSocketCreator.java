package ru.otus.hw15messagesystem.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import ru.otus.hw15messagesystem.DBHelper;

import ru.otus.hw15messagesystem.hibernate.DBService;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DBServiceWebSocketCreator implements WebSocketCreator {

    private DBService dbService;
    private Set<DBServiceWebSocket> userList;
    public DBServiceWebSocketCreator() {
        this.dbService = DBHelper.createDBService();
        this.userList = Collections.newSetFromMap(new ConcurrentHashMap<>());

    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        DBServiceWebSocket webSocket = new DBServiceWebSocket(this.dbService, this.userList);
        return webSocket;
    }
}
