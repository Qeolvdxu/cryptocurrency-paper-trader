package com.mycompany.cs321.project;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 * Handles the creation and login of user accounts. Each account has their 
 * own file with their credentials, balance, and order information. 
 * @author Connor Stewart, ..., 
 */
public class Account {
    static Scanner input = new Scanner(System.in);
    private String username;
    private String password;
    public double balanceUSD;
    public double balanceBTC;
    public double balanceETH;
    public ArrayList<Order> orderArray = new ArrayList<Order>();
    private File accountFile;
    
    public Account() {
        // Default values for an account
        this.username = "";
        this.password = "";
        this.balanceUSD = 100000.00000000;
        this.balanceBTC = 0.00000000;
        this.balanceETH = 0.00000000;
    }
    
    /**
     * Prompts the user for console input to create an account using an
     * username and password. Then saves this information in a file in the 
     * accounts directory.
     */
    public void createAccount() {
        System.out.println("\n-- Create Account --");
        // Read username
        System.out.println("Enter username: ");
        setUsername(input.nextLine());
            
        // Read password
        System.out.println("Enter password: ");
        // Encrypt password
        setPassword(encryptCredentials(input.nextLine()));
        
        // Create account file and save info
        try {
            // Create directory if it doesn't exist
            File dir = new File("accounts");
            dir.mkdirs();
            
            // Create file if it doesn't exist 
            this.accountFile = new File(dir, getUsername() + ".txt");
            if (accountFile.createNewFile()) {
                System.out.println("Account created: " + accountFile.getName() + "\n");
            } else {
                System.out.println("Account already exists.\n");
            }
            
            // Save account information to file      
            saveAccountInfo();
        } catch(IOException e) {
            System.out.println("Error occurred.\n");
        }
        System.out.println();
    }
    
    /**
     * Uses the LoginMenu GUI Form to create an account using an
     * username and password.Then saves this information in a file in the 
     * accounts directory.
     * @param username
     * @param password
     */
    public void createAccount(String username, String password) {
        // Test for filled fields
        if (username.equals("") || password.equals("")) {
            LoginMenu.statusLabel.setText("Enter username and password");
            return;
        }
        // Save username
        setUsername(username);
        
        // Encrypt password
        setPassword(encryptCredentials(password));
        
        // Create account file and save info
        try {
            // Create directory if it doesn't exist
            File dir = new File("accounts");
            dir.mkdirs();
            
            // Create file if it doesn't exist 
            this.accountFile = new File(dir, getUsername() + ".txt");
            if (accountFile.createNewFile()) {
                LoginMenu.statusLabel.setText("Account created: " + accountFile.getName() + "\n");
                LoginMenu.usernameField.setText("");
                LoginMenu.passwordField.setText("");
            } else {
                LoginMenu.statusLabel.setText("Account already exists.\n");
            }
            
            // Save account information to file      
            saveAccountInfo();
        } catch(IOException e) {
            LoginMenu.statusLabel.setText("Error occurred, try again.\n");
        }
        System.out.println();
    }
    
