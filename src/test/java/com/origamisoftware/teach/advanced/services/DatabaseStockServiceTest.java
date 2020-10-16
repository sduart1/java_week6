package com.origamisoftware.teach.advanced.services;

import com.origamisoftware.teach.advanced.model.StockQuote;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for the DatabaseStockService
 */
public class DatabaseStockServiceTest {

    
    /*
    * Create a new instance of DatabaseStockService and make sure the info is 
    * handled correctly
    */
    @Test
    public void testGetQuote() throws Exception {
        DatabaseStockService databaseStockService = new DatabaseStockService();
        String symbol = "APPL";
        StockQuote stockQuote = databaseStockService.getQuote(symbol);
        assertNotNull("Verify we can get a stock quote from the db", stockQuote);
        assertEquals("Make sure the symbols match", symbol, stockQuote.getSymbol());
    }
    
    
    /**
     * 
     * Create a new instance of DatabaseStockService and test getQuot() method
     * containing date range information
     */
    @Test
    public void testGetQouteRange() throws Exception {
        DatabaseStockService databaseStockService = new DatabaseStockService();
        
        String symbol = "GOOG";
        Calendar from = Calendar.getInstance();
        from.set(1981, 01, 25, 0,0,0);
        Calendar until = Calendar.getInstance();
        until.set(2020, 02, 15, 0, 0, 0);

        List<StockQuote> stockQuote = databaseStockService.getQuote(symbol, from, until);
        
        assertNotNull("Verify we can get a stock quote from the db", stockQuote);        
     
    }

}
