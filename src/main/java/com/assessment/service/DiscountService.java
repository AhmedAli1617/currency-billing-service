package com.assessment.service;

import com.assessment.enums.UserType;
import com.assessment.model.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DiscountService {
    public double calculateDiscount(List<Item> items, UserType userType, int customerTenure) {
        double total = items.stream().mapToDouble(Item::getPrice).sum();
        double nonGroceryTotal = items.stream()
                .filter(item -> !item.getCategory().equalsIgnoreCase("Groceries"))
                .mapToDouble(Item::getPrice)
                .sum();

        log.debug("Total bill amount: {}", total);
        log.debug("Total non-grocery amount: {}", nonGroceryTotal);
        log.debug("User type: {}, Customer tenure: {} years", userType, customerTenure);

        double percentageDiscount = 0;
        if (userType == UserType.EMPLOYEE) {
            percentageDiscount = 0.30;
            log.debug("Applying 30% employee discount");
        } else if (userType == UserType.AFFILIATE) {
            percentageDiscount = 0.10;
            log.debug("Applying 10% affiliate discount");
        } else if (userType == UserType.CUSTOMER && customerTenure > 2) {
            percentageDiscount = 0.05;
            log.debug("Applying 5% long-term customer discount");
        } else {
            log.debug("No percentage discount applicable");
        }

        double discount = nonGroceryTotal * percentageDiscount;
        double flatDiscount = ((int) (total / 100)) * 5;
        discount += flatDiscount;

        log.info("Percentage-based discount: {}", nonGroceryTotal * percentageDiscount);
        log.info("Flat discount ($5 per $100): {}", flatDiscount);
        log.info("Total discount applied: {}", discount);

        return discount;}
}
