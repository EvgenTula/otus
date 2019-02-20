package ru.otus.hw16messageserver.server.messageserver.messagesystem.message;

import ru.otus.hw16messageserver.server.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.Member;

public abstract class MessageToDBService extends Message {

    public MessageToDBService(Address from, Address to, String data) {
        super(from, to, data);
    }


    @Override
    public void exec(Member sender) { }
/*
    @Override
    public void exec(Member sender) {
        if (sender instanceof DBService) {
            exec((DBService)sender);
        }
    }

    public abstract void exec(DBService dbService);
*/
}
