import java.util.*;
import java.lang.*;
public class AssertO{
    private Object obj;

    public AssertO(Object o){
        this.obj = o;
    }

    public AssertO isNotNull(){
        if (obj == null) {
            throw new IllegalArgumentException();
        }
        return this;
    }
   
   public AssertO isNull(){
        if (obj != null) {
            throw new IllegalArgumentException();
        }
        return this;
   }

   public AssertO isEqualTo(Object o2){
        if (!Objects.equals(obj, o2)) {
            throw new IllegalArgumentException();
        }
        return this;
   }

   public AssertO isNotEqualTo(Object o2){
        if (Objects.equals(obj, o2)) {
            throw new IllegalArgumentException();
        }
        return this;
   }

    public AssertO isInstanceOf(Class c){
        if (!(c.isInstance(obj))){
            throw new IllegalArgumentException();
        }
        return this;
   }


}