package ru.otus.hw16messageserver.messageserver.messagesystem;

public class Address {

    private String address;

    public Address(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    @Override
    public boolean equals(Object obj) {
        Address objAddress = (Address)obj;
        if (this.getAddress().equals(objAddress.getAddress())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (this.getAddress().hashCode());
    }
}
