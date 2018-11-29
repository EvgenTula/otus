package ru.otus.wh11hibernate;

import ru.otus.wh11hibernate.dataset.AddressDataSet;
import ru.otus.wh11hibernate.dataset.DataSet;
import ru.otus.wh11hibernate.dataset.PhoneDataSet;
import ru.otus.wh11hibernate.dataset.UserDataSet;

import java.util.ArrayList;
import java.util.List;

/*

Домашнее задание
ДЗ-11: Hibernate ORM
На основе предыдущего ДЗ (myORM):
1. Оформить решение в виде DBService (interface DBService, class DBServiceImpl, UsersDAO, UsersDataSet, Executor)
2. Не меняя интерфейс DBSerivice сделать DBServiceHibernateImpl на Hibernate.
3. Добавить в UsersDataSet поля:
адресс (OneToOne)
class AddressDataSet{
private String street;
}
и телефон* (OneToMany)
class PhoneDataSet{
private String number;
}
Добавить соответствущие датасеты и DAO.

4.** Поддержать работу из пункта (3) в myORM

*/
public class Main {
    public static void main(String[] args) {
        DBService dbService = new DBServiceHibernateImpl();
        List<PhoneDataSet> phones = new ArrayList<>();
        phones.add(new PhoneDataSet("123"));
        phones.add(new PhoneDataSet("456"));
        phones.add(new PhoneDataSet("789"));
        dbService.save(new UserDataSet(1,"test",1,new AddressDataSet("test address"),phones));
        System.out.println(dbService.load(1, UserDataSet.class).toString());

        //dbService.
    }
}
