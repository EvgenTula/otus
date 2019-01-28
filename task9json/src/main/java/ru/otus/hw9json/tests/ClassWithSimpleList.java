package ru.otus.hw9json.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClassWithSimpleList {
    public String name;
    public List<Integer> listInteger;
    public ClassWithSimpleList(String name, int bound) {
        this.name = name;
        listInteger = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listInteger.add(new Random().nextInt(bound));
        }
    }
}
