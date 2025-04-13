package com.assessment.service;

import com.assessment.client.ExchangeRateClient;
import com.assessment.config.ServiceConfig;
import com.assessment.exceptions.UnsupportedCurrencyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CurrencyExchangeServiceTest {

    @Mock
    private ExchangeRateClient exchangeRateClient;

    @Mock
    private ServiceConfig serviceConfig;

    @InjectMocks
    private CurrencyExchangeService currencyExchangeService;

    private List<String> supportedCurrencies;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        supportedCurrencies = Arrays.asList("USD", "PKR", "EUR");
        when(serviceConfig.getSupportedCurrencies()).thenReturn(supportedCurrencies);
    }

    @Test
    void testGetExchangeRate_Success() {
        String baseCurrency = "USD";
        String targetCurrency = "PKR";
        double expectedRate = 280.0;

        when(exchangeRateClient.getExchangeRate(baseCurrency, targetCurrency)).thenReturn(expectedRate);

        double actualRate = currencyExchangeService.getExchangeRate(baseCurrency, targetCurrency);

        assertEquals(expectedRate, actualRate, 0.001);
        verify(exchangeRateClient, times(1)).getExchangeRate(baseCurrency, targetCurrency);
    }

    @Test
    void testGetExchangeRate_UnsupportedBaseCurrency() {
        String baseCurrency = "ABC";
        String targetCurrency = "PKR";

        UnsupportedCurrencyException exception = assertThrows(
                UnsupportedCurrencyException.class,
                () -> currencyExchangeService.getExchangeRate(baseCurrency, targetCurrency)
        );

        assertEquals("BaseCurrency: ABC is not supported", exception.getMessage());
        verify(exchangeRateClient, never()).getExchangeRate(anyString(), anyString());
    }

    @Test
    void testGetExchangeRate_UnsupportedTargetCurrency() {
        String baseCurrency = "USD";
        String targetCurrency = "XYZ";

        UnsupportedCurrencyException exception = assertThrows(
                UnsupportedCurrencyException.class,
                () -> currencyExchangeService.getExchangeRate(baseCurrency, targetCurrency)
        );

        assertEquals("BaseCurrency: XYZ is not supported", exception.getMessage());
        verify(exchangeRateClient, never()).getExchangeRate(anyString(), anyString());
    }
}
