package com.mycompany.cs321.project;

/**
 * Main driver class that initializes the program. 
 * @author Connor Stewart, Hayden Estes
 */
public class ClientInterface {

    /**
     * Main method for creating the text based menu option.
     * Also starts up the Main Data Thread
     * @param args Command line arguments, not used. 
     */
    public static void main(String[] args) {
        
        // Start updating data to price_LIVE.txt every 10 seconds for BTC, ETH, and DOGE
        DataHandlerThread mainDataThread = new DataHandlerThread("MainDataThread",10,true);
        mainDataThread.start();
        
        TradingReferee trade = new TradingReferee();
        trade.run();    
    }
}
