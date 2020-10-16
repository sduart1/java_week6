/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.origamisoftware.teach.advanced.services;

import com.origamisoftware.teach.advanced.model.Person;
import com.origamisoftware.teach.advanced.model.StockQuote;
import java.util.List;

/**
 *
 * @author steveduarte
 */
public interface PersonService {
      /**
     * Get a list of all people
     *
     * @return a list of Person instances
     * @throws StockServiceException if a service can not read or write the requested data
     *                                    or otherwise perform the requested operation.
     */
    List<Person> getPerson() throws StockServiceException;

    /**
     * Add a new person or update an existing Person's data
     *
     * @param person a person object to either update or create
     * @throws StockServiceException if a service can not read or write the requested data
     *                                    or otherwise perform the requested operation.
     */
    void addOrUpdatePerson(Person person) throws StockServiceException;

    /**
     * Get a list of all a person's hobbies.
     *
     * @return a list of hobby instances
     * @throws StockServiceException if a service can not read or write the requested data
     *                                    or otherwise perform the requested operation.
     */
    List<StockQuote> getStocks(Person person) throws StockServiceException;

    /**
     * Assign a hobby to a person.
     *
     * @param stock  The stock to assign
     * @param person The person to assign the hobby too.
     * @throws StockServiceException if a service can not read or write the requested data
     *                                    or otherwise perform the requested operation.
     */
    public void addStockToPerson(StockQuote stock, Person person) throws StockServiceException;

}
