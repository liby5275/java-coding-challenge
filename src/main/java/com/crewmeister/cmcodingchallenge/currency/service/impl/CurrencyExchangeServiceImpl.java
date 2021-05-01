package com.crewmeister.cmcodingchallenge.currency.service.impl;

import com.crewmeister.cmcodingchallenge.currency.entity.CurrencyExchange;
import com.crewmeister.cmcodingchallenge.currency.model.domain.CurrencyExchangeDTO;
import com.crewmeister.cmcodingchallenge.currency.model.response.CurrencyRatesResponse;
import com.crewmeister.cmcodingchallenge.currency.model.response.GetCurrenciesResponse;
import com.crewmeister.cmcodingchallenge.currency.repo.CurrencyExchangeDataRepo;
import com.crewmeister.cmcodingchallenge.currency.service.CurrencyExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.crewmeister.cmcodingchallenge.currency.exception.CurrencyExchangeAppException.*;
import static com.crewmeister.cmcodingchallenge.currency.constants.CurrencyConstants.*;


@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final CurrencyExchangeDataRepo currencyExchangeDataRepo;

    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeServiceImpl.class);

    public CurrencyExchangeServiceImpl(CurrencyExchangeDataRepo currencyExchangeDataRepo) {
        this.currencyExchangeDataRepo = currencyExchangeDataRepo;
    }

    @Override
    public CurrencyRatesResponse getAllCurrencyExchangeRates() {

        logger.info("At the service layer to fetch all the currency exchange rates from the DB");
        List<CurrencyExchange> exchangeRates = currencyExchangeDataRepo.findAll();
        return validateAndBuildExchangeRates(Optional.ofNullable(exchangeRates));
    }

    @Override
    public CurrencyRatesResponse getCurrencyExchangeRateByDate(String dateText) {

        Date date = validateAndParseInputDate(dateText);
        logger.info("About to get the exchange rate of the date {}", date);

        Optional<List<CurrencyExchange>> exchangeRatesOptional = currencyExchangeDataRepo.findByDate(date);
        return validateAndBuildExchangeRates(exchangeRatesOptional);

    }

    @Override
    public GetCurrenciesResponse getCurrenciesList() {

        logger.info("At the service layer to list out all the currencies");
        Optional<List<String>> currenciesOptional = currencyExchangeDataRepo.fetchCurrencies();
        GetCurrenciesResponse getCurrenciesResponse = null;

        if(currenciesOptional.isPresent()){

            logger.info("total of {} currencies found from the DB",currenciesOptional.get().size());
            getCurrenciesResponse = new GetCurrenciesResponse();
            getCurrenciesResponse.setCurrencies(currenciesOptional.get());

        } else {
            throw new FetchExchangeRatesException(NO_CURRENCY_FOUND,HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return getCurrenciesResponse;
    }

    private Date validateAndParseInputDate(String dateText) {

        logger.info("About to validate and parse the incoming input date string {}", dateText);
        try {
            Date exchangeRateDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateText);
            logger.info("Valid date in correct format found {}",exchangeRateDate);
            return exchangeRateDate;
        } catch (ParseException e) {
            throw new InvalidInputException(INVALID_DATE_INPUT,HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private CurrencyRatesResponse validateAndBuildExchangeRates(Optional<List<CurrencyExchange>> exchangeRates) {

        logger.info("About to build the exchange rate responses from the data received from DB");
        CurrencyRatesResponse currencyRatesResponse = null;

        if(exchangeRates.isPresent()){

            List<CurrencyExchange> currencyExchangeList = exchangeRates.get();
            logger.info("total of {} exchange rate entries found",currencyExchangeList.size());

            List<CurrencyExchangeDTO> exchangeDTOS = currencyExchangeList.stream().
                    map(rate -> new CurrencyExchangeDTO(rate.getCurrency(),
                    rate.getExchangeValue(), rate.getDate(), rate.getExchangeValue())).
                    collect(Collectors.toList());

            currencyRatesResponse = new CurrencyRatesResponse();
            currencyRatesResponse.setCurrencyExchangeRates(exchangeDTOS);

        } else {

            throw new FetchExchangeRatesException(NO_EXCHANGE_RATES_FOUND,
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        logger.info("completed constructing the response for fetch currency rates");
        return currencyRatesResponse;

    }


}
