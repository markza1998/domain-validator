package com.swiggy.domain_validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Created by siddhants on 9/10/17.
 */
class ValidatorMethodInvocator {

    static Optional<String> invoke(Method method, Object object, Validator validator) {
        if (!invokeMethod(method, object)) {
            Annotation annotation = method.getAnnotation(OnFail.class);
            if (annotation != null)
                return Optional.of(((OnFail) annotation).onFail());
            else {
                return Optional.of(validator.onFail());
            }
        }
        return Optional.empty();
    }

    static boolean invokeMethod(Method method, Object object) {
        Object value = ReflectionUtils.callMethod(method, object);
        return value == null || Utils.castBooleanWithDefault(value, true);
    }
}
