package ru.otus.hw11hibernate.datasets;

import javax.persistence.*;

@Entity
@Table(name =  "phone")
public class PhoneDataSet extends DataSet {
    @Column(name = "number")
    private String number;

    @ManyToOne
    private UserDataSet userDataSet;

    public PhoneDataSet() { }

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public void setUserDataSet(UserDataSet userDataSet) {
        this.userDataSet = userDataSet;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " { " + this.getNumber() + " }";
    }
}
