package ru.otus.hw16messageserver.server.messageserver.messagesystem;

public class MessageSystemContext  {

    private Address dbServiceAddress;
    private Address frontendAddress;
    private MessageSystem messageSystem;

    public MessageSystemContext(MessageSystemImpl messageSystem, Address dbServiceAddress, Address frontendAddress) {
        this.messageSystem = messageSystem;
        this.dbServiceAddress = dbServiceAddress;
        this.frontendAddress = frontendAddress;
        this.messageSystem.start();


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
