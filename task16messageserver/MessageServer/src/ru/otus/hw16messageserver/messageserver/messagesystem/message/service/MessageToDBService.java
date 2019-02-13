package ru.otus.hw16messageserver.messageserver.messagesystem.message.service;

import ru.otus.hw15messagesystem.hibernate.DBService;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.Member;
import ru.otus.hw15messagesystem.messagesystem.Message;

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
