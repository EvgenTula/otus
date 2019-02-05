package ru.otus.hw15messagesystem.messagesystem;

public abstract class Message {
    private Sender from;
    private Sender to;
    private String data;

    public Message(Sender from, Sender to, String data) {
        this.from = from;
        this.to = to;
        this.data = data;
    }

    public Sender getFrom() {
        return this.from;
    }

    public Sender getTo() {
        return to;
    }

    public String getData() {
        return data;
    }

    public abstract void exec(Sender sender);
}
