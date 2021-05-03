package com.crewmeister.cmcodingchallenge.currency.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.crewmeister.cmcodingchallenge.currency.constants.CurrencyConstants.*;
import static com.crewmeister.cmcodingchallenge.currency.exception.CurrencyExchangeAppExceptions.InvalidInputException;

public class ValidationUtil {

    private static Logger logger = LoggerFactory.getLogger(ValidationUtil.class);


    private ValidationUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Method to validate the input date
     * @param dateText
     * @return Java Util Date if valid string, else exception
     */
    public static Date validateAndParseInputDate(String dateText) {

        logger.info("About to validate and parse the incoming input date string {}", dateText);
        try {
            Date exchangeRateDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateText);
            logger.info("Valid date in correct format found {}",exchangeRateDate);
            return exchangeRateDate;
        } catch (ParseException e) {
            throw new InvalidInputException(INVALID_DATE_INPUT, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


    /**
     *  Method to validate the input currency
     * @param currency
     */
    public static void validateCurrencyInputs(String currency, double amount){

        logger.info("About to validate the input currency code passed");
        if(null == currency || currency.length() != 3){
            throw new InvalidInputException(INVALID_CURRENCY_INPUT, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        logger.info("About to validate the input amount passed");
        if(amount <= 0) {
            throw new InvalidInputException(INVALID_AMOUNT_INPUT, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}
