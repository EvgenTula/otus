package ru.otus.hw15messagesystem.messagesystem;

public abstract class Message {
    private Address from;
    private Address to;
    private String data;

    public Message(Address from, Address to, String data) {
        this.from = from;
        this.to = to;
        this.data = data;
    }

    public Address getFrom() {
        return this.from;
    }

    public Address getTo() {
        return to;
    }

    public String getData() {
        return data;
    }

    public abstract void exec(Address address);
}
