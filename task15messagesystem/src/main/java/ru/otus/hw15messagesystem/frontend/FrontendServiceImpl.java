package ru.otus.hw15messagesystem.frontend;

import org.eclipse.jetty.websocket.api.Session;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.Message;
import ru.otus.hw15messagesystem.messagesystem.MessageSystem;
import ru.otus.hw15messagesystem.messagesystem.MessageSystemContext;
import ru.otus.hw15messagesystem.websocket.DBServiceWebSocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FrontendServiceImpl implements FrontendService {

    private Address address;
    private MessageSystemContext messageSystemContext;
    private Set<DBServiceWebSocket> listClient;

    public FrontendServiceImpl(MessageSystemContext messageSystemContext,Address address) {
        this.messageSystemContext = messageSystemContext;
        this.address = address;
        this.listClient = new HashSet<>();
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    @Override
    public MessageSystem getMessageSystem() {
        return messageSystemContext.getMessageSystem();
    }

    public void sendDataAllClient(String data) {
        for (DBServiceWebSocket item : listClient) {
            try {
                item.getSession().getRemote().sendString(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addClient(DBServiceWebSocket webSocket) {
        this.listClient.add(webSocket);
    }

    @Override
    public void removeClient(DBServiceWebSocket webSocket) {
        this.listClient.remove(webSocket);
    }
}
