package ru.otus.hw9json;

import ru.otus.hw9json.tests.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {

    public static void main(String[] args) {

        Gson gson = new Gson();

        System.out.println("gson : " + gson.toJson("text"));
        System.out.println("ru.otus.hw9json.SimpleJson : " + SimpleJson.toJSON("text"));

        System.out.println("gson : " + gson.toJson(""));
        System.out.println("ru.otus.hw9json.SimpleJson : " + SimpleJson.toJSON(""));

        System.out.println("gson : " + gson.toJson(1));
        System.out.println("ru.otus.hw9json.SimpleJson : " + SimpleJson.toJSON(1));

        System.out.println("gson : " + gson.toJson('1'));
        System.out.println("ru.otus.hw9json.SimpleJson : " + SimpleJson.toJSON('1'));

        System.out.println("gson : " + gson.toJson(false));
        System.out.println("ru.otus.hw9json.SimpleJson : " + SimpleJson.toJSON(false));

        System.out.println("gson : " + gson.toJson(null));
        System.out.println("ru.otus.hw9json.SimpleJson : " + SimpleJson.toJSON(null));

        System.out.println("gson : " + gson.toJson(new Object()));
        System.out.println("ru.otus.hw9json.SimpleJson : " + SimpleJson.toJSON(new Object()));

        System.out.println("gson : " + gson.toJson(new Object[10]));
        System.out.println("ru.otus.hw9json.SimpleJson : " + SimpleJson.toJSON(new Object[10]));

        List<Integer> listInteger = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listInteger.add(new Random().nextInt(10));
        }
        System.out.println("gson : " + gson.toJson(listInteger));
        System.out.println("ru.otus.hw9json.SimpleJson : " + SimpleJson.toJSON(listInteger));


        SimpleClass simpleClass = new SimpleClass(1,"test SimpleClass");
        System.out.println("gson : " + gson.toJson(simpleClass));
        System.out.println("ru.otus.hw9json.SimpleJson : " + SimpleJson.toJSON(simpleClass));

        ExtendsSimpleClass extendsSimpleClass = new ExtendsSimpleClass(1,"test ExtendsSimpleClass", 100);
        System.out.println("gson : " + gson.toJson(extendsSimpleClass));
        System.out.println("ru.otus.hw9json.SimpleJson : " + SimpleJson.toJSON(extendsSimpleClass));

        ClassWithEnum classWithEnum = new ClassWithEnum("test ClassWithEnum", VALUES.THREE);
        System.out.println("gson : " + gson.toJson(classWithEnum));
        System.out.println("ru.otus.hw9json.SimpleJson : " + SimpleJson.toJSON(classWithEnum));

        ClassWithSimpleList classWithSimpleList = new ClassWithSimpleList("test ClassWithSimpleList", 100);
        System.out.println("gson : " + gson.toJson(classWithSimpleList));
        System.out.println("ru.otus.hw9json.SimpleJson : " + SimpleJson.toJSON(classWithSimpleList));

        List<SimpleClass> simpleClassList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            simpleClassList.add(new SimpleClass(i,"test SimpleClass " + i));
        }
        System.out.println("gson : " + gson.toJson(simpleClassList));
        System.out.println("ru.otus.hw9json.SimpleJson : " + SimpleJson.toJSON(simpleClassList));

        ClassWithObjectList classWithObjectList = new ClassWithObjectList("test classWithObjectList");
        System.out.println("gson : " + gson.toJson(classWithObjectList));
        System.out.println("ru.otus.hw9json.SimpleJson : " + SimpleJson.toJSON(classWithObjectList));

    }
}



