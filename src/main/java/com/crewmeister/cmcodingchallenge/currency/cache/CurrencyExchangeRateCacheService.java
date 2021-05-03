package com.crewmeister.cmcodingchallenge.currency.cache;

import com.crewmeister.cmcodingchallenge.currency.model.domain.CurrencyExchangeDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CurrencyExchangeRateCacheService {

    public void addToLiveCache(Map<Date, List<CurrencyExchangeDTO>> exchangeRatesMappedToDate);

    public Map<Date, List<CurrencyExchangeDTO>> getCachedCurrencyExchangeRatesCopy();

    public boolean isCacheEntriesPresent();

    public List<CurrencyExchangeDTO> getExchangeRatesOnADate(Date date);

    public boolean clearCache();

    public void addCurrencyToCache(String currency);

    public void addCurrencyToCache(Set<String> currencies);

    public boolean isCachedCurrenciesFound();

    public Set<String> getCachedCurrenciesCopy();

}
