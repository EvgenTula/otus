package ru.otus.hw16messageserver.dbserver.hibernate.datasets;

import ru.otus.hw16messageserver.dbserver.hibernate.DataSet;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressDataSetHibernate extends DataSet {

    @Column(name = "street")
    private String street;

    public AddressDataSetHibernate() { }

    public AddressDataSetHibernate(String street) {
        this.setStreet(street);
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return this.street;
    }
}
