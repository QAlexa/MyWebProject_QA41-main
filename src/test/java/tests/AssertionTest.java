package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AssertionTest {

    public static void main(String[] args) {
        Assert.assertEquals(myCalc(5, 5), 10);
    }

    public static int myCalc(int a, int b) {

        return a + b;
    }

    public static boolean myValue() {
        return true;
    }

//    @Test
//    public void testCalc() {
//        // Assert.assertEquals(myCalc(5,5),10);
//        // Assert.assertTrue(myValue());
//
//        //проверяет что выполнение кода приводит к выбрасыванию исключения
//        Assert.assertThrows(ArithmeticException.class, () -> myTest());
//    }
}
