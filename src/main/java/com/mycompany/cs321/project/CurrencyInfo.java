package com.mycompany.cs321.project;


import java.io.FileNotFoundException;
import java.io.IOException;

import java.text.DecimalFormat; // Formatting currency price
/**
 * Represents a cryptocurrency trading pair which information is updated real-time from the API.
 * @author Connor Stewart 
 */
public class CurrencyInfo {
    
    private String name;
    private String symbol;
    private double price;
    
    /**
     * Initializes cryptocurrency pair based upon the API using the pair ID.
     * @param selection the cryptocurrency the user selects.
     * @throws java.io.FileNotFoundException
     */
    public CurrencyInfo(int selection) throws FileNotFoundException, IOException {
        getAPIInfo(selection);
    }
    /**
     * Initializes cryptocurrency pair based upon the API using the pair name.  
     * @param selection the cryptocurrency the user selects.
     * @throws java.io.IOException
     */
    public CurrencyInfo(String selection) throws IOException {
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
        DecimalFormat df = new DecimalFormat("0.0000000");
        switch (selection) {
            case 1:
                this.name = "Bitcoin to USD";
                this.symbol = "BTC-USD";
                this.price = Float.parseFloat(df.format(dataModel.getData(1)));
                break;
            case 2:
                this.name = "Ethereum to USD";
                this.symbol = "ETH-USD";
                this.price = Float.parseFloat(df.format(dataModel.getData(3)));
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
    private void getAPIInfo(String selection) throws FileNotFoundException, IOException {
        DataModel dataModel = new DataModel();
        DecimalFormat df = new DecimalFormat("0.0000000");
        switch (selection) {
            case "BTC-USD":
                this.name = "Bitcoin to USD";
                this.symbol = "BTC-USD";
                this.price = Float.parseFloat(df.format(dataModel.getData(1)));
                break;
            case "ETH-USD":
                this.name = "Ethereum to USD";
                this.symbol = "ETH-USD";
                this.price = Float.parseFloat(df.format(dataModel.getData(3)));
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
    /**
     * Get cryptocurrency trading pair name in the form "FullNameX to FullNameY".
     * @return Cryptocurrency name.
     */
    public String getName() {
        return this.name; 
    }
    /**
     * Get cryptocurrency trading pair name in the form "SymbolX-SymbolY".
     * @return Cryptocurrency symbol.
     */
    public String getSymbol() {
        return this.symbol; 
    }
    
    /**
     * Get cryptocurrency trading pair price.
     * @return Cryptocurrency price.
     */
    public double getPrice() {
        return this.price; 
    }
}
