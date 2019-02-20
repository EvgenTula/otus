package ru.otus.hw16messageserver.server.messageserver.messagesystem;

import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.Message;
import ru.otus.hw16messageserver.server.websocket.DBServiceWebSocket;

public interface MessageSystemSocket {

    //void addMember(Member sender);
    String addClient(DBServiceWebSocket webSocket);
    void sendMessage(Message message);
    //void dispose();
    void start();
}
