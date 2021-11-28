package com.mycompany.cs321.project;

import java.math.BigDecimal;

/**
 * Represent an order that an account creates. 
 * @author Connor Stewart
 */
public class Order {
    private String type = "";
    private CurrencyInfo crypto = new CurrencyInfo();
    private double quantity = 0.0;
    
    /**
     * 
     * @param type String of "Buy" or "Sell" type.
     * @param crypto Cryptocurrency pair that order contains.
     * @param quantity Amount of currency that is being bought/sold.
     */
    public Order(String type, CurrencyInfo crypto, double quantity) {
        this.type = type;
        this.crypto = crypto;
        this.quantity = quantity;
    }  
    
    /**
     * Formats and concatenates order components for displaying and saving.
     * @return Formatted order string.
     */
    public String printOrder() {
        return this.type + "," + this.crypto.getSymbol() + "," + String.format("%.8f", new BigDecimal(this.quantity));
    }
    
    /**
     * Get Order quantity.
     * @return Order quantity.
     */
    public double getQuantity() {
        return this.quantity;
    }
    
    /**
     * Get Order type.
     * @return Order type.
     */
    public String getType() {
        return this.type;
    }
    
    /**
     * Get Order cryptocurrency.
     * @return Order cryptocurrency.
     */
    public CurrencyInfo getCurrency() {
        return this.crypto;
    }
}
