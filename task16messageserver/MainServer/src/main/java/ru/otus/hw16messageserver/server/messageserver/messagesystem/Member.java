package ru.otus.hw16messageserver.server.messageserver.messagesystem;

public interface Member {
    Address getAddress();
    MessageSystemSocketServer getMessageSystem();
}
