package ru.otus.hw16messageserver.messageserver.messagesystem;

public class MessageSystemContext  {

    private Address dbServiceAddress;
    private Address frontendAddress;
    private MessageSystem messageSystem;

    public MessageSystemContext(MessageSystemImpl messageSystem) {
        this.messageSystem = messageSystem;
    }

    public void setDBServiceAddress(Address dbServiceAddress) {
        this.dbServiceAddress = dbServiceAddress;
    }

    public Address getDbServiceAddress() {
        return dbServiceAddress;
    }

    public void setFrontendServiceAddress(Address frontendAddress) {
        this.frontendAddress = frontendAddress;
    }

    public Address getFrontend() {
        return frontendAddress;
    }

    public MessageSystem getMessageSystem() {
        return this.messageSystem;
    }
}
