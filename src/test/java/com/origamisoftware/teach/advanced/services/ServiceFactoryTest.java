package com.origamisoftware.teach.advanced.services;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * JUnit test for <CODE>ServiceFactory</CODE>
 */
public class ServiceFactoryTest {

    @Test
    public void testGetStockServiceInstance() {
        StockService stockService = ServiceFactory.getStockServiceInstance();
        assertNotNull(stockService);
    }
    
    @Test
    public void testGetPersonServiceInstance() throws StockServiceException {
        PersonService personService = ServiceFactory.getPersonServiceInstance();
        assertNotNull(personService);
    }
}
