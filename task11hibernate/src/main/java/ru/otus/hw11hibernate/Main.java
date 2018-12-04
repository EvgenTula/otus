package ru.otus.hw11hibernate;

import ru.otus.hw11hibernate.hibernate.datasets.AddressDataSetHibernate;
import ru.otus.hw11hibernate.hibernate.datasets.PhoneDataSetHibernate;
import ru.otus.hw11hibernate.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw11hibernate.hibernate.dbservice.DBServiceHibernateImpl;

import ru.otus.hw11hibernate.orm.dataset.UserDataSetOrm;
import ru.otus.hw11hibernate.orm.dbservice.DBServiceImpl;
import ru.otus.hw11hibernate.orm.dbservice.DataSetConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        /*orm*/
        List<DataSetConfiguration> configurationList = new ArrayList<>();
        configurationList.add(new DataSetConfiguration(UserDataSetOrm.class, "user"));
        DBService dbServiceOrm = new DBServiceImpl(configurationList);

        List<UserDataSetOrm> newUserList = new ArrayList<>();
        newUserList.add(new UserDataSetOrm(1,"user 1", 18));
        newUserList.add(new UserDataSetOrm(2,"user 2", 19));
        newUserList.add(new UserDataSetOrm(3,"user 3", 20));

        for (UserDataSetOrm item : newUserList) {
            dbServiceOrm.save(item);
        }

        newUserList.clear();
        UserDataSetOrm loadUser = dbServiceOrm.load(1, UserDataSetOrm.class);
        System.out.println(loadUser.toString());

        /*hibernate*/
        DBService dbService = new DBServiceHibernateImpl();
        List<PhoneDataSetHibernate> phones = new ArrayList<>();
        phones.add(new PhoneDataSetHibernate("123"));
        phones.add(new PhoneDataSetHibernate("456"));
        phones.add(new PhoneDataSetHibernate("789"));
        dbService.save(new UserDataSetHibernate(1,"test1",1,new AddressDataSetHibernate("test1 address"),phones));
        dbService.save(new UserDataSetHibernate(2,"test2",1,new AddressDataSetHibernate("test2 address"),phones));
        dbService.save(new UserDataSetHibernate(3,"test3",1,new AddressDataSetHibernate("test3 address"),phones));
        dbService.save(new UserDataSetHibernate(4,"test4",1,new AddressDataSetHibernate("test4 address"),phones));
        System.out.println(dbService.load(1, UserDataSetHibernate.class).toString());
        System.out.println(dbService.load(1, PhoneDataSetHibernate.class).toString());
        System.out.println(dbService.load(1, AddressDataSetHibernate.class).toString());

        System.out.println(((DBServiceHibernateImpl) dbService).userGetByName("test1").toString());
        List<UserDataSetHibernate> userList = ((DBServiceHibernateImpl) dbService).userGetAllList();
        for (UserDataSetHibernate user: userList) {
            System.out.println(user.toString());
        }
    }
}
