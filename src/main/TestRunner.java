package main;

import annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class TestRunner {

    private final Class<?> clazz;
    private Object instance;

    public TestRunner(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void process() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (instance == null) {
            this.instance = this.clazz.getDeclaredConstructor().newInstance();
        }

        String result = "";

        List<Method> methodList = List.of(clazz.getMethods());
        List<Method> testMethodList =
                methodList.stream().filter(m -> m.getAnnotation(Test.class) != null).collect(Collectors.toList());
        List<Method> beforeAllMethodList =
                methodList.stream().filter(m -> m.getAnnotation(BeforeClass.class) != null).collect(Collectors.toList());
        List<Method> afterAllMethodList =
                methodList.stream().filter(m -> m.getAnnotation(AfterClass.class) != null).collect(Collectors.toList());
        List<Method> beforeTestMethodList =
                methodList.stream().filter(m -> m.getAnnotation(Before.class) != null).collect(Collectors.toList());
        List<Method> afterTestMethodList =
                methodList.stream().filter(m -> m.getAnnotation(After.class) != null).collect(Collectors.toList());

        for (Method method : beforeAllMethodList) {
            result += (String) method.invoke(instance);
        }
        for (Method method : testMethodList) {
            if (method.getAnnotation(Skip.class) == null) {
                for (Method before : beforeTestMethodList) {
                    result += (String) before.invoke(instance);
                }
                result += (String) method.invoke(instance);
                for (Method after : afterTestMethodList) {
                    result += (String) after.invoke(instance);
                }
            }
        }
        for (Method method : afterAllMethodList) {
            result += (String) method.invoke(instance);
        }

        System.out.println(result);
    }
}