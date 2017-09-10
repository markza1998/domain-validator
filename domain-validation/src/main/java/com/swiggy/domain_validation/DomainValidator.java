package com.swiggy.domain_validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by siddhants on 9/9/17.
 */
public abstract class DomainValidator {

    public static<T> void throwableValidation(Class<T> dClazz, final ValidateBy validateBy, Object object) throws ValidationException {
        ValidationReport report = handleValidation(dClazz, validateBy, object);
        if(!report.isPass())
            throwValidationException(report);
    }

    public static<T> ValidationReport validate(Class<T> dClazz, final ValidateBy validateBy, Object object) throws ValidationException {
        return handleValidation(dClazz, validateBy, object);
    }

    private static<T> ValidationReport handleValidation(Class<T> dClazz, final ValidateBy validateBy, Object object){
        List<Method> methods = Arrays.asList(validateBy.clazz()[0].getMethods())
                .stream()
                .filter(m -> m.getName().contains(validateBy.methodPrefix()))
                .filter(m -> m.getReturnType().equals(Boolean.class) || m.getReturnType().equals(boolean.class))
                .filter(m -> m.getParameterCount() == 1)
                .filter(m -> m.getParameterTypes()[0].equals(dClazz))
                .collect(Collectors.toList());
        return invokeMethods(methods, object, validateBy.clazz()[0].getAnnotation(Validator.class), validateBy);
    }


    private static void throwValidationException(ValidationReport report) throws ValidationException {
        throw new ValidationException(report);
    }

    private static ValidationReport invokeMethods(Collection<Method> methods, Object object, Validator validator, ValidateBy validateBy){
        if(validateBy.isLazy())
            return LazyDomainValidator.invokeMethods(methods, object, validator);

        return EagerDomainValidator.invokeMethods(methods, object, validator);
    }


}
