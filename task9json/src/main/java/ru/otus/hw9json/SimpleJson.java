package ru.otus.hw9json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SimpleJson {

    private static Set<Class> setClasses = new HashSet<>();

    static {
        setClasses.add(Boolean.class);
        setClasses.add(Byte.class);
        setClasses.add(Short.class);
        setClasses.add(Integer.class);
        setClasses.add(Long.class);
        setClasses.add(Float.class);
        setClasses.add(Double.class);
    }

    public static String toJSON(Object o) {
        if (o == null) {
            return "null";
        }
        return parseObject(o);
    }

    private static String parseObject(Object obj) {
        if (isSimpleObject(obj.getClass()))
        {
            return parseSimpleObject(obj);
        }
        else
        {
            return parseCompositeObject(obj);
        }
    }

    private static boolean isSimpleObject(Class classInfo) {
        return  (classInfo.getCanonicalName().equals("java.lang.String")
                ||
                classInfo.getCanonicalName().equals("java.lang.Character")
                ||
                setClasses.contains(classInfo)
                ||
                classInfo.isArray()
                ||
                Collection.class.isAssignableFrom(classInfo)
        );
    }

    private static String parseSimpleObject(Object obj) {
        String result = "";
        Class classObjInfo = obj.getClass();
        if (classObjInfo.getCanonicalName().equals("java.lang.String")
                || classObjInfo.getCanonicalName().equals("java.lang.Character")
                ) {
                result = "\"" + obj.toString() + "\"";
        }
        if (setClasses.contains(classObjInfo)) {
            result = obj.toString();
        }

        if (classObjInfo.isArray()) {
            JSONArray array = new JSONArray();
            for (int i = 0 ; i < Array.getLength(obj); i++){
                array.add(Array.get(obj, i));
            }
            result = array.toJSONString();
        }

        if (Collection.class.isAssignableFrom(classObjInfo)) {
            JSONArray array = new JSONArray();
            for (Object arrayItem : (Collection) obj) {
                if (isSimpleObject(arrayItem.getClass()))
                {
                    array.add(arrayItem);
                }
                else
                {
                    array.add(prepareCompositeObject(arrayItem,arrayItem.getClass()));
                }

            }
            result = array.toJSONString();
        }
        return result;
    }

    private static String parseCompositeObject(Object obj) {
        return prepareCompositeObject(obj, obj.getClass()).toJSONString();
    }

    private static JSONObject prepareCompositeObject(Object obj, Class classInfo) {
        JSONObject jsonObject = new JSONObject();
        for (Field field: classInfo.getDeclaredFields()){
            try {
                if (!Modifier.isTransient(field.getModifiers())) {
                    field.setAccessible(true);
                    if (field.getType().isEnum()) {
                        jsonObject.put(field.getName(),field.get(obj).toString() + "");
                        continue;
                    }

                    if (Collection.class.isAssignableFrom(field.getType())) {
                        JSONArray array = new JSONArray();
                        for (Object arrayItem : (Collection) field.get(obj)) {
                            if (isSimpleObject(arrayItem.getClass()))
                            {
                                jsonObject.put(field.getName(), field.get(obj));
                                break;
                            }
                            else
                            {
                                array.add(prepareCompositeObject(arrayItem,arrayItem.getClass()));
                            }

                        }
                        if (!array.isEmpty()) {
                            jsonObject.put(field.getName(), array);
                        }
                    }
                    else {
                        jsonObject.put(field.getName(), field.get(obj));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        if (classInfo.getSuperclass() != null){
            jsonObject.putAll(prepareCompositeObject(obj, classInfo.getSuperclass()));
        }


        return jsonObject;
    }
}
