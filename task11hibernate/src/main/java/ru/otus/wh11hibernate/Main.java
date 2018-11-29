package ru.otus.wh11hibernate;

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
    }
}
