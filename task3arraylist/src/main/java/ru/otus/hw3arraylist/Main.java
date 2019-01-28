package ru.otus.hw3arraylist;

import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int size = 5;

        MyArrayList a = new MyArrayList(size);
        System.out.println(a.size());

        System.out.println("===create===");
        for(int i = 0; i < size; i++) {
            a.add(i);
        }
        printList(a);

        System.out.println("===addAll===");
        Collections.addAll(a,111, 112,113);
        printList(a);

        System.out.println("===array b is empty===");
        MyArrayList b = new MyArrayList(a.size());

        for(int i = 0; i < a.size(); i++) {
            b.add(0);
        }
        printList(b);

        System.out.println("===a copy to b===");
        Collections.copy(b, a);
        printList(b);

        System.out.println("===sort a===");
        Collections.sort(a,
                (o1, o2) -> (int)o2 - (int)o1
                );
        printList(a);
    }

    public static <E> void printList(List<E> list)
    {
        for (E item: list) {
            System.out.println(item);
        }
        System.out.println("size = " + list.size());
    }
}