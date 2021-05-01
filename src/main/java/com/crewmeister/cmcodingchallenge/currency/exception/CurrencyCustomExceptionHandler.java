package com.crewmeister.cmcodingchallenge.currency.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static com.crewmeister.cmcodingchallenge.currency.exception.CurrencyExchangeAppException.*;

@ControllerAdvice
public class CurrencyCustomExceptionHandler {

    Logger logger = LoggerFactory.getLogger(CurrencyCustomExceptionHandler.class);

    @ExceptionHandler(RefreshBatchException.class)
    public @ResponseBody CustomExceptionResponse handleRefreshVatchExceptions(final RefreshBatchException exception) {
        logger.warn("Exception occurred during refresh data service ");
        return new CustomExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(), null);
    }

    @ExceptionHandler(FetchExchangeRatesException.class)
    public @ResponseBody CustomExceptionResponse handleFetchExchangeRatesExceptions(final FetchExchangeRatesException exception) {
        logger.warn("Exception occurred while fetching exchange data from DB ");
        logger.warn("Exception message is {}", exception.getMessage());
        return new CustomExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(), null);
    }

    @ExceptionHandler(InvalidInputException.class)
    public @ResponseBody CustomExceptionResponse handleInvalidInputExceptions(final InvalidInputException exception) {
        logger.warn("Invalid input supplied to the service ");
        logger.warn("Exception message is {}", exception.getMessage());
        return new CustomExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(), null);
    }

}
