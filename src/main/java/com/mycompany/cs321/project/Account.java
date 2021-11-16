package com.mycompany.cs321.project;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

/**
 * Handles the creation and login of user accounts. Each account has their 
 * own file with their credentials, balance, and order information. 
 * @author Connor Stewart, ..., 
 */
public class Account {
    public String username;
    private String password;
    static Scanner input = new Scanner(System.in);
    
    /**
     * Prompts the user for console input to create an account using an
     * username and password. Then saves this information in a file in the 
     * accounts directory.
     */
    public void createAccount() {
        System.out.println("\n-- Create Account --");
        // Read username
        System.out.println("Enter username: ");
        username =  input.nextLine();
            
        // Read password
        System.out.println("Enter password: ");
        // Encrypt password
        password =  encryptCredentials(input.nextLine());
        
        // Create account file and save info
        try {
            // Create directory if it doesn't exist
            File dir = new File("accounts");
            dir.mkdirs();
            
            // Create file if it doesn't exist 
            File accountFile = new File(dir, username + ".txt");
            if (accountFile.createNewFile()) {
                System.out.println("Account created: " + accountFile.getName());
            } else {
                System.out.println("Account already exists.");
            }
            
            try ( // Save account information to file
                    FileWriter writer = new FileWriter(accountFile)) {
                writer.write(username + ":" + password);
            }
        } catch(IOException e) {
            System.out.println("Error occurred.");
        }
        System.out.println();
    }
    
    /**
     * Prompts the user for console input to login to an account using an
     * username and password. Then checks for and opens the corresponding
     * account file and validates the login information. 
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
            File accountFile = new File(dir, tempUsername + ".txt");
            Scanner fileReader = new Scanner(accountFile);
            // Parse saved password from file
            String accountInfo = fileReader.nextLine();
            String savedPassword = accountInfo.substring(accountInfo.indexOf(":")+1);
            // Does newly entered password match the saved password
            if (savedPassword.equals(tempPassword)) {
                // Logged in state
                username = tempUsername;
                password = tempPassword;
            } else {
                System.out.println("Incorrect password");
                return false;
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Account not found");
            return false;
        }
        System.out.println();
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
}
