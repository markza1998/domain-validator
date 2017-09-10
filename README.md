Domain Validator
==============

Latest release: [0.1.1]

An annotation based validator for POJOs with Runtime exceptions on failures. 
Programmer can define static functions in a class and define an Annotation 
over method before which validation needs to happen. 
Spring based wrapper based on spring annotation available for uage with spring framework.

Installation
------------

Build using gradle 

Usage
--------


~~~java

class Order{}

class OrderService {
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
}

~~~

Usually, create and update methods have some validations which are required to pass. Such logic usually leads to some bad
design and code smells. It makes sense to collect all such methods into the same class and collect them at runtime. 
By defining class name(CreateOrderValidator) over which validation needs to be done, this library can invoke all validation methods 
at runtime.
On the validator class we define the class on which validation needs to be done alongwith general error message on validation failure.
For a function to be invoked it should have following properties:
1. Return type should be Boolen(or primitive counterpart boolean)
2. Method should have only one argument, same as the object being validated.
3. For now, method has to be static(will add functionality for non static methods)
