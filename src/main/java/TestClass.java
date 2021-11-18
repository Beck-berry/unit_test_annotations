package main.java;

import main.java.annotations.*;

@TestCase
public class TestClass {

    public String notATest() {
        return "Nincs @Test, ezért ez sosem fut le.";
    }

    @Test
    public String plainTest() {
        return "@Test, fut ügyesen.";
    }

    @Test
    @Skip
    public String skippedTest() {
        return "@Skip, ergó nem futhat le";
    }

    @Before
    public String beforeEachTest() {
        return "---";
    }

    @After
    public String afterEachTest() {
        return "---";
    }

    @BeforeClass
    public String beforeAllTests() {
        return "###### START TEST CASE ######";
    }

    @AfterClass
    public String afterAllTests() {
        return "###### TEST CASE END ######";
    }
}