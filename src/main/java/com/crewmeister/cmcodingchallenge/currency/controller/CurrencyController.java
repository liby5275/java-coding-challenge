package com.crewmeister.cmcodingchallenge.currency.controller;


import com.crewmeister.cmcodingchallenge.currency.model.response.CurrencyConversionResponse;
import com.crewmeister.cmcodingchallenge.currency.model.response.CurrencyRatesResponse;
import com.crewmeister.cmcodingchallenge.currency.model.response.GetCurrenciesResponse;
import com.crewmeister.cmcodingchallenge.currency.service.CurrencyExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api")
public class CurrencyController {

    Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    private final CurrencyExchangeService currencyExchangeService;

    public CurrencyController(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }


    @GetMapping("/currencies")
    public ResponseEntity<GetCurrenciesResponse> getCurrencies() {

        logger.info("At the beginning of fetching all currencies");
        GetCurrenciesResponse getCurrenciesResponse = currencyExchangeService.getCurrenciesList();

        if(null != getCurrenciesResponse){
            logger.info("Returning currencies list of size {}",getCurrenciesResponse.getCurrencies().size());
            return  new ResponseEntity<>(getCurrenciesResponse, HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/rates")
    public ResponseEntity<List<CurrencyRatesResponse>> getCurrencyExchangeRates() {

        logger.info("At the beginning of fetching all currency exchange rates");
        List<CurrencyRatesResponse> currencyRatesResponse = currencyExchangeService.
                getAllCurrencyExchangeRates();

        if(null != currencyRatesResponse){
            logger.info("returning exchange rates corresponding to each of the date");
            return  new ResponseEntity<>(currencyRatesResponse, HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/rates/{date}")
    public ResponseEntity<List<CurrencyRatesResponse>> getCurrencyExchangeRates(@PathVariable String date) {

        logger.info("At the beginning of fetching all currency exchange for date {}",date);
        List<CurrencyRatesResponse> currencyRatesResponse = currencyExchangeService.
                getCurrencyExchangeRateByDate(date);

        if(null != currencyRatesResponse){
            return  new ResponseEntity<>(currencyRatesResponse, HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/convert")
    public ResponseEntity<CurrencyConversionResponse> getConvertedEuroAmount(@RequestParam(required = true) String currency,
                                                                             @RequestParam(required = true) String date,
                                                                             @RequestParam(required = true) double amount) {

        logger.info("About to convert the given amount of {} to Euro",currency);

        CurrencyConversionResponse convertedAmount = currencyExchangeService.
                getConvertedEuroAmount(currency, date, amount);

        if(null != convertedAmount ){
            return  new ResponseEntity<>(convertedAmount, HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
