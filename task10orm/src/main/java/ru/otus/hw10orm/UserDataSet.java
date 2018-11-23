package ru.otus.hw10orm;

public class UserDataSet extends DataSet {
    //private HashMap<String, String>

    public String name;
    public int age;

    public UserDataSet(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "id : " + this.id +
                "name : " + this.name +
                "age : " + this.age;
    }

}
