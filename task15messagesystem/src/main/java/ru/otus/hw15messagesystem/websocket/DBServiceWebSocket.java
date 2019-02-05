package ru.otus.hw15messagesystem.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.hw15messagesystem.messagesystem.MessageSystem;
import ru.otus.hw15messagesystem.messagesystem.MessageSystemContext;
import ru.otus.hw15messagesystem.messagesystem.message.MessageSaveData;

import java.util.Set;

@WebSocket
public class DBServiceWebSocket {

    private MessageSystemContext messageSystemContext;
    private MessageSystem messageSystem;
    private Set<DBServiceWebSocket> userList;
    private Session session;


    public DBServiceWebSocket(MessageSystemContext messageSystemContext, Set<DBServiceWebSocket> userList) {
        this.messageSystemContext = messageSystemContext;
        this.userList = userList;
        this.messageSystem = messageSystemContext.getMessageSystem();
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        messageSystem.sendMessage(new MessageSaveData(messageSystemContext.getServiceSender(), messageSystemContext.getFrontendSender(session), data));
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        userList.add(this);
        messageSystemContext.addFrontendSender(session);
        this.messageSystem.addAddress(messageSystemContext.getFrontendSender(session));
        this.messageSystem.sendMessage(new MessageSaveData(messageSystemContext.getServiceSender(), messageSystemContext.getFrontendSender(session),""));
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        this.messageSystem.removeAddress(messageSystemContext.getFrontendSender(session));
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
