package main;

import annotations.*;

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
        return "Teszt előtt vagyunk, ugye?!";
    }

    @After
    public String afterEachTest() {
        return "Lefutott a teszt.";
    }

    @BeforeClass
    public String beforeAllTests() {
        return "Indul a teszt csomag!";
    }

    @AfterClass
    public String afterAllTests() {
        return "Végzett az összes teszt.";
    }
}