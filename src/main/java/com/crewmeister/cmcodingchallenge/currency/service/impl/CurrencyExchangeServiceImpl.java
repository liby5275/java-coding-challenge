package com.crewmeister.cmcodingchallenge.currency.service.impl;

import com.crewmeister.cmcodingchallenge.currency.cache.CurrencyExchangeRateCacheService;
import com.crewmeister.cmcodingchallenge.currency.entity.CurrencyExchange;
import com.crewmeister.cmcodingchallenge.currency.model.domain.CurrencyExchangeDTO;
import com.crewmeister.cmcodingchallenge.currency.model.response.CurrencyConversionResponse;
import com.crewmeister.cmcodingchallenge.currency.model.response.CurrencyRatesResponse;
import com.crewmeister.cmcodingchallenge.currency.model.response.GetCurrenciesResponse;
import com.crewmeister.cmcodingchallenge.currency.repo.CurrencyExchangeDataRepo;
import com.crewmeister.cmcodingchallenge.currency.service.CurrencyExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.crewmeister.cmcodingchallenge.currency.exception.CurrencyExchangeAppExceptions.*;
import static com.crewmeister.cmcodingchallenge.currency.constants.CurrencyConstants.*;
import static com.crewmeister.cmcodingchallenge.currency.utils.ValidationUtil.*;

/**
 * Service class to perform below services
 * 1. Fetch all currencies from the DB
 * 2. Fetch and construct and collection of all exchange rates for each of the dates
 *  present in the DB/cache
 * 3. Fetch and return exchange rate of all currencies on a given date
 * 4. Convert the given currency amount to EUR
 */
