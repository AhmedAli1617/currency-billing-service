package com.assessment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedCurrencyException extends RuntimeException {
    public UnsupportedCurrencyException(String baseCurrency) {
        super("BaseCurrency: " + baseCurrency + " is not supported");
    }
}
