package ru.otus.task4annotations;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationTest {
    public static void test(Class testClass, List<Class> orderAnnotations) throws Exception {
        Class classInfo = testClass;
        System.out.println("Class : " + testClass.getName());

        List<Method> methods = new ArrayList<>();

        for (Class item : orderAnnotations) {
            for (Method methodInfo : classInfo.getDeclaredMethods()) {
                if (methodInfo.isAnnotationPresent(item))
                {
                    methods.add(methodInfo);
                }
            }
        }
        Object instance = classInfo.getDeclaredConstructors()[0].newInstance();
        for (Method item : methods) {
            System.out.println(item.toString() + " ; " + item.getName() + " result: " + item.invoke(instance, null));
        }
    }
}
