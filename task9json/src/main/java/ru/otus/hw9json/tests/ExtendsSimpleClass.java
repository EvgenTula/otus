package ru.otus.hw9json.tests;

public class ExtendsSimpleClass extends SimpleClass {
    public int secondNum;
    public ExtendsSimpleClass(int num, String txt, int secondNum) {
        super(num, txt);
        this.secondNum = secondNum;
    }
}
