package com.assessment.model;

import lombok.Data;

@Data
public class BillResponse {
    private double netPayableAmount;
    private String currency;
    // Getters and Setters
}
