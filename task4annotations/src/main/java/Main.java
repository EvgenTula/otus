import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static java.lang.annotation.ElementType.*;

public class Main {
    public static void main(String[] args) {
        try {
            testAnnotations(TestA.class);
            testAnnotations(TestB.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testAnnotations(Class testClass) throws Exception {
        Class classInfo = testClass;
        System.out.println("Class : " + testClass.getName());

        HashMap<Class, Method> methods = new HashMap<Class, Method>();
        methods.put(Before.class,null);
        methods.put(Test.class,null);
        methods.put(After.class,null);


        for (Map.Entry<Class,Method> item : methods.entrySet()) {
            for (Method methodInfo : classInfo.getDeclaredMethods()) {
                if (methodInfo.isAnnotationPresent(item.getKey()))
                {
                    item.setValue(methodInfo);
                }
            }
        }
        Object instance = classInfo.getDeclaredConstructors()[0].newInstance();
        for (Map.Entry<Class,Method> item : methods.entrySet()) {
            System.out.println(item.getValue().invoke(instance, null));
        }
    }
}


@Retention(RetentionPolicy.RUNTIME)
@Target(METHOD)
@interface Before { };

@Retention(RetentionPolicy.RUNTIME)
@Target(METHOD)
@interface After { };

@Retention(RetentionPolicy.RUNTIME)
@Target(METHOD)
@interface Test { };


class TestA  {
    @Before
    public String m1()  {
        return this.getClass() + " m1";
    }
    @After
    public String m2()  {
        return this.getClass() + " m2";
    }
    @Test
    public String m3()  {
        return this.getClass() + " m3";
    }
    public String m4()  {
        return this.getClass() + " m4";
    }
}


class TestB {
    @Test
    public String m1()  {
        return this.getClass() + " m1";
    }
    @After
    public String m2()  {
        return this.getClass() + " m2";
    }
    public String m3()  {
        return this.getClass() + " m3";
    }
    @Before
    public String m4()  {
        return this.getClass() + " m4";
    }
}
