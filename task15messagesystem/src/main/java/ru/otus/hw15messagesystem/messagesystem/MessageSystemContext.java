package ru.otus.hw15messagesystem.messagesystem;

import ru.otus.hw15messagesystem.frontend.FrontendService;
import ru.otus.hw15messagesystem.hibernate.DBService;

public class MessageSystemContext  {

    private DBService serviceSender;
    private FrontendService frontendSender;
    private MessageSystem messageSystem;

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public void setService(DBService serviceSender) {
        this.serviceSender = serviceSender;
    }

    public DBService getDBService() {
        return serviceSender;
    }

    public void setFrontend(FrontendService frontendSender) {
        this.frontendSender = frontendSender;
    }

    public FrontendService getFrontend() {
        return frontendSender;
    }

    public MessageSystem getMessageSystem() {
        return this.messageSystem;
    }
}
