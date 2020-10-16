package com.origamisoftware.teach.advanced.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * JUnit test for StockQuote class
 */
public class StockQuoteTest extends StockData {

    private BigDecimal price;
    private Date date;
    String symbol;
    private StockQuote stockQuote;

    @Before
    public void setUp() {
        price = new BigDecimal(100);
        date = Calendar.getInstance().getTime();
        symbol = "APPL";
        stockQuote = new StockQuote(price, date, symbol);
    }
    
    @Test
    public void creatStockQuote(){
        assertNotNull("Testing creation", stockQuote);
    }

    @Test
    public void testSettersAndGetters() {
        int id = 10;
        stockQuote.setId(id);
        assertEquals("Share price is correct", price, stockQuote.getPrice());
        assertEquals("Share date is correct", date, stockQuote.getDate());
        assertEquals("Symbol  is correct", symbol, stockQuote.getSymbol());
    }
}
