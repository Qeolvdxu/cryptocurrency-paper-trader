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
 * 
 * Please refer to the constructor on how to create a DataHandlerThread
 * 
 * Outputs to a seperate file in the background so that the DataModel can pull the data from the same file when needed and not worry about updating
 * 
 * The output is a file so that it can read and store data correctly without hardcoding values and getter functions
 * 
 * runs in the background on a seperate thread
 * 
 * Used in this program to store crypto buy and sell values
 * 
 * This class has been created dynamically so it is usable in any given format,
 * even in different programs with different data!
 * @author Hayden Estes
 */


public class DataHandlerThread extends Thread
{
    private Thread t;
    private final String threadName;
    private boolean running = true;
    private final boolean quiet;
    private final int bedtime;
    
    private final int curNum = 2;
    String[] curs = 
    {
        "BTC-USD",
        "ETH-USD",
    };
    
    
    /**
     * Contructs the thread and gives it is name
     * wait is the number of seconds between value updating
     * status is if the thread will output debug messages
     * @param name , wait, status
     */
    DataHandlerThread(String name, int wait, boolean status) 
    {
      quiet = status;
      threadName = name;
      bedtime = wait;
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
                    content.append(inputLine);
                    dataWriter.write(inputLine);
                }
            }
            dataWriter.newLine();

        }
    }
    
    /**
     * Starts the threat, setting up the private t value
     * calls run() after
     */
    @Override
    public void start () 
    {
        if (!quiet) System.out.println("Starting " +  threadName );
                
        // Start the thread! 
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
        if (!quiet) System.out.println("Thread " +  threadName + " exiting.");
        running = false;
    }
    
    
    /**
     * Override of threads run, does the main function processing 
     * Runs urlToFile over and over forever unless the kill function is called
     * Grabs data for each crypto listed in the array above, gets buying and selling each
     * The written files writes the information on 2 lines per crypto (for buy/sell) and each crypto in order directly after
     * This function is created to be able to dynamically change with the hardcoded array above for super easy currency changing
     * 10 seconds a sleep time between each urlToFile call
     */
    @Override
    public void run()
    {
        // Switch between Buy/Sell for url editing
        String bs = "buy";
        
        // Set up empty values to be used in the loop
        HttpURLConnection con = null;
        URL url = null;
        int i = 0;
        BufferedWriter writerBuffer = null;
        File out = new File("prices_LIVE.txt");

        // Loop this try block the entire time the thread is being used
        if (!quiet) System.out.println("Running " +  threadName );
        try {
            while (running) 
            {
                // Sleep and check if file doesn't exist
                sleep(bedtime*1000);
                if(!out.exists()) {
                    out.createNewFile();
                }
                     
                // Attempt to open the file for writing
                try 
                {       
                       // False parameter on file writer: overwrite contents with new data instead of appending. 
                       writerBuffer = new BufferedWriter(new FileWriter("prices_LIVE.txt", false));
                } 
                catch (IOException ex) 
                {
                     if (!quiet) System.out.println(threadName + ": Unable to open file!");
                         Logger.getLogger(DataHandlerThread.class.getName()).log(Level.SEVERE, null, ex);         
                }
                
                // Loop for each currency to write the file
                for (i = 0; i <= (curNum*2)-1; i++)
                {
                      url = new URL("https://api.coinbase.com/v2/prices/" + curs[i/2] + "/" + bs);
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
                            }
                      }
                      writerBuffer.newLine();
                     if ("buy".equals(bs))
                            bs = "sell";
                     else
                        bs = "buy";
                }
                con.disconnect();
                writerBuffer.close();
            }
        } 
        
        // Error handeling for main loop
        catch (IOException ex) 
        {
            if (!quiet) System.out.println(threadName + ": Main Loop Fail with IOEXCEPTION");
            Logger.getLogger(DataHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (InterruptedException ex) 
        {           
            if (!quiet) System.out.println(threadName + ": Sleep Interrupted");
            Logger.getLogger(DataHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally 
        {
            try 
            {
                writerBuffer.close();
            } 
            catch (IOException ex) 
            {
                if (!quiet) System.out.println(threadName + ": Failed to close writer");
                Logger.getLogger(DataHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Stop the thread entierly
        kill();
    }
}


