package ru.otus.hw2sizeof;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        Object obj = new Object();
        System.out.println("Object - " + calcSize(obj));

        String str = "";
        System.out.println("Empty String - " + calcSize(str));

        int[] arr_1 = new int[1];
        System.out.println("int[1] - " + calcSize(arr_1));

        int[] arr_10 = new int[10];
        System.out.println("int[10] - " + calcSize(arr_10));

        int[] arr_1000 = new int[1000];
        System.out.println("int[1000] - " + calcSize(arr_1000));

        ArrayList<Integer> list = new ArrayList<>();
        System.out.println("list<int> Empty - " + calcSize(list));
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println("list<int> 10 - " + calcSize(list));

        A objA_link_null = new A();
        System.out.println("Obj ru.otus.hw2sizeof.A link = null - " + calcSize(objA_link_null));

        A objA_link_full = new A(objA_link_null);
        objA_link_full.link = objA_link_full;
        System.out.println("Obj ru.otus.hw2sizeof.A link != null - " + calcSize(objA_link_full));
    }

    static long calcSize(Object obj)
    {
        return calcSize(obj, null);
    }

    static long calcSize(Object obj, List<Object> visited) {
        long result = AgentMemory.sizeof(obj);
        try {
            if (visited == null)
            {
                visited = new ArrayList<>();
                visited.add(obj);
            }
            Class classInfo = Class.forName(obj.getClass().getName());
            if (!classInfo.isPrimitive()) {
                for (Field field : classInfo.getDeclaredFields()) {
                    if (!Modifier.isStatic(field.getModifiers()) && !field.getType().isPrimitive()) {
                        field.setAccessible(true);
                        Object tmpObj = field.get(obj);
                        if (tmpObj != null && visited.contains(tmpObj) == false) {
                            result += calcSize(tmpObj, visited);
                        }
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

}
class A
{
    public A() {
        a = 5;
        b = 10;
        link = null;
    }
    public A(A linkObj) {
        a = 5;
        b = 10;
        link = linkObj;
    }
    public int a;
    public int b;
    public A link;
}



