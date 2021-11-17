package main;

import annotations.TestCase;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) {
        Class<TestClass> testClass = TestClass.class;
        if (testClass.getAnnotation(TestCase.class) != null) {
            TestRunner runner = new TestRunner(testClass);
            try {
                runner.process();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                    IllegalAccessException ex) {
                System.err.println("Nem sikerült az osztály inicializálása.");
            }
        }
        else
            System.err.println("Nincs @TestCase a kért osztályon");
    }
}
