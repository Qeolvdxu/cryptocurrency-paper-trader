package com.mycompany.cs321.project;

import java.util.Scanner; // Console input for text based menus.
import java.io.File; // Saving and loading account info to a file.
import java.io.FileWriter; // Writing to the account file.
import java.io.FileNotFoundException; // If an account file isnt found.
import java.io.IOException; // If an account file isnt found.
import java.math.BigDecimal; // Format balance and order quantities.
import java.util.Base64; // Password encoding
import java.util.ArrayList; // Order array
import java.text.DecimalFormat; // Format balance and order quantities.

/**
 * Represents a user for the program. Each account has their 
 * own file with their credentials, balance, and order information. 
 * @author Connor Stewart
 */
public class Account {
    static Scanner input = new Scanner(System.in);
    private String username;
    private String password;
    private double balanceUSD;
    private double balanceBTC;
    private double balanceETH;
    private ArrayList<Order> orderArray = new ArrayList<Order>();
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
     * Encodes the password 
     * @param password to be encoded
     * @return encoded password
     */
    private String encryptCredentials(String password) {
        // Encode entered password in base 64 encoding scheme.
        String encryptedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        return encryptedPassword;
    }
    
    /**
     * Save account information to the account file. 
     * @throws IOException if file cant be written to. 
     */
    public void saveAccountInfo() throws IOException{
        // Create writer based upon account file
        FileWriter writer = new FileWriter(accountFile);
        // Save account credentials
        writer.write(username + ":" + password + "\n");
        // Save account balances
        writer.write(String.format("%.8f", new BigDecimal(this.balanceUSD)) + "\n");
        writer.write(String.format("%.8f", new BigDecimal(this.balanceBTC)) + "\n");
        writer.write(String.format("%.8f", new BigDecimal(this.balanceETH)) + "\n");
        // Save orders
        for (Order o : orderArray) {
            writer.write(o.printOrder() + "\n");
        }
        // Close to finalize the save
        writer.close();
    }
    
    /**
     * Load account information from the account file.
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
            // Create new order based on each line
            String line = fileReader.nextLine();
            String[] lineArray = line.split(",");
            CurrencyInfo crypto = new CurrencyInfo(lineArray[1]);
            Order o = new Order(lineArray[0], crypto, Float.parseFloat(lineArray[2]));
            this.orderArray.add(o);
        }
    }
    /**
     * Display account balances to console, for use in text based program. 
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
     * Checks if an account has enough balance to cover the order submitted
     * @param o Order to check 
     * @return True if there is enough balance for the order, false otherwise
     */
    public boolean checkBalance(Order o) {
        // Buying balance change based upon quantity and price of new currency
        if (o.getType().equals("Buy")) {
            // Find which balances to change based on which traiding pair is in order
            switch (o.getCurrency().getSymbol()) {
                case "BTC-USD":
                    if (this.balanceUSD - o.getQuantity() * o.getCurrency().getPrice() < 0) {
                        return false;
                    }
                    break;
                case "ETH-USD":
                    if (this.balanceUSD - o.getQuantity() * o.getCurrency().getPrice() < 0) {
                        return false;
                    }
                    break;
                case "BTC-ETH":
                    if (this.balanceETH - o.getQuantity() * o.getCurrency().getPrice() < 0) {
                        return false;
                    }
                    break;
            } 
        // Selling balance based upon only quantity
        } else {
            // Find which balances to change based on which traiding pair is in order
            switch (o.getCurrency().getSymbol()) {
                case "BTC-USD":
                    if (this.balanceBTC - o.getQuantity() < 0) {
                        return false;
                    }
                    break;
                case "ETH-USD":
                    if (this.balanceETH - o.getQuantity() < 0) {
                        return false;
                    }
                    break;
                case "BTC-ETH":
                    if (this.balanceBTC - o.getQuantity() < 0) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }
    
    /**
     * Adds an order to the account in order to save it. 
     * @param o Order to add to an account.
     */
    public void addOrder(Order o) {
        this.orderArray.add(o);
    }
    
    /**
     * Executes an order by increasing/decreasing the accounts balance(s).
     * @param o Order that is executed.
     */
    public void execOrder(Order o) {
        // Buying increases first currency in pair, decreases second.
        if (o.getType().equals("Buy")) {
            switch (o.getCurrency().getSymbol()) {
                // Balance change based upon quantity and price of new currency.
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
        // Buying decreases first currency in pair, increases second.
        } else {
            switch (o.getCurrency().getSymbol()) {
                // Balance change based upon quantity and price of new currency.
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
     * Gets account username.
     * @return username.
     */
    public String getUsername() {
        return this.username;
    }
    
    /**
     * Sets account username.
     * @param username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets account password.
     * @param password to set that has first been encoded.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Gets account USD Balance.
     * @return USD Balance.
     */
    public double getBalanceUSD() {
        return this.balanceUSD;
    }
    
    /**
     * Gets account BTC Balance.
     * @return BTC Balance.
     */
    public double getBalanceBTC() {
        return this.balanceBTC;
    }
    
    /**
     * Gets account ETH Balance.
     * @return ETH Balance.
     */
    public double getBalanceETH() {
        return this.balanceETH;
    }
    
    /**
     * Gets array containing the accounts orders. Used for storage while program
     * is running, orders also get stored permanently to a file. 
     * @return Order array.
     */
    public ArrayList<Order> getOrderArray() {
        return this.orderArray;  
    }
}
