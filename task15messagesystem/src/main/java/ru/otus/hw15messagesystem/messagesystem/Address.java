package ru.otus.hw15messagesystem.messagesystem;

public class Address {

    private String id;
    private MessageSystem messageSystem;

    public Address(String id, MessageSystem messageSystem) {
        this.id = id;
        this.messageSystem = messageSystem;
    }

    public String getId() {
        return this.id;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }
}
