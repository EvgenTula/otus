package ru.otus.hw11hibernate.hibernate.datasets;

import ru.otus.hw11hibernate.DataSet;

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
    @PrimaryKeyJoinColumn
    private AddressDataSetHibernate address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userDataSet")
    private List<PhoneDataSetHibernate> phoneList;

    public UserDataSetHibernate() { }

    public UserDataSetHibernate(long id, String name, int age, AddressDataSetHibernate address, List<PhoneDataSetHibernate> phones) {
        this.setId(id);
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
        return this.getClass().getName() + " [\nid : " + this.getId() +
                "\nname : " + this.getName() +
                "\nage : " + this.getAge() +
                "\naddress : " + this.getAddress() +
                "\nphone :\n"  + printPhoneList() + "]";
    }

    private String printPhoneList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (PhoneDataSetHibernate phone: this.phoneList) {
            stringBuilder.append(phone.toString()+"\n");
        }
        return stringBuilder.toString();
    }

}
