/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cs321.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Reads text from a url connection and outputs to a file over and over
 * The output is a file so that it can read and store data correctly without hardcoding values and getter functions
 * runs in the background on a seperate thread
 * Used in this program to store crypto buy and sell values
 * @author hayden
 */


public class DataHandlerThread extends Thread
{
    private Thread t;
    private final String threadName;
    private boolean running = true;
    
    /**
     * Contructs the thread and gives it is name
     * @param name 
     */
    DataHandlerThread(String name) 
    {
      threadName = name;
      System.out.println("Creating " +  threadName );
    }

    /**
     * Writes URL contents to a file,
     * needs the URL variable and the FileWriting Variable
     * Is called in the main threads run() loop
     * 
     * @param url
     * @param dataWriter
     * @throws MalformedURLException
     * @throws IOException 
     */
    private void urlToFile(URL url, FileWriter dataWriter)  throws MalformedURLException, IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        try (dataWriter) 
        {
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())))
            {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null)
                {
                    content.append(inputLine);
                    dataWriter.write(inputLine);
                }
            }
            dataWriter.write('\n');
        }
        con.disconnect();
    }
    
    /* Seperate function 
    
    public void writeDataFileLoop() throws MalformedURLException, IOException 
    {
        FileWriter dataWriter = new FileWriter("prices_LIVE.txt");
        URL btc = new URL("https://api.coinbase.com/v2/prices/BTC-USD/buy");
        while (true)
        {
            urlToFile(btc,dataWriter);
        }
        // Add more currencys here
    }*/
    
    
    /**
     * Starts the threat, setting up the private t value
     * calls run() after
     */
    @Override
    public void start () 
    {
        System.out.println("Starting " +  threadName );
        if (t == null) 
        {
            t = new Thread (this, threadName);
            t.start ();
        }
    }

    /**
     * Causes the loop in run() to stop,
     * should only be used when the thread is to be killed
     */
    public void kill()
    {
        running = false;
    }
    
    
    /**
     * Override of threads run, does the main function processing 
     * Runs urlToFile over and over forever unless the kill function is called
     * 10 seconds a sleep time between each urlToFile call
     */
    @Override
    public void run()
    {
        FileWriter dataWriter = null;
        try {
            System.out.println("Running " +  threadName );
            dataWriter = new FileWriter("prices_LIVE.txt");
            URL btc = new URL("https://api.coinbase.com/v2/prices/BTC-USD/buy");
            //while (running) 
            //{
                urlToFile(btc,dataWriter);
            //}
            sleep(10000);
            
           /*  Run using seperate function
            
            try {
            writeDataFileLoop();
            } catch (IOException ex) {
            System.out.println("Encountered IO Exception on "+threadName+" run!");
            Logger.getLogger(DataHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            } */
        } catch (IOException ex) {
            System.out.println(threadName + ": Main Loop Fail");
            Logger.getLogger(DataHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            System.out.println(threadName + ": Sleep Interrupted");
            Logger.getLogger(DataHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dataWriter.close();
            } catch (IOException ex) {
                System.out.println(threadName + ": Failed to close writer");
                Logger.getLogger(DataHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }
}
