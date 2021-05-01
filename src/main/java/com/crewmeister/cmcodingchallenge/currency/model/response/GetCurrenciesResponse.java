package com.crewmeister.cmcodingchallenge.currency.model.response;

import java.util.List;

public class GetCurrenciesResponse {

    private List<String> currencies;

    public List<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<String> currencies) {
        this.currencies = currencies;
    }
}
