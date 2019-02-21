package ru.otus.hw16messageserver.server.messageserver.messagesystem;

public class Address {

    private String host;
    private int port;

    public Address(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return this.host;
    }
    public int getPort() {
        return this.port;
    }

    @Override
    public boolean equals(Object obj) {
        Address objAddress = (Address)obj;
        if ((this.getPort() == objAddress.getPort()) && (this.getHost().equals(objAddress.getHost()))) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (this.host.hashCode() + this.getPort());
    }
}
