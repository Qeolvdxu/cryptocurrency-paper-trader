/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cs321.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
 * @author Hayden Estes
 */


public class DataHandlerThread extends Thread
{
    private Thread t;
    private final String threadName;
    private boolean running = true;
    private final boolean quiet;
    
    private final int curNum = 3;
    String[] curs = 
    {
        "BTC",
        "ETH",
        "DOGE"
    };
    
    
    /**
     * Contructs the thread and gives it is name
     * @param name 
     */
    DataHandlerThread(String name, boolean status) 
    {
      quiet = status;
      threadName = name;
      if (!quiet) System.out.println("Creating " +  threadName );
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
    private void urlToFile(HttpURLConnection con, BufferedWriter dataWriter)  throws MalformedURLException, IOException {
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
                   // content.append(inputLine);
                    dataWriter.write(inputLine);
                }
            }
            dataWriter.newLine();

        }
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
        if (!quiet) System.out.println("Starting " +  threadName );
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
       BufferedWriter writerBuffer = null;

        try {
            dataWriter = new FileWriter("prices_LIVE.txt", true);
            writerBuffer = new BufferedWriter(dataWriter);
        } catch (IOException ex) {
            if (!quiet) System.out.println(threadName + ": Unable to open file!");
            Logger.getLogger(DataHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (!quiet) System.out.println("Running " +  threadName );
            HttpURLConnection con = null;

            URL url = null;
            int i = 0;
            while (running) 
            {
                //for (i = 0; i < curNum-1; i++)
                //{
                      url = new URL("https://api.coinbase.com/v2/prices/" + curs[i] + "-USD/buy");
                      con = (HttpURLConnection) url.openConnection();
                      con.setRequestMethod("GET");
                      int status = con.getResponseCode();
                      try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())))
                      {

                           String inputLine;
                           StringBuilder content = new StringBuilder();
                           while ((inputLine = in.readLine()) != null)
                           {
                               content.append(inputLine);
                               writerBuffer.write(inputLine);
                               System.out.println(inputLine);
                           }                   
                      }
                      writerBuffer.write("\n");
                //}
        
                
                //urlToFile(con,writerBuffer);
                //urlToFile(btcB,writerBuffer);
                //urlToFile(btcS,writerBuffer);
                // dataWriter.write("Boots with the fur");
                con.disconnect();

                sleep(10000);

            }
            
           /*  Run using seperate function
            
            try {
            writeDataFileLoop();
            } catch (IOException ex) {
            System.out.println("Encountered IO Exception on "+threadName+" run!");
            Logger.getLogger(DataHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            } */
        } catch (IOException ex) {
            if (!quiet) System.out.println(threadName + ": Main Loop Fail");
            Logger.getLogger(DataHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            if (!quiet) System.out.println(threadName + ": Sleep Interrupted");
            Logger.getLogger(DataHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dataWriter.close();
                writerBuffer.close();

            } catch (IOException ex) {
                if (!quiet) System.out.println(threadName + ": Failed to close writer");
                Logger.getLogger(DataHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        File out = new File("prices_LIVE.txt");
        if (!quiet)
            System.out.println("Thread " +  threadName + " exiting.");
        out.delete();
    }
}


