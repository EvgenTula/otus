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

        UserDataSetOrm loadUser = dbServiceOrm.load(1, UserDataSetOrm.class);
        System.out.println(loadUser.toString());

        /*hibernate*/
        DBService dbService = new DBServiceHibernateImpl(new ConfigurationHibernate());
        List<PhoneDataSetHibernate> phones = new ArrayList<>();
        phones.add(new PhoneDataSetHibernate("111"));
        phones.add(new PhoneDataSetHibernate("222"));
        phones.add(new PhoneDataSetHibernate("333"));
        dbService.save(new UserDataSetHibernate("test1",1,new AddressDataSetHibernate("test1 address"),phones));

        phones.clear();
        phones.add(new PhoneDataSetHibernate("444"));
        phones.add(new PhoneDataSetHibernate("555"));
        phones.add(new PhoneDataSetHibernate("666"));
        dbService.save(new UserDataSetHibernate("test2",1,new AddressDataSetHibernate("test2 address"),phones));

        phones.clear();
        phones.add(new PhoneDataSetHibernate("777"));
        phones.add(new PhoneDataSetHibernate("888"));
        phones.add(new PhoneDataSetHibernate("999"));
        dbService.save(new UserDataSetHibernate("test3",1,new AddressDataSetHibernate("test3 address"),phones));

        List<UserDataSetHibernate> userList = ((DBServiceHibernateImpl) dbService).userGetAllList();
        for (UserDataSetHibernate user: userList) {
            System.out.println(user.toString());
        }

        System.out.println(((DBServiceHibernateImpl) dbService).userGetByName("test1").toString());
    }
}
