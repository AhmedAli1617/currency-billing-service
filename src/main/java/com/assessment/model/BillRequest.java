package com.assessment.model;

import com.assessment.enums.UserType;
import lombok.Data;

import java.util.List;

@Data
public class BillRequest {
    private List<Item> items;
    private UserType userType;
    private int customerTenure; // in years
    private String originalCurrency;
    private String targetCurrency;
    // Getters and Setters
}
