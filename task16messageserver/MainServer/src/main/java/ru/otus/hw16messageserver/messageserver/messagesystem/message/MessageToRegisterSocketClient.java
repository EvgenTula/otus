package ru.otus.hw16messageserver.messageserver.messagesystem.message;

import ru.otus.hw16messageserver.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.messageserver.messagesystem.Member;

public class MessageToRegisterSocketClient extends Message {

    public MessageToRegisterSocketClient(Address from, Address to, String data) {
        super(from, to, data);
    }

    @Override
    public void exec(Member sender) {
        new UnsupportedOperationException("exec unsupported in MessageToRegisterSocketClient");
    }
}
