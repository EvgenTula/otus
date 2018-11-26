package ru.otus.hw10orm.dataset;

public class UserDataSet extends DataSet {
    //private HashMap<String, String>

    public String name;
    public int age;
    public UserDataSet() {}

    public UserDataSet(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "id : " + this.id +
                "\nname : " + this.name +
                "\nage : " + this.age;
    }

}
