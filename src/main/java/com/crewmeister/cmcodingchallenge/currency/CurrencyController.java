package com.crewmeister.cmcodingchallenge.currency;

/*import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;*/
import com.crewmeister.cmcodingchallenge.currency.service.CurrencyDataRefresherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController()
@RequestMapping("/api")
public class CurrencyController {


    @Autowired
    private CurrencyDataRefresherService currencyDataRefresherService;



   /* @GetMapping("/currencies")
    public ResponseEntity<ArrayList<CurrencyConversionRates>> getCurrencies() {
        ArrayList<CurrencyConversionRates> currencyConversionRates = new ArrayList<CurrencyConversionRates>();
        currencyConversionRates.add(new CurrencyConversionRates(2.5));

        return new ResponseEntity<ArrayList<CurrencyConversionRates>>(currencyConversionRates, HttpStatus.OK);
    }*/

        @GetMapping("/demo")
        public void demo() throws IOException {
            currencyDataRefresherService.getLatestCurrencyExchangeData();
        }

}
