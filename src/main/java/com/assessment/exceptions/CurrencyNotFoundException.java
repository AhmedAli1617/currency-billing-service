package com.assessment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException(String currency) {
        super("Currency: " + currency + " not found in exchange rates");
    }
}
