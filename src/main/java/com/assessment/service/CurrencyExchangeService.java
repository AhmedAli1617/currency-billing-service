package com.assessment.service;

import com.assessment.client.ExchangeRateClient;
import com.assessment.client.ExchangeRateResponse;
import com.assessment.config.ServiceConfig;
import com.assessment.exceptions.UnsupportedCurrencyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class CurrencyExchangeService {
    @Autowired
   ExchangeRateClient exchangeRateClient;

    @Autowired
    ServiceConfig serviceConfig;


    public double getExchangeRate(String baseCurrency, String targetCurrency) {
        if (!serviceConfig.getSupportedCurrencies().contains(baseCurrency)) {
            log.warn("Unsupported base currency: {}", baseCurrency);
            throw new UnsupportedCurrencyException(baseCurrency);
        }

        if (!serviceConfig.getSupportedCurrencies().contains(targetCurrency)) {
            log.warn("Unsupported target currency: {}", targetCurrency);
            throw new UnsupportedCurrencyException(targetCurrency);
        }

        log.info("Fetching exchange rate from {} to {}", baseCurrency, targetCurrency);
        return exchangeRateClient.getExchangeRate(baseCurrency, targetCurrency);
    }
}
