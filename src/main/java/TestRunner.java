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

    public void process() {
        if (clazz.getAnnotation(main.java.annotations.TestCase.class) == null) {
            System.err.println("Nincs @TestCase a kért osztályon");
        }

        if (instance == null) {
            try {
                this.instance = this.clazz.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                    IllegalAccessException ex) {
                System.err.println("Nem sikerült az osztály inicializálása.");
            }
        }

        List<Method> methodList = List.of(clazz.getMethods());
        List<Method> testMethodList =
                methodList.stream().filter(m -> m.getAnnotation(main.java.annotations.Test.class) != null
                        && m.getAnnotation(main.java.annotations.Skip.class) == null).collect(Collectors.toList());
        List<Method> beforeAllMethodList =
                methodList.stream().filter(m -> m.getAnnotation(main.java.annotations.BeforeClass.class) != null).collect(Collectors.toList());
        List<Method> afterAllMethodList =
                methodList.stream().filter(m -> m.getAnnotation(main.java.annotations.AfterClass.class) != null).collect(Collectors.toList());
        List<Method> beforeTestMethodList =
                methodList.stream().filter(m -> m.getAnnotation(main.java.annotations.Before.class) != null).collect(Collectors.toList());
        List<Method> afterTestMethodList =
                methodList.stream().filter(m -> m.getAnnotation(main.java.annotations.After.class) != null).collect(Collectors.toList());

        runFunctions(beforeAllMethodList);
        for (Method testMethod : testMethodList) {
            runFunctions(beforeTestMethodList);
            runMethod(testMethod);
            runFunctions(afterTestMethodList);
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