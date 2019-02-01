package ru.otus.hw15messagesystem.hibernate.datasets;

import com.google.gson.annotations.Expose;
import ru.otus.hw15messagesystem.hibernate.DataSet;

import javax.persistence.*;

@Entity
@Table(name =  "phone")
public class PhoneDataSetHibernate extends DataSet {
    @Column(name = "number")
    @Expose
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
        return this.getNumber();
    }
}
