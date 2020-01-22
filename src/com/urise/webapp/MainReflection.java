package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String uuid = "1";
        String fullName = "Ivanov";
        Resume r = new Resume(uuid, fullName);
        Class<? extends Resume> resumeClass = r.getClass();
        Field field = resumeClass.getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        // TODO : invoke r.toString via reflection

        Method method = resumeClass.getMethod("toString");
        Object result = method.invoke(r);
        System.out.println(result);
    }
}
