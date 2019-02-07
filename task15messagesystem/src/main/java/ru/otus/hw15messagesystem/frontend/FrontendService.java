package ru.otus.hw15messagesystem.frontend;

import ru.otus.hw15messagesystem.messagesystem.Sender;
import ru.otus.hw15messagesystem.websocket.DBServiceWebSocket;

public interface FrontendService extends Sender {
    void sendDataAllClient(String data);
    void addClient(DBServiceWebSocket webSocket);
    void removeClient(DBServiceWebSocket webSocket);
}
