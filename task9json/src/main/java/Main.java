import TestClass.A;
import TestClass.B;
import TestClass.C;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {

    public static void main(String[] args) {

        Gson gson = new Gson();

        System.out.println("gson : " + gson.toJson("text"));
        System.out.println("MyJson : " +MyJson.toJSON("text"));

        System.out.println("gson : " + gson.toJson(""));
        System.out.println("MyJson : " +MyJson.toJSON(""));

        System.out.println("gson : " + gson.toJson(1));
        System.out.println("MyJson : " +MyJson.toJSON(1));

        System.out.println("gson : " + gson.toJson('1'));
        System.out.println("MyJson : " +MyJson.toJSON('1'));

        System.out.println("gson : " + gson.toJson(false));
        System.out.println("MyJson : " +MyJson.toJSON(false));

        System.out.println("gson : " + gson.toJson(null));
        System.out.println("MyJson : " +MyJson.toJSON(null));

        System.out.println("gson : " + gson.toJson(new Object()));
        System.out.println("MyJson : " +MyJson.toJSON(new Object()));

        System.out.println("gson : " + gson.toJson(new Object[10]));
        System.out.println("MyJson : " + MyJson.toJSON(new Object[10]));

        List<Integer> listInteger = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listInteger.add(new Random().nextInt(10));
        }
        System.out.println("gson : " + gson.toJson(listInteger));
        System.out.println("MyJson : " + MyJson.toJSON(listInteger));


        A a = new A(1,"test A");
        B b = new B(1,"test B", 100);
        C c = new C(1,"test C", 100);
        System.out.println("gson : " + gson.toJson(a));
        System.out.println("MyJson : " +MyJson.toJSON(a));
        System.out.println("gson : " + gson.toJson(b));
        System.out.println("MyJson : " +MyJson.toJSON(b));
        System.out.println("gson : " + gson.toJson(c));
        System.out.println("MyJson : " +MyJson.toJSON(c));



    }
}



