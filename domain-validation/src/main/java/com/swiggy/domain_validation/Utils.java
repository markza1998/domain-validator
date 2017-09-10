package com.swiggy.domain_validation;

/**
 * Created by siddhants on 9/10/17.
 */
public class Utils {

    static boolean castBooleanWithDefault(Object v, boolean defaultV){
        try{
            return ((Boolean) v);
        }catch (Exception e){
            return defaultV;
        }
    }
}
