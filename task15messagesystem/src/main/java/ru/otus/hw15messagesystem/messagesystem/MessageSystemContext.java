package ru.otus.hw15messagesystem.messagesystem;

import org.eclipse.jetty.websocket.api.Session;
import ru.otus.hw15messagesystem.frontend.FrontendService;
import ru.otus.hw15messagesystem.frontend.FrontendServiceImpl;
import java.util.HashMap;
import java.util.Map;

public class MessageSystemContext  {
    private Sender serviceSender;
    private Map<Session, Sender> listFrontendSender;
    private MessageSystem messageSystem;
    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        this.listFrontendSender = new HashMap<>();
    }

    public Sender setDbSender() {
        return this.serviceSender;
    }

    public MessageSystem getMessageSystem() {
        return this.messageSystem;
    }

    public Map<Session, Sender> getListFrontendSender() {
        return listFrontendSender;
    }

    public void addFrontendSender(Session session) {
        FrontendService frontendService = new FrontendServiceImpl(session);
        frontendService.setAddress(new Address(session.getRemoteAddress().toString(),messageSystem));
        this.listFrontendSender.put(session, frontendService);
    }

    public Sender getFrontendSender(Session session) {
        return this.listFrontendSender.get(session);
    }

    public Sender getServiceSender() {
        return serviceSender;
    }
}
