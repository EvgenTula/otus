package ru.otus.hw16messageserver.frontend.frontendservice;

//import ru.otus.hw16messageserver.messageserver.messagesystem.Member;

import java.io.IOException;

public interface FrontendService {//extends Member {
    void start() throws IOException;

    //void sendDataClient(String uuid, String data);
    //String addClient(DBServiceWebSocket webSocket);
    void addClient(String uuid);
    //void removeClient(String uuid);
    //void sendSaveUserMessage(String data);
    //void sendGetUsersListMessage(String uuid);
}
