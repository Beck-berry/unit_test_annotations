import main.java.TestClass;

public class Main {

    public static void main(String[] args) {
        Class<TestClass> testClass = TestClass.class;
        TestRunner runner = new TestRunner(testClass);
        runner.process();
    }
}
