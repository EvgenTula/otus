package ru.otus.hw16messageserver.server.messageserver.messagesystem;

public class Address {

    private int port;

    public Address(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }
}
