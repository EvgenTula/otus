package ru.otus.hw15messagesystem.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.hw15messagesystem.messagesystem.MessageSystem;
import ru.otus.hw15messagesystem.messagesystem.MessageSystemContext;
import ru.otus.hw15messagesystem.messagesystem.message.service.MessageLoadData;
import ru.otus.hw15messagesystem.messagesystem.message.service.MessageSaveData;

@WebSocket
public class DBServiceWebSocket {

    private MessageSystemContext messageSystemContext;
    private MessageSystem messageSystem;
    private Session session;

    public DBServiceWebSocket(MessageSystemContext messageSystemContext) {
        this.messageSystemContext = messageSystemContext;
        this.messageSystem = messageSystemContext.getMessageSystem();
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        messageSystem.sendMessage(new MessageSaveData(messageSystemContext.getDBService(), messageSystemContext.getFrontend(), data));
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
        messageSystemContext.getFrontend().addClient(this);
        this.messageSystem.sendMessage(new MessageLoadData(messageSystemContext.getDBService(), messageSystemContext.getFrontend(), this));
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        messageSystemContext.getFrontend().removeClient(this);
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return this.session;
    }
}
