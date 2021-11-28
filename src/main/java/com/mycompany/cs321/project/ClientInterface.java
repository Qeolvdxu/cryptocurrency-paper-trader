package com.mycompany.cs321.project;

/**
 * Main driver class that initializes the program. 
 * @author Connor Stewart, ..., 
 */
public class ClientInterface {

    /**
     * Main method for creating the text based menu option.
     * @param args Command line arguments, not used. 
     */
    public static void main(String[] args) {
        TradingReferee trade = new TradingReferee();
        trade.run();    
    }
}
