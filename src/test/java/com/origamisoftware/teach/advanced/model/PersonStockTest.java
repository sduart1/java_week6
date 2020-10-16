/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.origamisoftware.teach.advanced.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for PersonStock class
 */
public class PersonStockTest {
     /**
     * Testing helper method for generating PersonStock test data
     *
     * @return a PersonStock object that uses Person and Stock Quote
     * return from their respective create method.
     */
    
    private BigDecimal price;
    private Date date;
    public String symbol;
    private static StockQuote stockQuote;

    @Before
    public void setUp() {
        price = new BigDecimal(100);
        date = Calendar.getInstance().getTime();
        symbol = "APPL";
        stockQuote = new StockQuote(price, date, symbol);
    }
    
    
    public static PersonStock createPersonStock(){
        Person person = PersonTest.createPerson();
        PersonStock stock = new PersonStock(person, stockQuote);
        return stock;
    }
    
    @Test
    public void testPersonStockGettersAndSetters(){
        Person person = PersonTest.createPerson();
        StockQuote stock = stockQuote;
        PersonStock personStock = new PersonStock();
        int id = 10;
        personStock.setId(id);
        personStock.setPerson(person);
        personStock.setStock(stock);
        assertEquals("person matches", person, personStock.getPerson());
        assertEquals("stock matches", stock, personStock.getStock());
        assertEquals("ID matches", id, personStock.getId());
    }
    
     @Test
    public void testEqualsNegativeDifferentPerson() {
        PersonStock personHobby = createPersonStock();
        personHobby.setId(10);
        StockQuote stock = stockQuote;
        Person person = new Person();
        Timestamp birthDate = new Timestamp(PersonTest.birthDayCalendar.getTimeInMillis() + 10000);
        person.setBirthDate(birthDate);
        person.setFirstName(PersonTest.firstName);
        person.setLastName(PersonTest.lastName);
        PersonStock personStock2 = new PersonStock(person, stock);
        assertFalse("Different person", personHobby.equals(personStock2));
    }
    
    
    @Test 
    public void testEquals(){
        PersonStock stock = createPersonStock();
        assertTrue("Same objects are equal", stock.equals(createPersonStock()));
    }
    
    @Test
    public void testToString(){
        PersonStock personStock = createPersonStock();
        assertTrue("toString has person", personStock.toString().contains(PersonTest.firstName));
        assertTrue("toString has stock", personStock.toString().contains(stockQuote.getSymbol()));

    }


}
