package com.assessment.controller;

import com.assessment.model.BillRequest;
import com.assessment.model.BillResponse;
import com.assessment.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api")
@Slf4j
public class BillController {
    @Autowired
    private BillService billService;



    @PostMapping("/calculate")
    public ResponseEntity<BillResponse> calculateBill(@RequestBody BillRequest request) {
        log.info("Received bill calculation request: {}", request);
        BillResponse response = billService.calculateNetPayable(request);
        log.info("Calculated bill response: {}", response);
        return ResponseEntity.ok(response);
    }
}
