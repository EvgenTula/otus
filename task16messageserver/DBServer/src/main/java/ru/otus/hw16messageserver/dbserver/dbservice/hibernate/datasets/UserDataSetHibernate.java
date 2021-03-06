package ru.otus.hw16messageserver.dbserver.dbservice.hibernate.datasets;

import ru.otus.hw16messageserver.dbserver.dbservice.hibernate.DataSet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Entity
@Table(name = "user")
public class UserDataSetHibernate extends DataSet {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressDataSetHibernate address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userDataSet")
    private List<PhoneDataSetHibernate> phoneList;

    public UserDataSetHibernate() {
        phoneList = new ArrayList<>();
    }

    public UserDataSetHibernate(String name, int age) {
        this.setName(name);
        this.setAge(age);
        this.phoneList = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(AddressDataSetHibernate address) {
        this.address = address;
    }

    public AddressDataSetHibernate getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public void addPhone(PhoneDataSetHibernate phone) {
        phoneList.add(phone);
        phone.setUserDataSet(this);
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " [\nid :\t\t" + this.getId() +
                "\nname :\t\t" + this.getName() +
                "\nage :\t\t" + this.getAge() +
                "\naddress :\t" + this.getAddress().toString() +
                "\nphone :\t\t"  + printPhoneList() + "]";
    }

    public String printPhoneList() {
        StringJoiner result = new StringJoiner(",");
        for (PhoneDataSetHibernate phone: this.phoneList) {
            result.add(phone.toString());
        }
        return result.toString();
    }

    public List<PhoneDataSetHibernate> getPhoneList() {
        return this.phoneList;
    }

}
