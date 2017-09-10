package com.swiggy.domain_validation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by siddhants on 9/10/17.
 */
public class ReflectionUtils {

    static Object callMethod(Method m, Object o){
        try {
            return m.invoke(null, o);
        } catch (InvocationTargetException | IllegalAccessException e) { // Warning todo
            e.printStackTrace();
        }
        return null;
    }
}
