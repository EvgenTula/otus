/*
import org.json.JSONArray;
import org.json.JSONObject;
*/

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MyJson {
    public static String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        return MyJson.getJson(obj);
    }


    private static String getJson(Object obj)
    {
        String result = "";

        try {
            Class classInfo = Class.forName(obj.getClass().getName());
            if (!classInfo.isPrimitive()) {
                for (Field field : classInfo.getDeclaredFields()) {
                    if (!Modifier.isStatic(field.getModifiers()) && (field.getType().isPrimitive() || field.getType().getCanonicalName().equals("java.lang.String"))) {
                        field.setAccessible(true);
                        result += field.get(obj);
                        //if (tmpObj != null && visited.contains(tmpObj) == false) {
//                            result += calcSize(tmpObj, visited);
  //                      }
                    }
                }
            }
            if (classInfo.isPrimitive())
            {
                if (classInfo.getCanonicalName().equals("java.lang.String")) {

                }
            }
            if (classInfo.isArray()) {
                System.out.println(classInfo);

                /*
                for (var item :) {

                }*/
            }
            /*
            else
            {
                for (Field field: classInfo.getDeclaredFields()) {
                    try {
                        prepareValue(field.get(obj));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }*/
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
        //return calcSize(obj, null);
    }

/*
    static long calcSize(Object obj, List<Object> visited) {
        long result = AgentMemory.sizeof(obj);
        try {
            if (visited == null)
            {
                visited = new ArrayList<>();
                visited.add(obj);
            }
            Class classInfo = Class.forName(obj.getClass().getName());
            if (!classInfo.isPrimitive()) {
                for (Field field : classInfo.getDeclaredFields()) {
                    if (!Modifier.isStatic(field.getModifiers()) && !field.getType().isPrimitive()) {
                        field.setAccessible(true);
                        Object tmpObj = field.get(obj);
                        if (tmpObj != null && visited.contains(tmpObj) == false) {
                            result += calcSize(tmpObj, visited);
                        }
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }*/
}
