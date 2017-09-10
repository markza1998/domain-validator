package com.swiggy.domain_validation;

import java.lang.annotation.Annotation;

/**
 * Created by siddhants on 9/9/17.
 */
public class OrderService {

    @ValidateBy(clazz = CreateOrderValidator.class)
    public Order create(Order order){
        return new Order();
    }

}

@Validator(clazz = Order.class, onFail = "Order creation failed")
class CreateOrderValidator{

    @OnFail(onFail = "Order is not ok")
    public static boolean isOrderOk(Order order){
        return false;
    }

    public static boolean isOrderOk1(Order order){
        return true;
    }

    public static boolean isOrderOk2(Order order){
        return true;
    }

    public static boolean isOrderOk3(Order order){
        return true;
    }

    public static boolean isOrderOk4(Order order){
        return true;
    }

}

class Order{

}


class OrderTest{
    public static void main(String[] args) throws NoSuchMethodException, ValidationException {
        testOrder();

    }

    public static void testOrder() throws NoSuchMethodException, ValidationException {
        OrderService service = new OrderService();
        Annotation annotation = service.getClass().getDeclaredMethods()[0].getAnnotation(ValidateBy.class);
        DomainValidator.throwableValidation(Order.class,
                (ValidateBy) annotation,
                new Order()
                );
    }
}
