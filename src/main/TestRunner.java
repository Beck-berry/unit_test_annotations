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

        runFunctions(beforeAllMethodList);
        for (Method testMethod : testMethodList) {
            if (testMethod.getAnnotation(Skip.class) == null) {
                runFunctions(beforeTestMethodList);
                runMethod(testMethod);
                runFunctions(afterTestMethodList);
            }
        }
        runFunctions(afterAllMethodList);
    }

    private void runFunctions(List<Method> functionList) {
        for (Method method : functionList) {
            runMethod(method);
        }
    }

    private void runMethod(Method method) {
        try {
            System.out.println(method.invoke(instance));
        } catch (InvocationTargetException | IllegalAccessException ex) {
            System.err.println("Hiba a metódus hívása során.");
        }
    }
}