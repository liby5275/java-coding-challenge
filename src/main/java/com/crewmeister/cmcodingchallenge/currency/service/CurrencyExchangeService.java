package com.crewmeister.cmcodingchallenge.currency.service;

import com.crewmeister.cmcodingchallenge.currency.model.response.CurrencyConversionResponse;
import com.crewmeister.cmcodingchallenge.currency.model.response.CurrencyRatesResponse;
import com.crewmeister.cmcodingchallenge.currency.model.response.GetCurrenciesResponse;

import java.util.List;

public interface CurrencyExchangeService {

    public List<CurrencyRatesResponse> getAllCurrencyExchangeRates();

    public List<CurrencyRatesResponse> getCurrencyExchangeRateByDate(String date);

    public GetCurrenciesResponse getCurrenciesList();

    public CurrencyConversionResponse getConvertedEuroAmount(String currency, String dateText, double amount);
}
