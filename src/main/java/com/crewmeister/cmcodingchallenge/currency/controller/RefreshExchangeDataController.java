package com.crewmeister.cmcodingchallenge.currency.controller;


import com.crewmeister.cmcodingchallenge.currency.service.ExchangeRateRefresherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/refresh")
public class RefreshExchangeDataController {

    Logger logger = LoggerFactory.getLogger(RefreshExchangeDataController.class);

    private final ExchangeRateRefresherService exchangeRateRefresherService;

    public RefreshExchangeDataController(ExchangeRateRefresherService exchangeRateRefresherService) {
        this.exchangeRateRefresherService = exchangeRateRefresherService;
    }


    @GetMapping
    public void refreshExchangeRates() throws IOException {

        logger.info("About to start the refreshing job of currency exchange rates");
        exchangeRateRefresherService.getLatestCurrencyExchangeData();
        logger.info("**********Completed refreshing the exchange data**********");
    }
}
