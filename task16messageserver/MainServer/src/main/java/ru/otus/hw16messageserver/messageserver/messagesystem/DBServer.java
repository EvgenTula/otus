package ru.otus.hw16messageserver.messageserver.messagesystem;

public interface DBServer extends Member {
    void start();
    int saveData(String data);
    String loadUserByid(int id);
    String loadUserList();
    void sendDataToFrontend(String uuid,String data);
    //void setFrontendAddress(String jsonAdrress);
}
