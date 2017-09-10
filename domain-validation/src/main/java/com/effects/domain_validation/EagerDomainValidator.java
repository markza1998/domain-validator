package com.swiggy.domain_validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by siddhants on 9/9/17.
 */
class EagerDomainValidator {
    static ValidationReport invokeMethods(Collection<Method> methods, Object object, Validator validator){
        List<String> errors = methods.stream()
                .map(m -> ValidatorMethodInvocator.invoke(m, object, validator))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        if(errors.isEmpty())
            return new ValidationReport(true, "", Collections.EMPTY_LIST, object);

        return new ValidationReport(true, validator.onFail() + ":" + object.toString(), errors, object);

    }

}
