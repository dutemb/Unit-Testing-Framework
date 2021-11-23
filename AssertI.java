import java.util.*;
public class AssertI{
    private int i;

    public AssertI(int i){
        this.i = i;
    }

    public AssertI isEqualTo(int i2){
          if (i != i2) {
            throw new IllegalArgumentException();
        }
        return this;
    }
    public AssertI isLessThan(int i2){
          if (i >= i2) {
            throw new IllegalArgumentException();
        }
        return this;
    }

    public AssertI isGreaterThan(int i2){
          if (i <= i2) {
            throw new IllegalArgumentException();
        }
        return this;
    }
}