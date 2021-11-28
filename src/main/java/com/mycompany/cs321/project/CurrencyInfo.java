package com.mycompany.cs321.project;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Connor Stewart, ..., 
 */
public class CurrencyInfo {
    
    private String name;
    private String symbol;
    private double price;
    
    /**
     * Initializes cryptocurrency based upon the API. 
     * @param selection the cryptocurrency the user selects.
     */
    public CurrencyInfo(int selection) throws FileNotFoundException, IOException {
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
    private void getAPIInfo(int selection) throws FileNotFoundException, IOException {
        DataModel dataModel = new DataModel();
        if (selection == 1) {
            this.name = "Bitcoin";
            this.symbol = "BTC";        
            this.price = dataModel.getData(1);
        } else if (selection == 2) {
            this.name = "Ethereum";
            this.symbol = "ETH";
            this.price = dataModel.getData(3);
        }
    }
    
    public String getName() {
        return this.name; 
    }
    
    public String getSymbol() {
        return this.symbol; 
    }
    
    public double getPrice() {
        return this.price; 
    }
}
