package ru.otus.hw16messageserver.server.messageserver.messagesystem.message;

import ru.otus.hw16messageserver.server.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.Member;

public class MessageClientConnect extends Message {

    public MessageClientConnect(Address from, Address to, String data) {
        super(from, to, data);
    }


    @Override
    public void exec(Member sender) {

    }
}
