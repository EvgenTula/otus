package ru.otus.hw11hibernate.hibernate.datasets;

import ru.otus.hw11hibernate.DataSet;

import javax.persistence.*;

@Entity
@Table(name =  "phone")
public class PhoneDataSetHibernate extends DataSet {
    @Column(name = "number")
    private String number;

    @ManyToOne
    private UserDataSetHibernate userDataSet;

    public PhoneDataSetHibernate() { }

    public PhoneDataSetHibernate(String number) {
        this.number = number;
    }

    public void setUserDataSet(UserDataSetHibernate userDataSet) {
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
        return "{ " + this.getNumber() + " }";
    }
}
