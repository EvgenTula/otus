import java.lang.reflect.*;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        Object obj = new Object();
        System.out.println("Object - " + AgentMemory.sizeof(obj));
        String str = "";
        System.out.println("Empty String - " + AgentMemory.sizeof(str));
        int[] arr_1 = new int[1];
        System.out.println("int[1] - " + AgentMemory.sizeof(arr_1));
        int[] arr_10 = new int[10];
        System.out.println("int[10] - " + AgentMemory.sizeof(arr_10));
        int[] arr_1000 = new int[1000];
        System.out.println("int[1000] - " + AgentMemory.sizeof(arr_1000));

        ArrayList<Integer> list = new ArrayList<>();
        System.out.println("list<int> Empty - " + AgentMemory.sizeof(list));
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println("list<int> 10 - " + AgentMemory.sizeof(list));

        A objA_link_null = new A();
        long size =  AgentMemory.sizeof(objA_link_null);
        try {
            Class classInfo = Class.forName(objA_link_null.getClass().getName());
            for (Field field : classInfo.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers()) && !field.getType().isPrimitive()) {
                    Object tmpObj = field.get(objA_link_null);
                    if (tmpObj != null) {
                        size += AgentMemory.sizeof(field);
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("Obj A link = null - " + size);


        A objA_link_full = new A(new A(new A()));
        System.out.println("Obj A link != null - " + calcSize(objA_link_full));
    }

    static long calcSize(Object obj) {
        long result = AgentMemory.sizeof(obj);
        try {
            Class classInfo = Class.forName(obj.getClass().getName());
            for (Field field : classInfo.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers()) && !field.getType().isPrimitive()) {

                    Object tmpObj = field.get(obj);
                    if (tmpObj != null) {
                        result += calcSize(tmpObj);
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



