package ru.otus.hw16messageserver.server.messageserver.messagesystem;

public abstract class Message {
    private Address from;
    private Address to;

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return this.from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(Member sender);
}
