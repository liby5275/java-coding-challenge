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
}
