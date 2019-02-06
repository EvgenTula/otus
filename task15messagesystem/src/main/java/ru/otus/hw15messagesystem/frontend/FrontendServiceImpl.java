package ru.otus.hw15messagesystem.frontend;

import org.eclipse.jetty.websocket.api.Session;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.MessageSystem;
import ru.otus.hw15messagesystem.messagesystem.MessageSystemContext;

import java.io.IOException;

public class FrontendServiceImpl implements FrontendService {

    private Address address;
    private MessageSystemContext messageSystemContext;

    public FrontendServiceImpl(MessageSystemContext messageSystemContext,Address address) {
        this.messageSystemContext = messageSystemContext;
        this.address = address;
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
        for (Session item : messageSystemContext.getListFrontendSender().keySet()) {
            try {
                item.getRemote().sendString(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
