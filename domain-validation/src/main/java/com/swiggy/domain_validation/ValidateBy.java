package com.swiggy.domain_validation;

import java.lang.annotation.*;

/**
 * Created by siddhants on 9/9/17.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateBy {

    /**
     * Class name of the validator
     * @return
     */
    Class<?>[] clazz();

    /**
     * Prefix to look for in methods
     * @return
     */
    String methodPrefix() default "is";

    /**
     * If any one validation fails, return
     * @return
     */
    boolean isLazy() default true;

}
