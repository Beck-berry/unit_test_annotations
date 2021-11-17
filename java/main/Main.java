package java.main;

import java.annotations.TestCase;

public class Main {

    public static void main(String[] args) {
        Class<TestClass> testClass = TestClass.class;
        if (testClass.getAnnotation(TestCase.class) != null) {
            TestRunner runner = new TestRunner();
            runner.process(testClass);
        }
        else
            System.out.println("Nincs @TestCase a kért osztályon");
    }
}
