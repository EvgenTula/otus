package ru.otus.hw16messageserver.messageserver.messagesystem;

public class Address {

    private int port;

    public Address(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }
}
