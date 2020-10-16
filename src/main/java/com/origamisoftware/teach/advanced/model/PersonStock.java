/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.origamisoftware.teach.advanced.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author steveduarte
 *//**
 * Models a table the combines person with their stocks.
 */
@Entity
@Table(name = "person_quotes", catalog = "stocks")
public class PersonStock {
    private int id;
    private Person person;
    private StockQuote stock;

    /**
     * Create a PersonHobby that needs to be initialized
     */
    public PersonStock() {
        // this empty constructor is required by hibernate framework

    }

    /**
     * Create a valid PersonStock
     *
     * @param person the person to assign the hobby to
     * @param stock  the stock to associate the person with
     */
    public PersonStock(Person person, StockQuote stock) {
        setStock(stock);
        setPerson(person);
    }

    /**
     * Primary Key - Unique ID for a particular row in the person_stock table.
     *
     * @return an integer value
     */
    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    /**
     * Set the unique ID for a particular row in the person_stock table.
     * This method should not be called by client code. The value is managed in internally.
     *
     * @param id a unique value.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return get the Person associated with this stock
     */
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "ID", nullable = false)
    public Person getPerson() {
        return person;
    }

    /**
     * Specify the Person associated with the stock.
     *
     * @param person a person instance
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     *
     * @return get the Stock associated with this Person
     */
    @ManyToOne
    @JoinColumn(name = "stock_id", referencedColumnName = "ID", nullable = false)
    public StockQuote getStock() {
        return stock;
    }

    /**
     * Specify the Hobby associated with the Person.
     *
     * @param hobby a person instance
     */
    public void setStock(StockQuote stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonStock that = (PersonStock) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + person.hashCode();
        result = 31 * result + stock.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PersonStock{" +
                "id=" + id +
                ", person=" + person +
                ", stock=" + stock +
                '}';
    }
}
