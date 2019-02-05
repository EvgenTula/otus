package ru.otus.hw15messagesystem.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import ru.otus.hw15messagesystem.DBHelper;

import ru.otus.hw15messagesystem.hibernate.DBService;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.MessageSystem;
import ru.otus.hw15messagesystem.messagesystem.MessageSystemContext;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DBServiceWebSocketCreator implements WebSocketCreator {

    private MessageSystem messageSystem;
    private MessageSystemContext messageSystemContext;
    private DBService dbService;
    private Set<DBServiceWebSocket> userList;
    public DBServiceWebSocketCreator() {
        this.messageSystem = new MessageSystem();
        this.messageSystemContext = new MessageSystemContext(this.messageSystem);
        this.dbService = DBHelper.createDBService();
        this.dbService.setAddress(new Address("dbService", this.messageSystem));
        this.messageSystem.addAddress(dbService);

        this.userList = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        DBServiceWebSocket webSocket = new DBServiceWebSocket(this.messageSystemContext, this.userList);
        return webSocket;
    }
}