    /**
     * Prompts the user for console input to login to an account using an
     * username and password.Then checks for and opens the corresponding
     * account file and validates the login information. 
     * @return true if user successfully logs in, false otherwise
     */
    public boolean logIn() {
        System.out.println("\n-- Log in --");
        // Read username
        System.out.println("Enter username: ");
        String tempUsername =  input.nextLine();
        
        // Read password
        System.out.println("Enter password: "); 
       // Encrypt entered password
        String tempPassword =  encryptCredentials(input.nextLine());
        
        // Validate username and password
        try {
            // Find the file in the accounts directory
            File dir = new File("accounts");
            dir.mkdirs();
            this.accountFile = new File(dir, tempUsername + ".txt");
            Scanner fileReader = new Scanner(accountFile);
            // Parse saved password from file
            String accountInfo = fileReader.nextLine();
            String savedPassword = accountInfo.substring(accountInfo.indexOf(":")+1);
            // Does newly entered password match the saved password
            if (savedPassword.equals(tempPassword)) {
                // Logged in state
                setUsername(tempUsername);
                setPassword(tempPassword);
            } else {
                System.out.println("Incorrect password\n");
                return false;
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Account not found\n");
            return false;
        }
        System.out.println();
        return true;
    }
    
    /**
     * Uses the LoginMenu GUI Form to create an account using an
     * username and password. Then checks for and opens the corresponding
     * account file and validates the login information. 
     * @param username
     * @param password
     * @return true if user successfully logs in, false otherwise
     */
    public boolean logIn(String username, String password) {
        String tempUsername =  username;
        
        // Encrypt entered password
        String tempPassword =  encryptCredentials(password);
        
        // Validate username and password
        try {
            // Find the file in the accounts directory
            File dir = new File("accounts");
            dir.mkdirs();
            this.accountFile = new File(dir, tempUsername + ".txt");
            Scanner fileReader = new Scanner(accountFile);
            // Parse saved password from file
            String accountInfo = fileReader.nextLine();
            String savedPassword = accountInfo.substring(accountInfo.indexOf(":")+1);
            // Does newly entered password match the saved password
            if (savedPassword.equals(tempPassword)) {
                // Logged in state
                setUsername(tempUsername);
                setPassword(tempPassword);
            } else {
                // Clear password field if entered incorrectly
                LoginMenu.statusLabel.setText("Incorrect password\n");
                LoginMenu.passwordField.setText("");
                return false;
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            LoginMenu.statusLabel.setText("Account not found\n");
            LoginMenu.passwordField.setText("");
            return false;
        }
        LoginMenu.statusLabel.setText("Logged in: " + getUsername());
        return true;
    }
    
    /**
     * Encrypts the password 
     * @param password to be encrypted
     * @return encrypted password
     */
    private String encryptCredentials(String password) {
        String encryptedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        return encryptedPassword;
    }
    
    /**
     * Save account information to file.
     * @throws IOException if file cant be written to. 
     */
    public void saveAccountInfo() throws IOException{
        FileWriter writer = new FileWriter(accountFile);
        writer.write(username + ":" + password + "\n");
        writer.write(String.format("%.8f", new BigDecimal(this.balanceUSD)) + "\n");
        writer.write(String.format("%.8f", new BigDecimal(this.balanceBTC)) + "\n");
        writer.write(String.format("%.8f", new BigDecimal(this.balanceETH)) + "\n");
        for (Order o : orderArray) {
            writer.write(o.printOrder() + "\n");
        }
        writer.close();
    }
    /**
     * Load account information from file.
     * @throws IOException if file cant be read.
     */
    public void loadAccountInfo() throws IOException{
        // Load user credentials
        Scanner fileReader = new Scanner(accountFile);
        String accountInfo = fileReader.nextLine();
        // Load balances
        this.balanceUSD = Double.parseDouble(fileReader.nextLine());
        this.balanceBTC = Double.parseDouble(fileReader.nextLine());
        this.balanceETH = Double.parseDouble(fileReader.nextLine());
        // Load orders
        this.orderArray.clear();
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] lineArray = line.split(",");
            CurrencyInfo crypto = new CurrencyInfo(lineArray[1]);
            Order o = new Order(lineArray[0], crypto, Float.parseFloat(lineArray[2]));
            this.orderArray.add(o);
        }
    }
    /**
     * Display account info to console. 
     */
    public void printAccountInfo() {
        DecimalFormat df = new DecimalFormat("0.00000000");
        
        System.out.println("\n-- Balances -- ");
        System.out.println("USD:" + df.format(this.balanceUSD));
        System.out.println("BTC:" + df.format(this.balanceBTC));
        System.out.println("ETH:" + df.format(this.balanceETH));
        System.out.println();
    }
    /**
     * Test method for seeing if increasing a balance reflects in the file. 
     * @param currentCurrency
     */
    public void testBuyBitcoin(CurrencyInfo currentCurrency) {
        this.balanceUSD -= 100;
        this.balanceBTC += (100 / currentCurrency.getPrice());
    }
    
    public void addOrder(Order o) {
        this.orderArray.add(o);
    }
    
    public void execOrder(Order o) {
        if (o.getType().equals("Buy")) {
            switch (o.getCurrency().getSymbol()) {
                case "BTC-USD":
                    this.balanceBTC += o.getQuantity();
                    this.balanceUSD -= o.getQuantity() * o.getCurrency().getPrice();
                    break;
                case "ETH-USD":
                    this.balanceETH += o.getQuantity();
                    this.balanceUSD -= o.getQuantity() * o.getCurrency().getPrice();
                    break;
                case "BTC-ETH":
                    this.balanceBTC += o.getQuantity();
                    this.balanceETH -= o.getQuantity() * o.getCurrency().getPrice();
                    break;
            } 
        } else {
            switch (o.getCurrency().getSymbol()) {
                case "BTC-USD":
                    this.balanceBTC -= o.getQuantity();
                    this.balanceUSD += o.getQuantity() * o.getCurrency().getPrice();
                    break;
                case "ETH-USD":
                    this.balanceETH -= o.getQuantity();
                    this.balanceUSD += o.getQuantity() * o.getCurrency().getPrice();
                    break;
                case "BTC-ETH":
                    this.balanceBTC -= o.getQuantity();
                    this.balanceETH += o.getQuantity() * o.getCurrency().getPrice();
                    break;
        
            }
        }
    }
    
    /**
     * gets account username
     * @return username
     */
    public String getUsername() {
        return this.username;
    }
    
    /**
     * sets account username
     * @param username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * sets account password
     * @param password that has been encoded
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
