package ru.otus.hw11hibernate.orm.dataset;

import ru.otus.hw11hibernate.DataSet;

public class AddressDataSetOrm extends DataSet {

    private String street;
    public AddressDataSetOrm() { }

    public AddressDataSetOrm(String street) {
        this.setStreet(street);
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " { " + this.street + " }";
    }
}
