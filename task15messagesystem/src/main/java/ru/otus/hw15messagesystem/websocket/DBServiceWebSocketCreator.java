package ru.otus.hw15messagesystem.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import ru.otus.hw15messagesystem.DBHelper;

import ru.otus.hw15messagesystem.frontend.FrontendService;
import ru.otus.hw15messagesystem.frontend.FrontendServiceImpl;
import ru.otus.hw15messagesystem.hibernate.DBService;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.MessageSystem;
import ru.otus.hw15messagesystem.messagesystem.MessageSystemContext;

public class DBServiceWebSocketCreator implements WebSocketCreator {

    private MessageSystem messageSystem;
    private MessageSystemContext messageSystemContext;
    private DBService dbService;
    private FrontendService frontendService;
    public DBServiceWebSocketCreator() {
        this.messageSystem = new MessageSystem();
        this.messageSystemContext = new MessageSystemContext(this.messageSystem);
        this.dbService = DBHelper.createDBService(this.messageSystemContext,new Address("dbService"));
        this.frontendService = new FrontendServiceImpl(this.messageSystemContext,new Address("frontendService"));
        this.messageSystemContext.setService(dbService);
        this.messageSystemContext.setFrontend(frontendService);
        this.messageSystem.addAddress(dbService);
        this.messageSystem.addAddress(frontendService);
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        DBServiceWebSocket webSocket = new DBServiceWebSocket(this.messageSystemContext);
        return webSocket;
    }
}
