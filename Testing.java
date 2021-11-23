import java.util.*;

public class Testing {
    @BeforeClass public static void before_class(){
        System.out.println("before_class method");
    }
    @Before public void before_(){
        System.out.println("before_ method");
    }
     @Test public static void testContains(){
        System.out.println("testing String");
        String s2 = "Jeff ";
        String s = "Jeff Foster is a cool dude who knows software engineering";
        Assertion.assertThat(s).contains(s2);
    }
   /* @After public static void after_() {
        System.out.println("After_");
    }

    @AfterClass public static void afterClass_() {
        System.out.println("AfterClass_");
    }*/


}