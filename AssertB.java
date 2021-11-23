import java.util.*;
public class AssertB{
    private boolean b1;

    public AssertB(boolean b){
        this.b1 = b;
    }

    public AssertB isEqualTo(boolean b2){
        if(b1 != b2){
            throw new IllegalArgumentException();
        }
        return this;
    }

      public AssertB isTrue(){
        if(b1 == false){
            throw new IllegalArgumentException();
        }
        return this;
    }

    public AssertB isFalse(){
        if(b1 == true){
            throw new IllegalArgumentException();
        }
        return this;
    }
}