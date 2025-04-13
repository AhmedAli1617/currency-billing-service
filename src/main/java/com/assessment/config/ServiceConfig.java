package com.assessment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "service")
@Data
public class ServiceConfig {

    private String baseUrl;
    private String apiKey;
    private String defaultCurrency;
    private List<String> supportedCurrencies;
    private String exchangeRateApiUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
