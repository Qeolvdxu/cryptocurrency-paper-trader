package com.mycompany.cs321.project;

import java.text.DecimalFormat;

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
    public CurrencyInfo(int selection) {
        getAPIInfo(selection);
    }
    /**
     * Initializes cryptocurrency based upon the API. 
     * @param selection the cryptocurrency the user selects.
     */
    public CurrencyInfo(String selection) {
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
        DecimalFormat df = new DecimalFormat("0.0000000");
        switch (selection) {
            case 1:
                this.name = "Bitcoin to USD";
                this.symbol = "BTC-USD";
                this.price = Float.parseFloat(df.format(57278.10));
                break;
            case 2:
                this.name = "Ethereum to USD";
                this.symbol = "ETH-USD";
                this.price = Float.parseFloat(df.format(4274.55));
                break;
            case 3:
                this.name = "Bitcoin to Ethereum";
                this.symbol = "BTC-ETH";
                this.price = Float.parseFloat(df.format(13.43));
                break;
            default:
                break;
        }
    }
    
        /**
     * This is a temporary implementation of the API using hardcoded fields 
     * for testing. 
     * @param selection the cryptocurrency the user selects. 
     */
    private void getAPIInfo(String selection) {
        DecimalFormat df = new DecimalFormat("0.0000000");
        switch (selection) {
            case "BTC-USD":
                this.name = "Bitcoin to USD";
                this.symbol = "BTC-USD";
                this.price = Float.parseFloat(df.format(57278.10));
                break;
            case "ETH-USD":
                this.name = "Ethereum to USD";
                this.symbol = "ETH-USD";
                this.price = Float.parseFloat(df.format(4274.55));
                break;
            case "BTC-ETH":
                this.name = "Bitcoin to Ethereum";
                this.symbol = "BTC-ETH";
                this.price = Float.parseFloat(df.format(13.43));
                break;
            default:
                break;
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
