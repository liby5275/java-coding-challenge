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
    public @ResponseBody CustomExceptionResponse handleValidationExceptions
            (final RefreshBatchException exception) {
        logger.warn("Exception occurred during refresh data service ");
        return new CustomExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(), null);
    }
}
