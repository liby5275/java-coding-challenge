package com.crewmeister.cmcodingchallenge.currency.service;

import java.io.IOException;

public interface CurrencyDataRefresherService {

    public void getLatestCurrencyExchangeData() throws IOException;
}
