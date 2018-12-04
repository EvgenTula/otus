package ru.otus.hw11hibernate.orm.dataset;

import ru.otus.hw11hibernate.DataSet;

import java.util.List;

public class UserDataSetOrm extends DataSet {

    private String name;
    private int age;
    //private AddressDataSetOrm address;
    //private List<PhoneDataSetOrm> phoneList;


    public UserDataSetOrm() {}

    public UserDataSetOrm(long id, String name, int age/*, AddressDataSetOrm address, List<PhoneDataSetOrm> phones*/) {
        this.setId(id);
        this.setName(name);
        this.setAge(age);
        /*
        this.setAddress(address);
        this.phoneList = phones;
        for (PhoneDataSetOrm phone: phones) {
            phone.setUserDataSet(this);
        }
        */
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
/*
    public void setAddress(AddressDataSetOrm address) {
        this.address = address;
    }

    public AddressDataSetOrm getAddress() {
        return address;
    }
*/
    @Override
    public String toString() {
        return this.getClass().getName() + " [\nid : " + this.getId() +
                "\nname : " + this.getName() +
                "\nage : " + this.getAge() /*+
                "\naddress : " + this.getAddress() +
                "\nphone :\n"  + printPhoneList()*/ + "]";
    }
/*
    private String printPhoneList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (PhoneDataSetOrm phone: this.phoneList) {
            stringBuilder.append(phone.toString()+"\n");
        }
        return stringBuilder.toString();
    }*/
}
