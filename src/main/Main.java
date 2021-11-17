package main;

import annotations.TestCase;

public class Main {

    public static void main(String[] args) {
        Class<TestClass> testClass = TestClass.class;
        if (testClass.getAnnotation(TestCase.class) != null) {
            TestRunner runner = new TestRunner(testClass);
            runner.process();
        } else {
            System.err.println("Nincs @TestCase a kért osztályon");
        }
    }
}
