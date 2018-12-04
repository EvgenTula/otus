package ru.otus.hw11hibernate.orm.dataset;

import ru.otus.hw11hibernate.DataSet;

public class UserDataSetOrm extends DataSet {

    private String name;
    private int age;
    public UserDataSetOrm() {}

    public UserDataSetOrm(long id, String name, int age) {
        this.setId(id);
        this.setName(name);
        this.setAge(age);
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

    @Override
    public String toString() {
        return "id : " + this.getId() +
                "\nname : " + this.getName() +
                "\nage : " + this.getAge();
    }

}
