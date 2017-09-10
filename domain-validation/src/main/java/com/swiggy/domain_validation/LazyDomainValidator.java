package com.swiggy.domain_validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Created by siddhants on 9/9/17.
 */
class LazyDomainValidator {

    static ValidationReport invokeMethods(Collection<Method> methods, Object object, Validator validator){
        return methods.stream()
                .map(m -> ValidatorMethodInvocator.invoke(m, object, validator))
                .filter(Optional::isPresent)
                .findFirst()
                .map(Optional::get)
                .map(error -> new ValidationReport(false, validator.onFail() + error , Collections.singletonList(error), object))
                .orElseGet(() -> new ValidationReport(true, "", Collections.EMPTY_LIST, object));
    }
}
