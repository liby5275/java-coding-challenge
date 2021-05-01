package com.crewmeister.cmcodingchallenge.currency.controller;

/*import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;*/
import com.crewmeister.cmcodingchallenge.currency.model.response.CurrencyRatesResponse;
import com.crewmeister.cmcodingchallenge.currency.model.response.GetCurrenciesResponse;
import com.crewmeister.cmcodingchallenge.currency.service.CurrencyExchangeService;
import com.crewmeister.cmcodingchallenge.currency.service.ExchangeRateRefresherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController()
@RequestMapping("/api")
public class CurrencyController {

    Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    private final CurrencyExchangeService currencyExchangeService;

    @Autowired
    private ExchangeRateRefresherService exchangeRateRefresherService;

    public CurrencyController(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }


    @GetMapping("/currencies")
    public ResponseEntity<GetCurrenciesResponse> getCurrencies() {

        logger.info("At the beginning of fetching all currencies");
        GetCurrenciesResponse getCurrenciesResponse = currencyExchangeService.getCurrenciesList();

        if(null != getCurrenciesResponse){
            return  new ResponseEntity<>(getCurrenciesResponse, HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/rates")
    public ResponseEntity<CurrencyRatesResponse> getCurrencyExchangeRates() {
        logger.info("At the beginning of fetching all currency exchange rates");
        CurrencyRatesResponse currencyRatesResponse = currencyExchangeService.getAllCurrencyExchangeRates();

        if(null != currencyRatesResponse){
            return  new ResponseEntity<>(currencyRatesResponse, HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/rates/{date}")
    public ResponseEntity<CurrencyRatesResponse> getCurrencyExchangeRates(@PathVariable String date) {

        logger.info("At the beginning of fetching all currency exchange for date {}",date);
        CurrencyRatesResponse currencyRatesResponse = currencyExchangeService.
                getCurrencyExchangeRateByDate(date);

        if(null != currencyRatesResponse){
            return  new ResponseEntity<>(currencyRatesResponse, HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


   /* @GetMapping("/currencies")
    public ResponseEntity<ArrayList<CurrencyConversionRates>> getCurrencies() {
        ArrayList<CurrencyConversionRates> currencyConversionRates = new ArrayList<CurrencyConversionRates>();
        currencyConversionRates.add(new CurrencyConversionRates(2.5));

        return new ResponseEntity<ArrayList<CurrencyConversionRates>>(currencyConversionRates, HttpStatus.OK);
    }*/

        @GetMapping("/demo")
        public void demo() throws IOException {
            exchangeRateRefresherService.getLatestCurrencyExchangeData();
        }



}
