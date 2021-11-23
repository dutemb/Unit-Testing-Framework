import java.util.*;
public class UnitTests{
    public static void main(String[] args) {
        HashMap<String, Throwable> res = Unit.testClass("Testing");
        System.out.println(res);
    }
}
