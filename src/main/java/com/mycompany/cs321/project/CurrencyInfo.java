package com.mycompany.cs321.project;

/**
 *
 * @author Connor Stewart, ..., 
 */
public class CurrencyInfo {
    
    String name;
    String symbol;
    double price;
    
    /**
     * Initializes cryptocurrency based upon the API. 
     * @param selection the cryptocurrency the user selects.
     */
    public CurrencyInfo(int selection) {
        getAPIInfo(selection);
    }
    /**
     * Default constructor initializes fields to empty.
     */
    public CurrencyInfo() {
        this.name = "";
        this.symbol = "";
        this.price = 0.0;
    }
    /**
     * This is a temporary implementation of the API using hardcoded fields 
     * for testing. 
     * @param selection the cryptocurrency the user selects. 
     */
    private void getAPIInfo(int selection) {
        if (selection == 1) {
            this.name = "Bitcoin";
            this.symbol = "BTC";
            this.price = 59944.70;
        } else if (selection == 2) {
            this.name = "Ethereum";
            this.symbol = "ETH";
            this.price = 4386.39;
        }
    }
}
