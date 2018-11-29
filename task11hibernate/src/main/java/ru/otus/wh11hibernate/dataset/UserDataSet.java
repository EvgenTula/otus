package ru.otus.wh11hibernate.dataset;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private AddressDataSet address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userDataSet")
    private List<PhoneDataSet> phoneList;

    public UserDataSet() { }

    public UserDataSet(long id, String name, int age, AddressDataSet address, List<PhoneDataSet> phones) {
        this.setId(id);
        this.setName(name);
        this.setAge(age);
        this.setAddress(address);
        this.phoneList = phones;
        for (PhoneDataSet phone: phones) {
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

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public void addPhone(PhoneDataSet phone) {
        phoneList.add(phone);
        phone.setUserDataSet(this);
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " {\nid : " + this.getId() +
                "\nname : " + this.getName() +
                "\nage : " + this.getAge() +
                "\naddress : " + this.getAddress() + printPhoneList();
    }

    private String printPhoneList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (PhoneDataSet phone: this.phoneList) {
            stringBuilder.append(phone.toString()+"\n");
        }
        return stringBuilder.toString();
    }

}
