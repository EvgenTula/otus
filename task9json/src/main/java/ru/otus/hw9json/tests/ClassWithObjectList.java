package ru.otus.hw9json.tests;

import java.util.ArrayList;
import java.util.List;

public class ClassWithObjectList {
    public String name;
    public List<SimpleClass> simpleClassList;
    public ClassWithObjectList(String name) {
        this.name = name;
        simpleClassList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            simpleClassList.add(new SimpleClass(i, "test SimpleClass " + i));
        }
    }
}
