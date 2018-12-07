package ru.otus.hw11hibernate;

import ru.otus.hw11hibernate.hibernate.config.ConfigurationHibernate;
import ru.otus.hw11hibernate.hibernate.datasets.AddressDataSetHibernate;
import ru.otus.hw11hibernate.hibernate.datasets.PhoneDataSetHibernate;
import ru.otus.hw11hibernate.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw11hibernate.hibernate.dbservice.DBServiceHibernateImpl;

import ru.otus.hw11hibernate.orm.config.ConfigurationOrm;
import ru.otus.hw11hibernate.orm.dataset.AddressDataSetOrm;
import ru.otus.hw11hibernate.orm.dataset.PhoneDataSetOrm;
import ru.otus.hw11hibernate.orm.dataset.UserDataSetOrm;
import ru.otus.hw11hibernate.orm.dbservice.DBServiceOrmImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        /*orm*/
        DBService dbServiceOrm = new DBServiceOrmImpl(new ConfigurationOrm());

        List<UserDataSetOrm> userListOrm = new ArrayList<>();

        List<PhoneDataSetOrm> phonesOrm = new ArrayList<>();


        phonesOrm.add(new PhoneDataSetOrm("111"));
        phonesOrm.add(new PhoneDataSetOrm("222"));
        phonesOrm.add(new PhoneDataSetOrm("333"));
        userListOrm.add(new UserDataSetOrm("user 1", 18, new AddressDataSetOrm("test 1 orm"), phonesOrm));

        phonesOrm.clear();
        phonesOrm.add(new PhoneDataSetOrm("444"));
        phonesOrm.add(new PhoneDataSetOrm("555"));
        phonesOrm.add(new PhoneDataSetOrm("666"));
        userListOrm.add(new UserDataSetOrm("user 2", 19, new AddressDataSetOrm("test 2 orm"), phonesOrm));

        phonesOrm.clear();
        phonesOrm.add(new PhoneDataSetOrm("777"));
        phonesOrm.add(new PhoneDataSetOrm("888"));
        phonesOrm.add(new PhoneDataSetOrm("999"));
        userListOrm.add(new UserDataSetOrm("user 3", 20, new AddressDataSetOrm("test 3 orm"), phonesOrm));

        for (UserDataSetOrm item : userListOrm) {
            dbServiceOrm.save(item);
        }

        UserDataSetOrm load = dbServiceOrm.load(1, UserDataSetOrm.class);
        System.out.println(load.toString());

        /*hibernate*/
        DBService dbService = new DBServiceHibernateImpl(new ConfigurationHibernate());
        List<PhoneDataSetHibernate> phones = new ArrayList<>();
        phones.add(new PhoneDataSetHibernate("1111"));
        phones.add(new PhoneDataSetHibernate("2222"));
        phones.add(new PhoneDataSetHibernate("3333"));
        dbService.save(new UserDataSetHibernate("test1 hibernate",1,new AddressDataSetHibernate("test1 address hibernate"),phones));

        phones.clear();
        phones.add(new PhoneDataSetHibernate("4444"));
        phones.add(new PhoneDataSetHibernate("5555"));
        phones.add(new PhoneDataSetHibernate("6666"));
        dbService.save(new UserDataSetHibernate("test2 hibernate",1,new AddressDataSetHibernate("test2 address hibernate"),phones));

        phones.clear();
        phones.add(new PhoneDataSetHibernate("7777"));
        phones.add(new PhoneDataSetHibernate("8888"));
        phones.add(new PhoneDataSetHibernate("9999"));
        dbService.save(new UserDataSetHibernate("test3 hibernate",1,new AddressDataSetHibernate("test3 address hibernate"),phones));

        List<UserDataSetHibernate> userList = ((DBServiceHibernateImpl) dbService).userGetAllList();
        for (UserDataSetHibernate user: userList) {
            System.out.println(user.toString());
        }

        System.out.println(((DBServiceHibernateImpl) dbService).userGetByName("test1 hibernate").toString());

    }
}
