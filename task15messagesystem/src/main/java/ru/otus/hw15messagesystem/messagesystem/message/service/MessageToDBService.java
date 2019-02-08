package ru.otus.hw15messagesystem.messagesystem.message.service;

import ru.otus.hw15messagesystem.hibernate.DBService;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.Message;
import ru.otus.hw15messagesystem.messagesystem.Member;

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
