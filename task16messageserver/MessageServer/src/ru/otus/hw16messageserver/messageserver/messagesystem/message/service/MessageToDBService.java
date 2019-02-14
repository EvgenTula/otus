package ru.otus.hw16messageserver.messageserver.messagesystem.message.service;

import ru.otus.hw16messageserver.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.messageserver.messagesystem.Member;
import ru.otus.hw16messageserver.messageserver.messagesystem.Message;

public abstract class MessageToDBService extends Message {

    public MessageToDBService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Member sender) {
        if (sender instanceof DBService) {
            exec((DBService)sender);
        }
    }

    public abstract void exec(DBService dbService);
}