@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final CurrencyExchangeDataRepo currencyExchangeDataRepo;

    private final CurrencyExchangeRateCacheService currencyExchangeRateCacheService;

    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeServiceImpl.class);

    public CurrencyExchangeServiceImpl(CurrencyExchangeDataRepo currencyExchangeDataRepo,
                                       CurrencyExchangeRateCacheService currencyExchangeRateCacheService) {
        this.currencyExchangeDataRepo = currencyExchangeDataRepo;
        this.currencyExchangeRateCacheService = currencyExchangeRateCacheService;
    }

    /**
     * Method to Fetch and construct collection of all exchange rates  for each of the date
     * present in the DB
     * @return CurrencyRatesResponse
     */
    @Override
    public List<CurrencyRatesResponse> getAllCurrencyExchangeRates() {

        logger.info("At the service layer to fetch all the currency exchange rates from the DB");
        List<CurrencyRatesResponse> currencyRatesResponseList = null;
        Map<Date, List<CurrencyExchangeDTO>> exchangeRatesMappedToDate = null;

        logger.info("About to check whether the cache is empty or not." +
                " if cache is not empty, it mean it contains all the data from DB");

        if(currencyExchangeRateCacheService.isCacheEntriesPresent()){

            logger.info("Cache is not empty and it does contain all the exchange rates");
            exchangeRatesMappedToDate = currencyExchangeRateCacheService.getCachedCurrencyExchangeRatesCopy();

        } else {

            logger.info("Cache is empty. so need to get exchange rates from DB and construct it ");
            Optional<List<CurrencyExchange>> exchangeRatesFromDB = currencyExchangeDataRepo.findAllByOrderByDateAsc();
            exchangeRatesMappedToDate = validateBuildAndMapExchangeRatesToDate(exchangeRatesFromDB);

            logger.info("Adding exchange rates received from DB to the cache");
            currencyExchangeRateCacheService.addToLiveCache(exchangeRatesMappedToDate);

        }

        if(null != exchangeRatesMappedToDate && exchangeRatesMappedToDate.size() > 0){

            currencyRatesResponseList = exchangeRatesMappedToDate.entrySet().stream()
                    .map( entry -> new CurrencyRatesResponse(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
        }

        return currencyRatesResponseList;
    }


    /**
     * Method to Fetch and return exchange rate of all currencies on a given date
     * @param dateText
     * @return CurrencyRatesResponse
     */
    @Override
    public List<CurrencyRatesResponse> getCurrencyExchangeRateByDate(String dateText) {

        Date date = validateAndParseInputDate(dateText);
        logger.info("About to get the exchange rate of the date {}", date);
        List<CurrencyRatesResponse> currencyRatesResponseList = null;

        logger.info("About to check the cache for the exchange rates on the given date");
        if(currencyExchangeRateCacheService.isCacheEntriesPresent()){

            logger.info("Cache is present. Which must be the exact copy of the DB");
            List<CurrencyExchangeDTO> exchangeRates = currencyExchangeRateCacheService.getExchangeRatesOnADate(date);
            currencyRatesResponseList = new ArrayList<>();
            currencyRatesResponseList.add(new CurrencyRatesResponse(exchangeRates.get(0).getDate(),exchangeRates));

        } else {

            logger.info("Cache is empty. so need to get exchange rates from DB and construct it ");
            Optional<List<CurrencyExchange>> exchangeRatesOptional = currencyExchangeDataRepo.findByDate(date);
            Map<Date, List<CurrencyExchangeDTO>> exchangeRatesMappedToDate =
                    validateBuildAndMapExchangeRatesToDate(exchangeRatesOptional);

            if(null != exchangeRatesMappedToDate && exchangeRatesMappedToDate.size() > 0){

                currencyRatesResponseList = exchangeRatesMappedToDate.entrySet().stream()
                        .map( entry -> new CurrencyRatesResponse(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList());
            }

        }

        return currencyRatesResponseList;

    }


    /**
     * Method to  Fetch all currencies from the DB
     * @return GetCurrenciesResponse
     */
    @Override
    public GetCurrenciesResponse getCurrenciesList() {

        logger.info("At the service layer to list out all the currencies");
        GetCurrenciesResponse getCurrenciesResponse = null;
        Set<String> currenciesSet;

        if(currencyExchangeRateCacheService.isCachedCurrenciesFound()){

            logger.info("All the currencies list are already cached");
            currenciesSet = currencyExchangeRateCacheService.getCachedCurrenciesCopy();
            getCurrenciesResponse = new GetCurrenciesResponse();
            getCurrenciesResponse.setCurrencies(new ArrayList<>(currenciesSet));

        } else {

            logger.info("currencies list not found in the cache. Hence fetching from DB");
            Optional<List<String>> currenciesOptional = currencyExchangeDataRepo.fetchCurrencies();
            if(currenciesOptional.isPresent()){

                logger.info("total of {} currencies found from the DB",currenciesOptional.get().size());
                getCurrenciesResponse = new GetCurrenciesResponse();
                getCurrenciesResponse.setCurrencies(currenciesOptional.get());

                logger.info("About to save the currencies set to the cache");
                currencyExchangeRateCacheService.addCurrencyToCache(new HashSet<>(currenciesOptional.get()));

            } else {
                throw new FetchExchangeRatesException(NO_CURRENCY_FOUND,HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }


        return getCurrenciesResponse;
    }


    /**
     * Method to convert the given currency amount to EUR
     * @param currency
     * @param dateText
     * @param amount
     * @return CurrencyConversionResponse
     */
    @Override
    public CurrencyConversionResponse getConvertedEuroAmount(String currency, String dateText, double amount) {

        logger.info("At the service layer to convert the give foreign currency amount to EURO");
        Date date = validateAndParseInputDate(dateText);
        validateCurrencyInputs(currency, amount);

        CurrencyConversionResponse convertedAmountResponse = null;

        Optional<CurrencyExchange> currencyExchangeDate = currencyExchangeDataRepo.
                findByCurrencyAndDate(currency,date);

        if(currencyExchangeDate.isPresent()){

            logger.info("valid exchange rate for the currency {} is found for the date {}", currency, date);
            double exchangeRate = currencyExchangeDate.get().getExchangeValue();

            if(exchangeRate > 0){
                double convertedAmount = amount/exchangeRate;
                convertedAmountResponse = new CurrencyConversionResponse("EUR",convertedAmount);

            } else{
                throw new FetchExchangeRatesException(INVALID_EXCHANGE_RATE_FOUND,
                        HttpStatus.INTERNAL_SERVER_ERROR.value());
            }

        } else {
            throw new FetchExchangeRatesException(NO_RATE_FOUND_FOR_GIVEN_DATE_AND_CURRENCY,
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        logger.info("converted amount in EUR is {}",convertedAmountResponse);
        return convertedAmountResponse;
    }



    /**
     * Method to iterate the entries from DB and then construct a collection.
     * A collection will be constructed based on the date. ie, all the rates corresponding
     * to each of the dates will be displayed
     * @param exchangeRates
     * @return
     */
    private  Map<Date, List<CurrencyExchangeDTO>> validateBuildAndMapExchangeRatesToDate(Optional<List<CurrencyExchange>> exchangeRates) {

        logger.info("About to build the exchange rate responses from the data received from DB");
        Map<Date, List<CurrencyExchangeDTO>> exchangeRatesMappedToDate = null;

        if(exchangeRates.isPresent()){

            List<CurrencyExchange> currencyExchangeList = exchangeRates.get();
            logger.info("total of {} exchange rate entries found",currencyExchangeList.size());

            //Map which contains exchange rates data for each of the date.
            exchangeRatesMappedToDate = buildRatesCorrespondingToEachDate(currencyExchangeList);

        } else {

            throw new FetchExchangeRatesException(NO_EXCHANGE_RATES_FOUND,
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        logger.info("completed cmapping each date against the exchange rates");
        return exchangeRatesMappedToDate;

    }


    /**
     * Method which will construct a map which maps each date against the exchange data
     * of different currencies on that date
     * @param currencyExchangeList
     * @return
     */
    private Map<Date, List<CurrencyExchangeDTO>> buildRatesCorrespondingToEachDate(List<CurrencyExchange> currencyExchangeList){

        logger.info("About to build rates corresponding to each date");
        Map<Date, List<CurrencyExchangeDTO>> exchangeRatesMappedToDate = new LinkedHashMap<>();

        for(CurrencyExchange currencyExchange : currencyExchangeList){

            List<CurrencyExchangeDTO> exchangeDTOS = null;

            if(exchangeRatesMappedToDate.containsKey(currencyExchange.getDate())){
                exchangeDTOS= exchangeRatesMappedToDate.get(currencyExchange.getDate());
            } else {
                exchangeDTOS = new ArrayList<>();
            }

            CurrencyExchangeDTO currencyExchangeDTO =  new CurrencyExchangeDTO(currencyExchange.getCurrency(),
                    currencyExchange.getExchangeValue(), currencyExchange.getDate(),
                    currencyExchange.getPercentageExchange());
            exchangeDTOS.add(currencyExchangeDTO);
            exchangeRatesMappedToDate.put(currencyExchange.getDate(),exchangeDTOS);
        }

        logger.info("Total of {} different date and its corresponding rates are constructed",
                exchangeRatesMappedToDate.size());
        return exchangeRatesMappedToDate;
    }




}
