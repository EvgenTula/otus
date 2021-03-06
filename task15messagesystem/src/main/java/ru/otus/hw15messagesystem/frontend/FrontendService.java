package ru.otus.hw15messagesystem.frontend;

import ru.otus.hw15messagesystem.messagesystem.Member;
import ru.otus.hw15messagesystem.websocket.DBServiceWebSocket;

public interface FrontendService extends Member {
    void sendDataClient(String uuid, String data);
    String addClient(DBServiceWebSocket webSocket);
    void removeClient(String uuid);
    void sendSaveUserMessage(String data);
    void sendGetUsersListMessage(String uuid);
}
