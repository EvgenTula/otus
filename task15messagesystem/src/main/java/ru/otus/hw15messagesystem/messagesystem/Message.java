package ru.otus.hw15messagesystem.messagesystem;

public abstract class Message {
    private Sender from;
    private Sender to;

    public Message(Sender from, Sender to) {
        this.from = from;
        this.to = to;
    }

    public Sender getFrom() {
        return this.from;
    }

    public Sender getTo() {
        return to;
    }

    public abstract void exec(Sender sender);
}
