package ru.otus.hw16messageserver.server.messageserver.messagesystem;

public class Address {

    private String host;
    private int port;

    //private SocketWorker socketWorker;

    public Address(String host, int port) {
        this.host = host;
        this.port = port;
        /*
        try {
            socketWorker = new SocketWorker(new Socket(host,port));
            socketWorker.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    public String getHost() {
        return this.host;
    }
    public int getPort() {
        return this.port;
    }
    /*
    public SocketWorker getSocketWorker() {
        return socketWorker;
    }*/
}
