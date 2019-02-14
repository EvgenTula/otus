package ru.otus.hw16messageserver.messageserver;

import ru.otus.hw16messageserver.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemContext;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemImpl;

public class MessageServer {
    MessageSystemContext messageSystemContext;
    public MessageServer() {
        messageSystemContext = new MessageSystemContext(new MessageSystemImpl());
        Address dbServerAddress = new Address("dbServiceAddress");
        Address frontendAddress = new Address("frontendAddress");
        messageSystemContext.setDBServiceAddress(dbServerAddress);
        messageSystemContext.setFrontendServiceAddress(frontendAddress);
    }
}
