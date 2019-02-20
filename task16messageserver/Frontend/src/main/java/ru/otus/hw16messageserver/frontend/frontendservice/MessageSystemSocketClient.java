package ru.otus.hw16messageserver.frontend.frontendservice;

import ru.otus.hw16messageserver.server.messageserver.messagesystem.MessageSystemSocket;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.Message;
import ru.otus.hw16messageserver.server.websocket.DBServiceWebSocket;

public class MessageSystemSocketClient implements MessageSystemSocket {

    @Override
    public String addClient(DBServiceWebSocket dbServiceWebSocket) {
        return null;
    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void start() {

    }
}
