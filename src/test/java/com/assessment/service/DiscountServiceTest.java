package com.assessment.service;

import com.assessment.enums.UserType;
import com.assessment.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountServiceTest {

    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        discountService = new DiscountService();
    }

    @Test
    void testCalculateDiscountForEmployee() {
        List<Item> items = Arrays.asList(
                new Item("Laptop", "Electronics",1000.0),
                new Item("Apples",  "Groceries",200.0)
        );
        double discount = discountService.calculateDiscount(items, UserType.EMPLOYEE, 1);
        double expectedPercentageDiscount = 1000.0 * 0.30; // 30% of non-grocery items
        double expectedFlatDiscount = ((int) ((1000.0 + 200.0) / 100)) * 5; // $5 per $100
        double expectedTotalDiscount = expectedPercentageDiscount + expectedFlatDiscount;
        assertEquals(expectedTotalDiscount, discount, 0.001);
    }

    @Test
    void testCalculateDiscountForAffiliate() {
        List<Item> items = Arrays.asList(
                new Item("TV", "Electronics", 2000.0),
                new Item("Bread", "Groceries", 100.0)
        );

        // Act: Calculate the discount for an affiliate user with 1 year of customer tenure
        double discount = discountService.calculateDiscount(items, UserType.AFFILIATE, 1);

        // Calculate expected discount values
        double nonGroceryTotal = 2000.0; // Only "TV" is non-grocery
        double percentageDiscount = nonGroceryTotal * 0.10; // 10% discount for affiliates
        double totalAmount = nonGroceryTotal + 100.0; // Total amount including groceries
        double flatDiscount = (int) (totalAmount / 100) * 5; // $5 discount per $100 spent
        double expectedTotalDiscount = percentageDiscount + flatDiscount;

        // Assert: Verify that the calculated discount matches the expected total discount
        assertEquals(expectedTotalDiscount, discount, 0.001);
    }

    @Test
    void testCalculateDiscountForLongTermCustomer() {
        // Arrange: Create a list of items
        List<Item> items = Arrays.asList(
                new Item("Phone", "Electronics", 800.0),
                new Item("Milk", "Groceries", 100.0)
        );

        // Act: Calculate the discount for a customer with 3 years of tenure
        double discount = discountService.calculateDiscount(items, UserType.CUSTOMER, 3);

        // Calculate expected discount values
        double nonGroceryTotal = 800.0; // Only "Phone" is non-grocery
        double percentageDiscount = nonGroceryTotal * 0.05; // 5% discount for customers with more than 2 years of tenure
        double totalAmount = nonGroceryTotal + 100.0; // Total amount including groceries
        double flatDiscount = (int) (totalAmount / 100) * 5; // $5 discount per $100 spent
        double expectedTotalDiscount = percentageDiscount + flatDiscount;

        // Assert: Verify that the calculated discount matches the expected total discount
        assertEquals(expectedTotalDiscount, discount, 0.001);
    }

    @Test
    void testCalculateDiscountForNewCustomer() {
        List<Item> items = Arrays.asList(
                new Item("Headphones","Electronics", 300.0 ),
                new Item("Bananas", "Groceries",50.0 )
        );
        double discount = discountService.calculateDiscount(items, UserType.CUSTOMER, 1);
        double expectedPercentageDiscount = 0.0; // No percentage discount
        double expectedFlatDiscount = ((int) ((300.0 + 50.0) / 100)) * 5; // $5 per $100
        double expectedTotalDiscount = expectedPercentageDiscount + expectedFlatDiscount;
        assertEquals(expectedTotalDiscount, discount, 0.001);
    }

    @Test
    void testCalculateDiscountWithOnlyGroceries() {
        List<Item> items = Arrays.asList(
                new Item("Rice", "Groceries",100.0),
                new Item("Beans", "Groceries",150.0)
        );
        double discount = discountService.calculateDiscount(items, UserType.EMPLOYEE, 5);
        double expectedPercentageDiscount = 0.0; // No discount on groceries
        double expectedFlatDiscount = ((int) ((100.0 + 150.0) / 100)) * 5; // $5 per $100
        double expectedTotalDiscount = expectedPercentageDiscount + expectedFlatDiscount;
        assertEquals(expectedTotalDiscount, discount, 0.001);
    }
}
