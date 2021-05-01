package com.crewmeister.cmcodingchallenge.currency.model.response;

import com.crewmeister.cmcodingchallenge.currency.model.domain.CurrencyExchangeDTO;

import java.util.List;

public class CurrencyRatesResponse {

    private List<CurrencyExchangeDTO> currencyExchangeRates;

    public List<CurrencyExchangeDTO> getCurrencyExchangeRates() {
        return currencyExchangeRates;
    }

    public void setCurrencyExchangeRates(List<CurrencyExchangeDTO> currencyExchangeRates) {
        this.currencyExchangeRates = currencyExchangeRates;
    }
}


