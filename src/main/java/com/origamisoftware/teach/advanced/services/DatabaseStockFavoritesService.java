/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.origamisoftware.teach.advanced.services;

import com.origamisoftware.teach.advanced.model.Person;
import com.origamisoftware.teach.advanced.model.PersonStock;
import com.origamisoftware.teach.advanced.model.StockQuote;
import com.origamisoftware.teach.advanced.util.DatabaseUtils;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * Stock Favorites class that matches stock data to person data.
 *
 */
public class DatabaseStockFavoritesService implements PersonService {
    
    /**
     * Get a list of all people
     * @return a list of Person instances
     * @throws StockServiceException 
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Person> getPerson() throws StockServiceException {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        List<Person> returnValue = null;
        Transaction transaction = null;
        try {
            
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Person.class);
            
            returnValue = criteria.list();
            
            
        } catch (HibernateException e){
            if (transaction != null && transaction.isActive()) {
                transaction.rollback(); 
            }
            throw new StockServiceException ("Could not get Person data. " + e.getMessage(), e);
        } finally {
            if (transaction != null && transaction.isActive()){
                transaction.commit();
            }
        }
        
        return returnValue;
    }

    /**
     *  Add a new person or update an existing Person's data
     * 
     * @param person a person object to either update or create
     */
    @Override
    public void addOrUpdatePerson(Person person) {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(person);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }


    /**
     *
     * @param person specifies what person you are retrieving stock data for
     * @return stocks specifies stock data for the person in question
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<StockQuote> getStocks(Person person){
        Session session = DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        List<StockQuote> stocks = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(PersonStock.class);
            criteria.add(Restrictions.eq("person", person));
            
            List<PersonStock> list = criteria.list();
            for (PersonStock personStock : list){
                stocks.add(personStock.getStock());
            }
            transaction.commit();
        } catch (HibernateException e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        } finally {
            if (transaction != null && transaction.isActive()){
                transaction.commit();
            }
        }
        return stocks;
    }

    /**
     *
     * @param stock  The stock to assign
     * @param person The person to assign the stock too.
     */
    @Override
    public void addStockToPerson(StockQuote stock, Person person) {
        Session session =  DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            PersonStock personStock = new PersonStock();
            personStock.setStock(stock);
            personStock.setPerson(person);
            session.saveOrUpdate(personStock);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }
    
}
