package ru.otus.hw4annotations;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            List<Class> orderAnnotations = new ArrayList<>();
            orderAnnotations.add(Before.class);
            orderAnnotations.add(Test.class);
            orderAnnotations.add(After.class);

            AnnotationTest.test(TestA.class, orderAnnotations);
            AnnotationTest.test(TestB.class, orderAnnotations);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class TestA {
    @Before
    public String m1() {
        return "before test";
    }

    @After
    public String m2() {
        return "after test";
    }

    @Test
    public String m3() { return "test m3 ok"; }

    @Test
    public String m31() { return "test m31 ok "; }

    @Test
    public String m32() { return "test m32 ok "; }

    @Test
    public String m33() { return "test m33 ok "; }

    public String m4() {
        return " m4";
    }
}


class TestB {
    @Test
    public String m1() { return "test ok "; }

    @After
    public String m2() {
        return "after test";
    }

    public String m3() {
        return " m3";
    }

    @Before
    public String m4() {
        return "before test";
    }
}
