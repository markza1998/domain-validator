package com.swiggy.spring_domain_validator;

import com.swiggy.domain_validation.DomainValidator;
import com.swiggy.domain_validation.ValidateBy;
import com.swiggy.domain_validation.Validator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by siddhants on 9/9/17.
 */
@Aspect
@Component
public class ValidationByProcessor {


    private final Map<Method, Supplier<?>> map = new HashMap<>();

    private final Supplier<?> EMPTY_SUPPLIER = () -> null;

    /**
     *
     * @param joinPoint
     */
    @Before(value = "@annotation(com.swiggy.domain_validator.ValidateBy)")
    public void validate(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        if(map.containsKey(methodSignature.getMethod()))
            map.get(methodSignature.getMethod()).get();
        else{
            map.put(methodSignature.getMethod(), buildSupplier(methodSignature, joinPoint.getArgs()));
            map.get(methodSignature.getMethod()).get();
        }
    }


    private Supplier<?> buildSupplier(MethodSignature methodSignature, Object[] args){
        ValidateBy validateBy = methodSignature.getMethod().getAnnotation(ValidateBy.class);
        Validator validator = validateBy.clazz()[0].getAnnotation(Validator.class);
        if(validator == null || args.length == 0) // If annotation is not available over the validation class return
            return EMPTY_SUPPLIER;
        Optional<Object> objectOptional = findObjectOfClass(validator.clazz(), args);
        if(!objectOptional.isPresent())
            return () -> null;
        return () ->  {
               DomainValidator.throwableValidation(validator.clazz(), validateBy, objectOptional.get());
               return null;
        };
    }

    private Optional<Object> findObjectOfClass(Class<?> clazz, Object[] objects){
        for(Object object: objects){
            if(object.getClass().equals(clazz))
                return Optional.of(object);
        }
        return Optional.empty();
    }



}
