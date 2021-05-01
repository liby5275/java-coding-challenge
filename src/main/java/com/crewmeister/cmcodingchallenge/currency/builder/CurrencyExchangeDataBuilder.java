package com.crewmeister.cmcodingchallenge.currency.builder;

import com.crewmeister.cmcodingchallenge.currency.model.domain.CurrencyExchangeDTO;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class CurrencyExchangeDataBuilder {

    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeDataBuilder.class);

    private CurrencyExchangeDTO currencyExchangeDTO;

    public CurrencyExchangeDataBuilder(String currency) {
        this.currencyExchangeDTO = new CurrencyExchangeDTO();
        this.currencyExchangeDTO.setCurrency(currency);
    }

    public CurrencyExchangeDataBuilder withValue(Elements date){
        logger.info("adding exchange value to the DTO");
        //this.currencyExchangeDTO.setValue(value);
        return this;
    }

    public CurrencyExchangeDataBuilder withDate(Elements data){
        logger.info("adding Date to the DTO");

        this.currencyExchangeDTO.setDate(new Date());
        return this;
    }

    public CurrencyExchangeDataBuilder withPercentageChange(Elements data){
        logger.info("adding percentageChange to the DTO");
        //this.currencyExchangeDTO.setPercentageChange(percentageChange);
        return this;
    }


    public CurrencyExchangeDTO build(){
        return this.currencyExchangeDTO;
    }

}
