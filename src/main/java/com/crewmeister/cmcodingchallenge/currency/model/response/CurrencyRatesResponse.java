package com.crewmeister.cmcodingchallenge.currency.model.response;

import com.crewmeister.cmcodingchallenge.currency.model.domain.CurrencyExchangeDTO;

import java.util.Date;
import java.util.List;

public class CurrencyRatesResponse {

    private Date date;
    private List<CurrencyExchangeDTO> currencyExchangeRates;

    public CurrencyRatesResponse() {
    }

    public CurrencyRatesResponse(Date date, List<CurrencyExchangeDTO> currencyExchangeRates) {
        this.date = date;
        this.currencyExchangeRates = currencyExchangeRates;
    }

    public List<CurrencyExchangeDTO> getCurrencyExchangeRates() {
        return currencyExchangeRates;
    }

    public void setCurrencyExchangeRates(List<CurrencyExchangeDTO> currencyExchangeRates) {
        this.currencyExchangeRates = currencyExchangeRates;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}


