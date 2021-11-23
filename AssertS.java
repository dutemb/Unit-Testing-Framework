import java.util.*;
public class AssertS{
    private String s1;
    
    public AssertS(String s){
        this.s1 = s;
    }

    public AssertS isNotNull(){
        if (s1 == null) {
            throw new IllegalArgumentException();
        }
        return this;
    }
   
   public AssertS isNull(){
        if (s1 != null) {
            throw new IllegalArgumentException();
        }
        return this;
   }

   public AssertS isEqualTo(Object o2){
        if (!Objects.equals(s1, o2)) {
            throw new IllegalArgumentException();
        }
        return this;
   }

   public AssertS isNotEqualTo(Object o2){
        if (Objects.equals(s1, o2)) {
            throw new IllegalArgumentException();
        }
        return this;
   }

   public AssertS startsWith(String s2){
          if (!s1.startsWith(s2)) {
            throw new IllegalArgumentException();
        }
        return this;
   }

   public AssertS isEmpty(){
        if (!s1.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return this;
   }

   public AssertS contains(String s2){
      if (!s1.contains(s2)) {
            throw new RuntimeException();
        }
        return this;
   }

    

}