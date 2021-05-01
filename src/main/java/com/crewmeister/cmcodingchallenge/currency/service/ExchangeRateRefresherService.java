package com.crewmeister.cmcodingchallenge.currency.service;

import java.io.IOException;

public interface ExchangeRateRefresherService {

    public void getLatestCurrencyExchangeData() throws IOException;
}
