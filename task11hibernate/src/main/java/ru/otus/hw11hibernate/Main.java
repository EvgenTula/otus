package ru.otus.hw11hibernate;

import ru.otus.hw11hibernate.datasets.AddressDataSet;
import ru.otus.hw11hibernate.datasets.PhoneDataSet;
import ru.otus.hw11hibernate.datasets.UserDataSet;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DBService dbService = new DBServiceHibernateImpl();
        List<PhoneDataSet> phones = new ArrayList<>();
        phones.add(new PhoneDataSet("123"));
        phones.add(new PhoneDataSet("456"));
        phones.add(new PhoneDataSet("789"));
        dbService.save(new UserDataSet(1,"test1",1,new AddressDataSet("test1 address"),phones));
        dbService.save(new UserDataSet(2,"test2",1,new AddressDataSet("test2 address"),phones));
        dbService.save(new UserDataSet(3,"test3",1,new AddressDataSet("test3 address"),phones));
        dbService.save(new UserDataSet(4,"test4",1,new AddressDataSet("test4 address"),phones));
        System.out.println(dbService.load(1, UserDataSet.class).toString());
        System.out.println(dbService.load(1, PhoneDataSet.class).toString());
        System.out.println(dbService.load(1, AddressDataSet.class).toString());

        System.out.println(((DBServiceHibernateImpl) dbService).userGetByName("test1").toString());
        List<UserDataSet> userList = ((DBServiceHibernateImpl) dbService).userGetAllList();
        for (UserDataSet user: userList) {
            System.out.println(user.toString());
        }
    }
}
