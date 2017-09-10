package com.swiggy.domain_validation;

import java.util.List;

/**
 * Created by siddhants on 9/10/17.
 */
public class ValidationException extends RuntimeException {

    private final ValidationReport report;

    public ValidationException(ValidationReport report) {
        super(report.toString());
        this.report = report;
    }
}
