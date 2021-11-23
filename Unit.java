import java.util.*;
import java.lang.annotation.*;
import java.lang.reflect.*;

public class Unit {
    public static HashMap < String, Throwable > testClass(String name) {
        Class <?> cur;
        Object ob;
        HashMap < String, Throwable > returned = new HashMap < String, Throwable > ();
        List < Method > beforeClass = new ArrayList < Method > ();
        List < Method > before = new ArrayList < Method > ();
        List < Method > testM = new ArrayList < Method > ();
        List < Method > after = new ArrayList < Method > ();
        List < Method > afterClass = new ArrayList < Method > ();

        try {
            cur = Class.forName(name);
        } catch (Exception e) {
            return null;
        }

        try {
            ob = cur.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }

        Method[] m = cur.getDeclaredMethods();
        m = sortedA(m);

        for (Method curM: m) {
            int doubleAnnotation = 0;
            
            if (curM.getAnnotation(BeforeClass.class) != null ) {
                doubleAnnotation++;
                checkAn(doubleAnnotation);
                beforeClass.add(curM);

            }
            if (curM.getAnnotation(Before.class) != null) {
                if(Modifier.isStatic(curM.getModifiers())) continue;
                //if(!Modifier.isStatic(curM.getModifiers()))
                doubleAnnotation++;
                checkAn(doubleAnnotation);
                before.add(curM);
               // System.out.println(before.)
            }
             if (curM.getAnnotation(Test.class) != null) {
                doubleAnnotation++;
                checkAn(doubleAnnotation);
                testM.add(curM);
            
            }
           
            if (curM.getAnnotation(After.class) != null) {
                if(Modifier.isStatic(curM.getModifiers())) continue;
                doubleAnnotation++;
                checkAn(doubleAnnotation);
                after.add(curM);
            }
            if (curM.getAnnotation(AfterClass.class) != null) {
                doubleAnnotation++;
                checkAn(doubleAnnotation);
                afterClass.add(curM);
            }
           
        }

        checkMethod(beforeClass);
        for (Method m1: testM) {
            //checkMethod(before);
            try {
                checkMethod(before);
                m1.invoke(ob);
                returned.put(m1.getName(), null);
                checkMethod(after);
            } catch (Exception e) {
                returned.put(m1.getName(), e.getCause());
            }
            //checkMethod(after);
        }
        checkMethod(afterClass);


        return returned;
    }

   private static void checkMethod(List < Method > method) {
        for (Method m : method) {
            try {
                m.invoke(null);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }

  private static Method[] sortedA(Method[] m){
        Arrays.sort(m, new Comparator < Method > () {
            @Override
            public int compare(Method method1, Method method2) {
                String m = method1.getName();
                String mm = method2.getName();
                return m.compareTo(mm);
            }
        });
        return m;
   }

 private static void checkAn(int count){
       if (count > 1) {
                throw new IllegalArgumentException();
            }
   }


    public static HashMap < String, Object[] > quickCheckClass(String name) {
    
        Class <?> cur;
        Object ob;
        HashMap<String, Object[]> returned = new HashMap<String, Object[]>();
        List<Method> properties = new ArrayList<Method>();

         try {
            cur = Class.forName(name);
        } catch (Exception e) {
            return null;
        }

        try {
            ob = cur.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }

        Method[] methods = cur.getDeclaredMethods();
        methods = sortedA(methods);
        
         for (Method curM: methods) {
            if (curM.getAnnotation(Property.class) != null) {
                properties.add(curM);
            }
        }
        Method[] converted = new Method[properties.size()];
        //pass it array to sort, then cconvert to list
       properties = Arrays.asList(sortedA(properties.toArray(converted)));
       
        for (Method m : properties){
            Parameter[] allParams = m.getParameters();
            int numRuns = 1;
            ArrayList<ArrayList<Object>> tested = new ArrayList<ArrayList<Object>>();
            for (Parameter curC : allParams){
                AnnotatedType an  = curC.getAnnotatedType();
                tested.add(handleCases(ob,cur,an));
            }
            
            for (int i = 0; i < tested.size(); i++) {
                numRuns *= tested.get(i).size();
            }

            if(numRuns>100) numRuns = 100;

           

        }
       return returned;
    }
    private static ArrayList<Object> handleCases(Object o, Class c,AnnotatedType a){
        Annotation decide = a.getAnnotations()[0];
        ArrayList<Object> returned = new ArrayList<>();
        if(decide.annotationType() == StringSet.class){
           return treatString(returned,a,o,c);
        }else if(decide.annotationType() == IntRange.class){
           return treatInteger(returned,a,o,c);
        } else if (decide.annotationType() ==  ListLength.class){
           // treatList(decide,returned, a,o,c);
        }else if(decide.annotationType() == ForAll.class){
            return treatObject(decide,returned,a,o,c);
        }else{
            throw new RuntimeException();
        }
        return returned;
    }

    private static ArrayList<Object> treatInteger( ArrayList<Object> returned,AnnotatedType a, Object o, Class c){
         if (a.getType() != Integer.class) {
                throw new IllegalArgumentException();
            }
            IntRange iRange = (IntRange)a;
            for (int i = iRange.min(); i <= iRange.max(); i++) {
                returned.add(i);
            }
            return returned;
    }

      private static ArrayList<Object> treatString( ArrayList<Object> returned,AnnotatedType a, Object o, Class c){
         if (a.getType() != String.class) {
                throw new IllegalArgumentException();
            }
            StringSet sRange = (StringSet)a;
            for (String s: sRange.strings()) {
                returned.add(s);
            }
            return returned;
    }

    private static ArrayList<Object> treatObject(Annotation an, ArrayList<Object> returned,AnnotatedType a, Object o, Class c){
        if (a.getType() != Object.class) {
                throw new IllegalArgumentException();
            }
            ForAll forAll = (ForAll) an;
            String getThis = forAll.name();
            Method method;
            try {
                method = c.getDeclaredMethod(getThis);
            } catch (Exception e) {
                throw new IllegalArgumentException();
            }
            for (int i = 0; i < forAll.times(); i++) {
                try {
                    returned.add(method.invoke(o));
                } catch (Exception e){
                    throw new IllegalArgumentException();
                }
            }
          return returned;  
    }

      /* private static ArrayList<Object> treatList( Annotation annotation, ArrayList<Object> returned,AnnotatedType a, Object o, Class c){

        ListLength outerList = (ListLength) annotation;
        //get integer list inside of the list
        AnnotatedType intRange = ((AnnotatedParameterizedType) a).getAnnotatedActualTypeArguments()[0];
        ArrayList<Object> intList = new ArrayList<Object>();
        intList = treatInteger(intList,intRange,o,c);
     
        for(int i = outerList.min(); i < outerList.max(); i++){
             if (i == 0) {
                    returned.add((List) new ArrayList<>());
                    continue;
                }
              
             
        }
        return returned;
    }*/

    

 
}