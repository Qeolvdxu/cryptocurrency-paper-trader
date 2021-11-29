package com.mycompany.cs321.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class for the business logic of the trading. 
 * @author Hayden Estes 
 */
public class DataModel {
    private float floatBuffer;
    BufferedReader reader = null;

    
    /**
     * Constructor, sets up reader for the file prices_LIVE.txt
     * @throws FileNotFoundException 
     */
    public DataModel() throws FileNotFoundException {
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("prices_LIVE.txt"))));
    }
    
    /**
     * Returns a specific lines contents from a file
     * @param lineNumber
     * @return
     * @throws IOException 
     */
    private String getLine(int lineNumber) throws IOException
    {
        BufferedReader r = new BufferedReader(new FileReader("prices_LIVE.txt"));
        for (int i = 0; i < lineNumber - 1; i++)
        {
            r.readLine();
        }
       // r.close();
        return r.readLine();
    }
    
    /**
     * Returns the buying price of Bitcoin
     * Reads from the file prices_LIVE.txt
     * the file is updated every so often by the DataHandlerThread
     * 
     * For example...
     * 1 : BTC Buy
     * 2 : BTC Sell
     * 3 : ETH Buy
     * 4 : ETH Sell
     * 5 : DOGE Buy
     * 6 : DOGE Sell
     * 
     * @param key
     * @return
     * @throws IOException 
     */
    public float getData(int key) throws IOException
    {
        String str = getLine(key);
        str = str.replaceAll("[^\\d.]", "");
        return Float.valueOf(str);
    }
}
