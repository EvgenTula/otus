package ru.otus.hw16messageserver.server.messageserver.messagesystem.message;

import ru.otus.hw16messageserver.server.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.Member;

public class MessageClientConnect extends Message {

    private String data;

    public MessageClientConnect(Address from, Address to, String data) {
        super(from, to);
        this.data = data;
    }

    public String getData() {
        return this.data;
    }

    @Override
    public void exec(Member sender) {

    }
}
