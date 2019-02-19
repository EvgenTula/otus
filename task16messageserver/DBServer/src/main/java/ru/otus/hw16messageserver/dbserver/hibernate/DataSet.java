package ru.otus.hw16messageserver.dbserver.hibernate;

import javax.persistence.*;


@MappedSuperclass
public class DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
