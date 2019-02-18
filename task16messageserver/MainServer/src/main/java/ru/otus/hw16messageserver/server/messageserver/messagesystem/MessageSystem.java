package ru.otus.hw16messageserver.server.messageserver.messagesystem;

public interface MessageSystem {
    void addMember(Member sender);
    void sendMessage(Message message);
    void dispose();
}
