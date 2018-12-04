package ru.otus.hw11hibernate.orm.dataset;

import ru.otus.hw11hibernate.DataSet;

public class PhoneDataSetOrm extends DataSet {

    private String number;
    private UserDataSetOrm userDataSet;

    public PhoneDataSetOrm() { }

    public PhoneDataSetOrm(String number) {
        this.number = number;
    }

    public void setUserDataSet(UserDataSetOrm userDataSet) {
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
