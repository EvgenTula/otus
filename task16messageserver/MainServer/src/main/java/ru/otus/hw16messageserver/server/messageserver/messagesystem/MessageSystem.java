package ru.otus.hw16messageserver.server.messageserver.messagesystem;

import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.Message;

public interface MessageSystem {

    //void addMember(Member sender);
    void sendMessage(Message message);
    //void dispose();
    void start();
}
