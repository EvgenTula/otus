package ru.otus.hw15messagesystem.hibernate;

import com.google.gson.annotations.Expose;

import javax.persistence.*;


@MappedSuperclass
public class DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
