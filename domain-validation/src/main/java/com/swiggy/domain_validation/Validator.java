package com.swiggy.domain_validation;

import java.lang.annotation.*;

/**
 * Created by siddhants on 9/9/17.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Validator {

     Class<?> clazz();

     String onFail();
}
