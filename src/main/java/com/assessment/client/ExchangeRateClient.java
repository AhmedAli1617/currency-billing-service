package com.assessment.client;

import com.assessment.config.ServiceConfig;
import com.assessment.exceptions.CurrencyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class ExchangeRateClient {
    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired
    private RestTemplate restTemplate;


    public String buildExchangeRateApiUrl(String currencyCode) {
        return UriComponentsBuilder.fromHttpUrl(serviceConfig.getBaseUrl())
                .pathSegment(serviceConfig.getApiKey(), "latest", currencyCode)
                .toUriString();
    }

    public double getExchangeRate(String baseCurrency, String targetCurrency) {
        log.info("Fetching exchange rate from {} to {}", baseCurrency, targetCurrency);

        String url = buildExchangeRateApiUrl(baseCurrency);
        try {
            ResponseEntity<ExchangeRateResponse> response = restTemplate.getForEntity(url, ExchangeRateResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                ExchangeRateResponse exchangeRateResponse = response.getBody();
                if (exchangeRateResponse != null) {
                    Map<String, Double> rates = exchangeRateResponse.getConversionRates();
                    if (rates != null && rates.containsKey(targetCurrency)) {
                        log.info("Exchange rate from {} to {}: {}", baseCurrency, targetCurrency, rates.get(targetCurrency));
                        return rates.get(targetCurrency);
                    } else {
                        log.error("Target currency {} not found in exchange rates", targetCurrency);
                        throw new CurrencyNotFoundException(targetCurrency);
                    }
                } else {
                    log.error("Exchange rate response body is null");
                    throw new RuntimeException("Exchange rate response body is null");
                }
            } else {
                log.error("Failed to fetch exchange rate. HTTP Status: {}", response.getStatusCode());
                throw new RuntimeException("Failed to fetch exchange rate. HTTP Status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching exchange rate: {}", e.getMessage());
            throw new RuntimeException("Error occurred while fetching exchange rate", e);
        }
    }
}
