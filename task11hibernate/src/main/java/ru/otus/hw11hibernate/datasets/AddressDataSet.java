package ru.otus.hw11hibernate.datasets;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressDataSet extends DataSet {

    @Column(name = "street")
    private String street;

    public AddressDataSet() { }

    public AddressDataSet(String street) {
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
