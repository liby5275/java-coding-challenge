package com.crewmeister.cmcodingchallenge.currency.builder;

import com.crewmeister.cmcodingchallenge.currency.model.domain.CurrencyExchangeDTO;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrencyExchangeDataBuilder {

    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeDataBuilder.class);

    private CurrencyExchangeDTO currencyExchangeDTO;

    public CurrencyExchangeDataBuilder(String currency) {
        this.currencyExchangeDTO = new CurrencyExchangeDTO();
        this.currencyExchangeDTO.setCurrency(currency);
    }

    public CurrencyExchangeDataBuilder withDate(Elements exchangeData) {

        logger.info("adding exchange value to the DTO");

        if(isValidCurrencyDataColumnFound(exchangeData,0)){

            String dateText = exchangeData.get(0).text();
            try {
                Date exchangeRateDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateText);
                logger.info("Valid date in correct format found {}",exchangeRateDate);
                this.currencyExchangeDTO.setDate(exchangeRateDate);
            } catch (ParseException e) {
                logger.warn("Invalid date format found. Unable to parse the date");
            }
        }

        return this;
    }

    public CurrencyExchangeDataBuilder withValue(Elements exchangeData) {

        logger.info("adding Date to the DTO");

        if(isValidCurrencyDataColumnFound(exchangeData,1)){

            try{
                Double value = Double.parseDouble(exchangeData.get(1).text());
                this.currencyExchangeDTO.setValue(value);
            } catch (NumberFormatException exception) {
                logger.warn("Invalid double data format: {}",exchangeData.get(1).text());
            }

        }

        return this;
    }

    public CurrencyExchangeDataBuilder withPercentageChange(Elements exchangeData) {

        logger.info("adding percentageChange to the DTO");

        if(isValidCurrencyDataColumnFound(exchangeData,3)){

            try{
                Double percentageChange = Double.parseDouble(exchangeData.get(3).text());
                this.currencyExchangeDTO.setPercentageChange(percentageChange);
            } catch (NumberFormatException exception) {
                logger.warn("Invalid percentageChange data format: {}",exchangeData.get(3).text());
            }

        }

        return this;
    }

    private boolean isValidCurrencyDataColumnFound(Elements exchangeData, int index) {
        return null != exchangeData
                && null != exchangeData.get(index)
                && null != exchangeData.get(index).text();
    }


    public CurrencyExchangeDTO build(){
        return this.currencyExchangeDTO;
    }

}
