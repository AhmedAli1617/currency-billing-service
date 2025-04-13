package com.assessment.service;

import com.assessment.model.BillRequest;
import com.assessment.model.BillResponse;
import com.assessment.model.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BillService {
    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    private DiscountService discountService;

    public BillResponse calculateNetPayable(BillRequest request) {
        log.info("Starting bill calculation for request: {}", request);

        double totalAmount = request.getItems().stream()
                .mapToDouble(Item::getPrice)
                .sum();
        log.debug("Total amount before discount: {}", totalAmount);

        double discount = discountService.calculateDiscount(
                request.getItems(),
                request.getUserType(),
                request.getCustomerTenure()
        );
        log.debug("Calculated discount: {}", discount);

        double amountAfterDiscount = totalAmount - discount;
        log.debug("Amount after discount: {}", amountAfterDiscount);

        double exchangeRate = currencyExchangeService.getExchangeRate(
                request.getOriginalCurrency(),
                request.getTargetCurrency()
        );
        log.debug("Exchange rate from {} to {}: {}",
                request.getOriginalCurrency(),
                request.getTargetCurrency(),
                exchangeRate
        );

        double netPayable = amountAfterDiscount * exchangeRate;
        log.info("Net payable amount in {}: {}", request.getTargetCurrency(), netPayable);

        BillResponse response = new BillResponse();
        response.setNetPayableAmount(netPayable);
        response.setCurrency(request.getTargetCurrency());

        log.info("Completed bill calculation. Response: {}", response);
        return response;
    }
}
