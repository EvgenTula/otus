package ru.otus.hw15messagesystem.frontend;

import org.eclipse.jetty.websocket.api.Session;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.MessageSystem;
import ru.otus.hw15messagesystem.messagesystem.MessageSystemContext;

public class FrontendServiceImpl implements FrontendService {

    private Address address;
    private Session session;
    private MessageSystemContext messageSystemContext;
    public FrontendServiceImpl(Session session) {
        this.session = session;
        //this.address = new Address(this.session.getRemoteAddress().toString());
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    @Override
    public void setAddress(Address address) {
        this.address = address;
    }

    public Session getSession() {
        return this.session;
    }
}
