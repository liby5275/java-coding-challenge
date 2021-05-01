package com.crewmeister.cmcodingchallenge.currency.service;

import com.crewmeister.cmcodingchallenge.currency.model.response.CurrencyRatesResponse;
import com.crewmeister.cmcodingchallenge.currency.model.response.GetCurrenciesResponse;

import java.util.Date;
import java.util.List;

public interface CurrencyExchangeService {

    public CurrencyRatesResponse getAllCurrencyExchangeRates();

    public CurrencyRatesResponse getCurrencyExchangeRateByDate(String date);

    public GetCurrenciesResponse getCurrenciesList();
}
