package com.crewmeister.cmcodingchallenge.currency.cache.impl;

import com.crewmeister.cmcodingchallenge.currency.cache.CurrencyExchangeRateCacheService;
import com.crewmeister.cmcodingchallenge.currency.model.domain.CurrencyExchangeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class CurrencyExchangeRateCacheServiceImpl implements CurrencyExchangeRateCacheService {

    Logger logger = LoggerFactory.getLogger(CurrencyExchangeRateCacheServiceImpl.class);

    private Map<Date,List<CurrencyExchangeDTO>> cachedExchangeRates = new ConcurrentHashMap<>();
    private Set<String> cachedCurrencies = new HashSet<>();


    @Override
    public void addToLiveCache(Map<Date, List<CurrencyExchangeDTO>> exchangeRatesMappedToDate) {

        logger.info("setting exchange rates to the cache");
        this.cachedExchangeRates = new ConcurrentHashMap<>(exchangeRatesMappedToDate);

    }

    @Override
    public Map<Date, List<CurrencyExchangeDTO>> getCachedCurrencyExchangeRatesCopy() {

        logger.info("returning a copy of the cache");
        return new ConcurrentHashMap<>(this.cachedExchangeRates);
    }

    @Override
    public boolean isCacheEntriesPresent() {
        return this.cachedExchangeRates.size() > 0 ;
    }

    @Override
    public List<CurrencyExchangeDTO> getExchangeRatesOnADate(Date date) {

        logger.info("At the cache layer to search and get the exchange rates for the date {}", date);
        return this.cachedExchangeRates.get(date);
    }

    @Override
    public boolean clearCache() {

        logger.info("About to clear the currency exchange cache");
        this.cachedExchangeRates.clear();
        this.cachedCurrencies.clear();
        return this.cachedExchangeRates.size() == 0 && this.cachedCurrencies.size() == 0;
    }

    @Override
    public void addCurrencyToCache(String currency) {

        logger.info("Adding a currency to the cachedCurrencies");
        this.cachedCurrencies.add(currency);
    }

    @Override
    public void addCurrencyToCache(Set<String> currencies) {

        logger.info("Adding currencies to the cache as batch");
        this.cachedCurrencies = new HashSet<>(currencies);

    }

    @Override
    public boolean isCachedCurrenciesFound() {
        return this.cachedCurrencies.size() > 0;
    }

    @Override
    public Set<String> getCachedCurrenciesCopy() {

        logger.info("returning a copy of the currency cache");
        return new HashSet<>(this.cachedCurrencies);
    }
}
