package ru.otus.hw16messageserver.messageserver.messagesystem;

public interface DBServer extends Member {
    void start();
    String saveData(String data);
    String loadUserByid(long id);
    String loadUserList();
    void sendDataToFrontend(String uuid,String data);
    void sendDataToFrontend(String data);
    //void setFrontendAddress(String jsonAdrress);
}
