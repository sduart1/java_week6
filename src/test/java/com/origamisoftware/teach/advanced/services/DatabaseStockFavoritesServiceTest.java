/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.origamisoftware.teach.advanced.services;

import com.origamisoftware.teach.advanced.model.Person;
import com.origamisoftware.teach.advanced.model.PersonTest;
import com.origamisoftware.teach.advanced.model.StockQuote;
import com.origamisoftware.teach.advanced.services.DatabaseStockFavoritesService;
import com.origamisoftware.teach.advanced.services.PersonService;
import com.origamisoftware.teach.advanced.services.ServiceFactory;
import com.origamisoftware.teach.advanced.services.StockService;
import com.origamisoftware.teach.advanced.services.StockServiceException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import com.origamisoftware.teach.advanced.util.DatabaseUtils;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author steveduarte
 */
public class DatabaseStockFavoritesServiceTest {
    
    private PersonService personService;
    private StockService stockService;
    private String symbol;
    private DatabaseStockFavoritesService favorites;
    private Calendar from;
    private Calendar until;

    private void initDb() throws Exception {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
    }

    // do not assume db is correct
    @Before
    public void setUp() throws Exception {
        // we could also copy db state here for later restore before initializing
        initDb();
        personService = ServiceFactory.getPersonServiceInstance();
        stockService = ServiceFactory.getStockServiceInstance();
        symbol = "APPL";
        from = Calendar.getInstance();
        from.set(1981, 01, 25, 0,0,0);
        until = Calendar.getInstance();
        until.set(2020, 02, 15, 0, 0, 0);
    }

     /**
      *clean up after ourselves. (this could also restore db from initial state
      * @throws Exception
      */
    @After
    public void tearDown() throws Exception {
        initDb();
    }
    
    @Test
    public void testGetInstance() {
        assertNotNull("Make sure personService is available", personService);
    }
    
    @Test
    public void testGetPerson() throws StockServiceException {
        List<Person> personList = personService.getPerson();
        assertFalse("Make sure we get some Person objects from service", personList.isEmpty());
    }

    @Test
    public void testGetStocks() throws StockServiceException {
        Person person = PersonTest.createPerson();
        List<StockQuote> stocks = personService.getStocks(person);
        assertNotNull("Make sure we get stock object", stocks);
    }
    
    @Test
    public void testAddOrUpdatePerson()throws StockServiceException {
        Person newPerson = PersonTest.createPerson();
        personService.addOrUpdatePerson(newPerson);
        List<Person> personList = personService.getPerson();
        boolean found = false;
        for (Person person : personList) {
            Timestamp returnedBirthDate = person.getBirthDate();
            Calendar returnCalendar = Calendar.getInstance();
            returnCalendar.setTimeInMillis(returnedBirthDate.getTime());
            if (returnCalendar.get(Calendar.MONTH) == PersonTest.birthDayCalendar.get(Calendar.MONTH)
                    &&
                    returnCalendar.get(Calendar.YEAR) == PersonTest.birthDayCalendar.get(Calendar.YEAR)
                    &&
                    returnCalendar.get(Calendar.DAY_OF_MONTH) == PersonTest.birthDayCalendar.get(Calendar.DAY_OF_MONTH)
                    &&
                    person.getLastName().equals(PersonTest.lastName)
                    &&
                    person.getFirstName().equals(PersonTest.firstName)) {
                found = true;
                break;
            }
        }
        assertTrue("Found the person we added", found);
    }
    

    @Test
    public void testAddStockToPerson() throws StockServiceException {
        Person person = PersonTest.createPerson();
        List<StockQuote> stocks = stockService.getQuote(symbol, from, until);
        // make the person have all the hobbies
        for (StockQuote stock : stocks) {
            favorites.addStockToPerson(stock, person);
        }
        List<StockQuote> stockList = stockService.getQuote(symbol, from, until);
        for (StockQuote stock : stocks) {
            boolean removed = stockList.remove(stock);
            assertTrue("Verify that the stock was found on the list", removed);
        }
        // if  hobbyList is empty then we know the lists matched.
        assertTrue("Verify the list of returned stocks match what was expected ", stockList.isEmpty());

    }
    

   
}
