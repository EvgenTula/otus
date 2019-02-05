package ru.otus.hw15messagesystem.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.hw15messagesystem.frontend.FrontendService;
import ru.otus.hw15messagesystem.frontend.FrontendServiceImpl;
import ru.otus.hw15messagesystem.hibernate.DBService;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.MessageSystem;
import ru.otus.hw15messagesystem.messagesystem.message.MessageSaveData;

import java.util.Set;

@WebSocket
public class DBServiceWebSocket {

    private MessageSystem messageSystem;
    private DBService dbService;
    private FrontendService frontendService;

    public DBServiceWebSocket(MessageSystem messageSystem,DBService dbService, Set<DBServiceWebSocket> userList) {
        this.messageSystem = messageSystem;
        this.dbService = dbService;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        this.messageSystem.sendMessage(new MessageSaveData(this.dbService, frontendService, data));
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        frontendService = new FrontendServiceImpl(session);
        frontendService.setAddress(new Address(session.getRemoteAddress().toString(),this.messageSystem));
        this.messageSystem.addAddress(frontendService);
        this.messageSystem.sendMessage(new MessageSaveData(this.dbService, frontendService,""));
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        this.messageSystem.removeAddress(this.frontendService);
    }
}
