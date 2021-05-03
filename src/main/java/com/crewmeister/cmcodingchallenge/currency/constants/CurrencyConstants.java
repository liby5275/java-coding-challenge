package com.crewmeister.cmcodingchallenge.currency.constants;

public class CurrencyConstants {

    private CurrencyConstants() {
        throw new IllegalStateException("Utility Constant class");
    }

    //String constants
    public static final String TABLE = "table";
    public static final String TABLE_ROW = "tr";
    public static final String TABLE_COLUMN = "td";
    public static final String TABLE_HREF = "href";
    public static final String TABLE_HREF_TITLE_PREFIX = "BBEX3.D.";
    public static final String TABLE_HREF_TITLE_SUFFIX = "EUR.BB.AC.000";

    //Error messages

    public static final String NO_VALID_CURRENCY_ROWS_FOUND = "No currency exchange rows found in the website provided";
    public static final String INVALID_DATA = "No Data found from the url provided.";
    public static final String NO_EXCHANGE_RATES_FOUND = "No exchange rates found from the DB";
    public static final String NO_RATE_FOUND_FOR_GIVEN_DATE_AND_CURRENCY =
            "Exchange rate data is not present in the db for the given date & currency combo";
    public static final String NO_CURRENCY_FOUND = "No currency found from the DB";
    public static final String INVALID_DATE_INPUT = "Un-parsable date input provided. please provide yyyy-MM-dd format";
    public static final String INVALID_CURRENCY_INPUT = "Invalid currency code provided. code must of three in length";
    public static final String INVALID_AMOUNT_INPUT = "Negative / Zero amount provided. Hence not proceeding";
    public static final String INVALID_EXCHANGE_RATE_FOUND =
            "DB has entry for the currency & Date combo . But with a zero/negative conversion rate";

}
