package ru.otus.hw10orm;

public class UserDataSet extends DataSet {

    private static final String TABLE_NAME = "user";

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

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
