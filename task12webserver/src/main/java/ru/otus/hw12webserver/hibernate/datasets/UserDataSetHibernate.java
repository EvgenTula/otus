package ru.otus.hw12webserver.hibernate.datasets;

import ru.otus.hw12webserver.hibernate.DataSet;

import javax.persistence.*;
import java.util.List;

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

    public UserDataSetHibernate() { }

    public UserDataSetHibernate(String name, int age, AddressDataSetHibernate address, List<PhoneDataSetHibernate> phones) {
        this.setId(-1);
        this.setName(name);
        this.setAge(age);
        this.setAddress(address);
        this.phoneList = phones;
        for (PhoneDataSetHibernate phone: phones) {
            phone.setUserDataSet(this);
        }
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

    private String printPhoneList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (PhoneDataSetHibernate phone: this.phoneList) {
            stringBuilder.append(phone.toString()+" ");
        }
        return stringBuilder.toString();
    }

}
