package ru.otus.hw15messagesystem.frontend;

import org.eclipse.jetty.websocket.api.Session;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.Sender;

public class Frontend implements Sender {

    private Address address;
    private Session session;
    public Frontend (Session session, Address address) {
        this.session = session;
        this.address = address;
    }

    @Override
    public Address getAddress() {
        return this.address;
    }
}
