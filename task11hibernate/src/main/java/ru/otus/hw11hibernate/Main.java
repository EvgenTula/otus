package ru.otus.hw11hibernate;

import ru.otus.hw11hibernate.hibernate.config.ConfigurationHibernate;
import ru.otus.hw11hibernate.hibernate.datasets.AddressDataSetHibernate;
import ru.otus.hw11hibernate.hibernate.datasets.PhoneDataSetHibernate;
import ru.otus.hw11hibernate.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw11hibernate.hibernate.dbservice.DBServiceHibernateImpl;

import ru.otus.hw11hibernate.orm.config.ConfigurationOrm;
import ru.otus.hw11hibernate.orm.dataset.UserDataSetOrm;
import ru.otus.hw11hibernate.orm.dbservice.DBServiceOrmImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        /*orm*/
        DBService dbServiceOrm = new DBServiceOrmImpl(new ConfigurationOrm());

        /*
        List<PhoneDataSetOrm> phonesOrm = new ArrayList<>();
        phonesOrm.add(new PhoneDataSetOrm("123"));
        phonesOrm.add(new PhoneDataSetOrm("456"));
        phonesOrm.add(new PhoneDataSetOrm("789"));
        */
        List<UserDataSetOrm> userListOrm = new ArrayList<>();
        /*
        userListOrm.add(new UserDataSetOrm(1,"user 1", 18, new AddressDataSetOrm("test 1 orm"), phonesOrm));
        userListOrm.add(new UserDataSetOrm(2,"user 2", 19, new AddressDataSetOrm("test 2 orm"), phonesOrm));
        userListOrm.add(new UserDataSetOrm(3,"user 3", 20, new AddressDataSetOrm("test 3 orm"), phonesOrm));
        */
        userListOrm.add(new UserDataSetOrm(1,"user 1", 18));
        userListOrm.add(new UserDataSetOrm(2,"user 2", 19));
        userListOrm.add(new UserDataSetOrm(3,"user 3", 20));
        for (UserDataSetOrm item : userListOrm) {
            dbServiceOrm.save(item);
        }

        userListOrm.clear();
        UserDataSetOrm loadUser = dbServiceOrm.load(1, UserDataSetOrm.class);
        System.out.println(loadUser.toString());

        /*hibernate*/
        DBService dbService = new DBServiceHibernateImpl(new ConfigurationHibernate());
        List<PhoneDataSetHibernate> phones = new ArrayList<>();
        phones.add(new PhoneDataSetHibernate("111"));
        phones.add(new PhoneDataSetHibernate("222"));
        phones.add(new PhoneDataSetHibernate("333"));
        dbService.save(new UserDataSetHibernate(1,"test1",1,new AddressDataSetHibernate("test1 address"),phones));


        phones.clear();
        phones.add(new PhoneDataSetHibernate("444"));
        phones.add(new PhoneDataSetHibernate("555"));
        phones.add(new PhoneDataSetHibernate("666"));
        dbService.save(new UserDataSetHibernate(2,"test2",1,new AddressDataSetHibernate("test2 address"),phones));

        phones.clear();
        phones.add(new PhoneDataSetHibernate("777"));
        phones.add(new PhoneDataSetHibernate("888"));
        phones.add(new PhoneDataSetHibernate("999"));
        dbService.save(new UserDataSetHibernate(3,"test3",1,new AddressDataSetHibernate("test3 address"),phones));

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
