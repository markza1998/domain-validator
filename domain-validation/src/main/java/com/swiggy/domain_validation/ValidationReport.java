package com.swiggy.domain_validation;

import java.util.List;

/**
 * Created by siddhants on 9/9/17.
 */
public class ValidationReport {
    private final boolean pass;
    private final String msg;
    private final List<String> errors;
    private final Object object;

    public ValidationReport(boolean pass, String msg, List<String> errors, Object object) {
        this.pass = pass;
        this.msg = msg;
        this.errors = errors;
        this.object = object;
    }

    public boolean isPass() {
        return pass;
    }
}
