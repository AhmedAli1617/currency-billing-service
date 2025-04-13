package com.assessment.service;

import com.assessment.enums.UserType;
import com.assessment.model.BillRequest;
import com.assessment.model.BillResponse;
import com.assessment.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BillServiceTest {

    @Mock
    private DiscountService discountService;

    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @InjectMocks
    private BillService billService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateNetPayable() {
        // Prepare test data
        Item item1 = new Item("Item1",  "Electronics",100.0);
        Item item2 = new Item("Item2", "Groceries",200.0 );
        List<Item> items = Arrays.asList(item1, item2);

        BillRequest request = new BillRequest();
        request.setItems(items);
        request.setUserType(UserType.EMPLOYEE);
        request.setCustomerTenure(3);
        request.setOriginalCurrency("USD");
        request.setTargetCurrency("PKR");

        // Mock dependencies
        when(discountService.calculateDiscount(items, UserType.EMPLOYEE, 3)).thenReturn(50.0);
        when(currencyExchangeService.getExchangeRate("USD", "PKR")).thenReturn(280.0);

        // Execute the method under test
        BillResponse response = billService.calculateNetPayable(request);

        // Calculate expected result
        double totalAmount = 100.0 + 200.0;
        double amountAfterDiscount = totalAmount - 50.0;
        double expectedNetPayable = amountAfterDiscount * 280.0;

        // Verify the result
        assertEquals(expectedNetPayable, response.getNetPayableAmount());
        assertEquals("PKR", response.getCurrency());
    }
}
