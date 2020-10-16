package com.origamisoftware.teach.advanced.services;

import com.origamisoftware.teach.advanced.model.Person;
import com.origamisoftware.teach.advanced.model.StockQuote;
import com.origamisoftware.teach.advanced.util.DatabaseUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * A factory that returns a <CODE>StockService</CODE> 
 * and a <CODE>PersonService<\CODE> instance.
 */
public class ServiceFactory {

    /**
     * Prevent instantiations
     */
    private ServiceFactory() {}

    /**
     *
     * @return get a <CODE>StockService</CODE> instance
     */
    public static StockService getStockServiceInstance() {
        return new DatabaseStockService() {
            @Override
            public StockQuote getQuote(String symbol) throws StockServiceException {
                return new StockQuote(new BigDecimal(100), Calendar.getInstance().getTime(), symbol);
            }

            @Override
            public List<StockQuote> getQuote(String symbol, Calendar from, Calendar until) throws StockServiceException {
                List<StockQuote> stockQuotes = new ArrayList<>();
                Date aDay = from.getTime();
                while (until.after(aDay))  {
                    stockQuotes.add(new StockQuote(new BigDecimal(100),aDay,symbol));
                    from.add(Calendar.DAY_OF_YEAR, 1);
                    aDay = from.getTime();
                }
                return stockQuotes;            }
        };
    }
    
    /**
     * 
     * @return a <CODE>PersonService</CODE> instance
     */
    public static PersonService getPersonServiceInstance() throws StockServiceException {
        List<Person> person = new DatabaseStockFavoritesService().getPerson();
        
        return new DatabaseStockFavoritesService(){
        
        @Override
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
        
        return returnValue; }
    
        };
    }
}
