package ru.otus.hw15messagesystem.frontend;

import org.eclipse.jetty.websocket.api.Session;
import ru.otus.hw15messagesystem.messagesystem.Address;

public class FrontendServiceImpl implements FrontendService {

    private Address address;
    private Session session;
    public FrontendServiceImpl(Session session) {
        this.session = session;
        this.address = new Address(this.session.getRemoteAddress().toString());
    }

    @Override
    public Address getAddress() {
        return this.address;
    }
}
