package com.mycompany.cs321.project;

import java.math.BigDecimal;

/**
 * Represent an order. 
 * @author Connor Stewart, ..., 
 */
public class Order {
    private String type = "";
    private CurrencyInfo crypto = new CurrencyInfo();
    private double quantity = 0.0;
    //private String status = "open";
    
    public Order(String type, CurrencyInfo crypto, double quantity) {
        this.type = type;
        this.crypto = crypto;
        this.quantity = quantity;
    }  
    
    public String printOrder() {
        return this.type + "," + this.crypto.getSymbol() + "," + String.format("%.8f", new BigDecimal(this.quantity));
    }
   
    public double getQuantity() {
        return this.quantity;
    }
    
    public String getType() {
        return this.type;
    }
    
    public CurrencyInfo getCurrency() {
        return this.crypto;
    }
}
