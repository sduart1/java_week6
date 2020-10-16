package com.origamisoftware.teach.advanced.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A container class that contains stock data.
 */
@Entity
public class StockQuote extends StockData {
    
    private int id;
    private BigDecimal price;
    private Date date;
    private String symbol;

    
    /**
     * Primary Key - Unique ID for a particular row in the hobby table.
     *
     * @return an integer value
     */
    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }
    
    /**
     * Set the unique ID for a particular row in the hobby table.
     * This method should not be called by client code. The value is managed in internally.
     *
     * @param id a unique value.
     */
    public void setId(int id) {
        this.id = id;
    }

    
    
    /**
     * Create a new instance of a StockQuote.
     *
     * @param price  the share price for the given date
     * @param date   the date of the share price
     * @param symbol the stock symbol.
     */
    public StockQuote(BigDecimal price, Date date, String symbol) {
        super();
        this.price = price;
        this.date = date;
        this.symbol = symbol;
    }

    /**
     * @return Get the share price for the given date.
     */
    @Basic
    @Column (name = "price", nullable = false, insertable = true, updatable = true, length = 256)
    public BigDecimal getPrice() {
        return price;
    }
    
    /**
     * Specify the stock's price
     *
     * @param price a BigDecimal value
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    

    /**
     * @return The date of the share price
     */
    @Basic
    @Column (name = "date", nullable = false, insertable = true, updatable = true, length = 256)
    public Date getDate() {
        return date;
    }
    
    /**
     * Specify the stock quote's date
     * @param date a Date value
     */
    public void setDate(Date date){
        this.date = date;
    }
    

    /**
     * @return The stock symbol.
     * 
     */
    @Basic
    @Column (name="symbol", insertable = true, updatable = true, length = 256)
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol){
        this.symbol  = symbol;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockQuote stock = (StockQuote) o;

        if (id != stock.id) return false;
        if (symbol != null ? !symbol.equals(stock.symbol) : stock.symbol != null)
            return false;
        if (price != null ? !price.equals(stock.price) : stock.price != null)
            return false;
        if (date != null ? !date.equals(stock.date) : stock.date != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String dateString = simpleDateFormat.format(date);
        return "StockQuote{" +
                "price=" + price +
                ", date=" + dateString +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
