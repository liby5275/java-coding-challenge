package com.crewmeister.cmcodingchallenge.currency.service.impl;

import com.crewmeister.cmcodingchallenge.currency.builder.CurrencyExchangeDataBuilder;
import com.crewmeister.cmcodingchallenge.currency.model.domain.CurrencyExchangeDTO;
import com.crewmeister.cmcodingchallenge.currency.service.CurrencyDataRefresherService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.crewmeister.cmcodingchallenge.currency.exception.CurrencyExchangeAppException.*;
import static com.crewmeister.cmcodingchallenge.currency.constants.CurrencyConstants.*;

@Service
public class CurrencyDataRefresherServiceImpl implements CurrencyDataRefresherService {

    @Value("${bundesbank.currency.exchange.service.url}")
    private String currencyServiceUrl;

    @Value("${bundesbank.currency.exchange.base.url}")
    private String currencyServiceBaseUrl;

    private Logger logger = LoggerFactory.getLogger(CurrencyDataRefresherServiceImpl.class);

    /**
     * this method orchestrates the below actions
     *  1. collect thr URLs to each of currencies exchange data
     *  2. Parse the content from each of those urls, and save the relevant content to the database
     *  for example, exchange details of AUD is in one url and USD is in another url.
     * @throws IOException
     */
    @Override
    public void getLatestCurrencyExchangeData() throws IOException {

        logger.info("At the start of fetching latest data from the external web portal");
        Document doc = Jsoup.connect(currencyServiceUrl).get();

        if(doc == null){
            throw new RefreshBatchException(INVALID_DATA,
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        logger.info("About to collect the urls to each currency exchange data");
        Map<String,String> currencyDataUrls = getCurrencyExchangeBaseDataUrls(doc);

        if(!CollectionUtils.isEmpty(currencyDataUrls)){

            logger.info("total of {} valid urls received to the individual currency details",
                    currencyDataUrls.size());

            currencyDataUrls.entrySet().stream().forEach(item -> collectAndSaveIndividualCurrencyData(item));
        }


    }

    private Map<String,String> getCurrencyExchangeBaseDataUrls(Document doc) {

        Element element = doc.select(TABLE).get(0);

        if(null != element){
            Elements rows = element.select(TABLE_ROW);
            return processCurrencyDataBaseRows(rows);
        } else {
            throw new RefreshBatchException(NO_VALID_CURRENCY_ROWS_FOUND,
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * method to iterate the base url content and collect all the individual urls corresponding
     * to each currency
     * @param rows
     * @return
     */
    private Map<String,String> processCurrencyDataBaseRows(Elements rows) {

        logger.info("About to process the each currency rows one by one");
        logger.info("Total of {} currency data rows found", rows.size()-1);
        Map<String,String> currencyDataUrls = new HashMap<>();

        for (int i = 1; i < rows.size(); i++) {//first row is the col names so skip it.

            Element row = rows.get(i);
            Elements cols = row.select(TABLE_COLUMN);

            if(null != cols && !cols.isEmpty()) {

                Element col = cols.get(0);
                Element hrefToIndividualCurrencyData = col.select("a").first();
                String currencyTitle = hrefToIndividualCurrencyData.text();

                if(null != currencyTitle && currencyTitle.contains(TABLE_HREF_TITLE_PREFIX) &&
                    currencyTitle.contains(TABLE_HREF_TITLE_SUFFIX)){

                        logger.info("Valid currency present in this row");
                        String currency = hrefToIndividualCurrencyData.text().substring(8,11);

                        logger.info("The currency is {} and adding its corresponding url to map to be processed",
                                currency);
                        String currencyDataUrl = hrefToIndividualCurrencyData.attr(TABLE_HREF);
                        currencyDataUrls.put(currency,currencyDataUrl);
                 }


            } else {
                throw new RefreshBatchException(NO_VALID_CURRENCY_ROWS_FOUND,
                        HttpStatus.INTERNAL_SERVER_ERROR.value());
            }

        }
        return currencyDataUrls;
    }


    private void collectAndSaveIndividualCurrencyData(Map.Entry individualCurrencyData)  {

        String individualCurrencyDetailsurl = new StringBuilder(currencyServiceBaseUrl).
                append(individualCurrencyData.getValue()).toString();
        String currency = (String) individualCurrencyData.getKey();
        logger.info("About to fetch the exchange details of currency {}",currency);
        Document individualCurrencyDoc = null;

        try{
            individualCurrencyDoc = Jsoup.connect(individualCurrencyDetailsurl).get();
        } catch (IOException exception){
            //Just a warning message since the exception was thrown when trying to read ONE individual currency data
            logger.warn("Exception occurred while trying to read data for the currency {}"
                    ,currency);
        }

        if(null != individualCurrencyDoc){

            logger.info("successfully received the data for the currency {}", currency);
            Element currencyElement = individualCurrencyDoc.select(TABLE).get(0);

            if(null != currencyElement){
                Elements currencyDataRows = currencyElement.select(TABLE_ROW);
                fetchAndSaveIndividualCurrencyData(currencyDataRows, currency);

            }

        }

    }

    private void fetchAndSaveIndividualCurrencyData(Elements currencyDataRows, String currency) {

        logger.info("About to save exchange rates of {} to the DB",currency);
        for(int i=1; i < currencyDataRows.size(); i++) {//first row is the col names so skip it.
            Element currencyDataRow = currencyDataRows.get(i);
            Elements dataCols = currencyDataRow.select(TABLE_COLUMN);

            logger.info("Building currencyExchangeData object");
            CurrencyExchangeDTO currencyExchangeDTO = new CurrencyExchangeDataBuilder(currency).
                    withDate(dataCols).withValue(dataCols).withPercentageChange(dataCols).build();
        }
    }


}
