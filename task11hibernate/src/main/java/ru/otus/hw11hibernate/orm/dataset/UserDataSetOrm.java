package ru.otus.hw11hibernate.orm.dataset;

import ru.otus.hw11hibernate.DataSet;

import java.util.ArrayList;
import java.util.List;

public class UserDataSetOrm extends DataSet {

    private String name;
    private int age;
    private AddressDataSetOrm address_id;
    private List<PhoneDataSetOrm> phoneList;


    public UserDataSetOrm() {
        this.phoneList = new ArrayList<>();
    }

    public UserDataSetOrm(String name, int age, AddressDataSetOrm address, List<PhoneDataSetOrm> phones) {
        this.setId(-1);
        this.setName(name);
        this.setAge(age);
        this.setAddress_id(address);
        this.phoneList = phones;
        for (PhoneDataSetOrm phone: phones) {
            phone.setUserDataSet(this);
        }
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

    public void setAddress_id(AddressDataSetOrm address_id) {
        this.address_id = address_id;
    }

    public AddressDataSetOrm getAddress_id() {
        return address_id;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " [\nid :\t\t" + this.getId() +
                "\nname :\t\t" + this.getName() +
                "\nage :\t\t" + this.getAge() +
                "\naddress_id : " + this.getAddress_id() +
                "\nphone :\n"  + printPhoneList() + " ]";
    }

    private String printPhoneList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (PhoneDataSetOrm phone: this.phoneList) {
            stringBuilder.append(phone.toString()+"\n");
        }
        return stringBuilder.toString();
    }
}
