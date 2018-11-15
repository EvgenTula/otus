package TestClass;

import java.util.ArrayList;
import java.util.Random;

public class C extends B {
    public ArrayList<Integer> listInteger;
    public C(int num, String txt, int secondNum) {
        super(num, txt, secondNum);
        listInteger = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listInteger.add(new Random().nextInt(100));
        }
    }
}
