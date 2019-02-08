package ru.otus.hw15messagesystem.frontend;

import ru.otus.hw15messagesystem.messagesystem.Sender;
import ru.otus.hw15messagesystem.websocket.DBServiceWebSocket;

import java.io.IOException;

public interface FrontendService extends Sender {
    void sendDataClient(String uuid, String data);
    String addClient(DBServiceWebSocket webSocket);
    void removeClient(String uuid);
    void sendSaveUserMessage(String data);
    void sendGetUsersListMessage(String uuid);
}
