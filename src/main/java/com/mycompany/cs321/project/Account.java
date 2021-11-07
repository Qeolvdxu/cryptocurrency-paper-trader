package com.mycompany.cs321.project;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Connor Stewart, ..., 
 */
public class Account {
    String username;
    String password;
    static Scanner input = new Scanner(System.in);
    
    /**
     * Prompts the user for console input to create an account using an
     * username and password. Then saves this information in a file in the 
     * accounts directory.
     */
    public void createAccount() {
        System.out.println("[Create Account]");
        // Read username
        System.out.println("Enter username: ");
        username =  input.nextLine();
            
        // Read password
        System.out.println("Enter password: ");
        password =  input.nextLine();
        
        // Create account file and save info
        try {
            // Create directory if it doesn't exist
            File dir = new File("accounts");
            dir.mkdirs();
            
            // Create file if it doesn't exist 
            File accountFile = new File(dir, username + ".txt");
            if (accountFile.createNewFile()) {
                System.out.println("File created: " + accountFile.getName());
            } else {
                System.out.println("File already exists.");
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
    public void logIn() {
        System.out.println("[Log in]");
        // Read username
        System.out.println("Enter username: ");
        String tempUsername =  input.nextLine();
        
        
        // Read password
        System.out.println("Enter password: ");
        String tempPassword =  input.nextLine();
        
        // Validate username and password
        try {
            File dir = new File("accounts");
            dir.mkdirs();
            File accountFile = new File(dir, tempUsername + ".txt");
            Scanner fileReader = new Scanner(accountFile);
            String accountCredentials = fileReader.nextLine();
            
            if (validateCredentials(accountCredentials)) {
                System.out.println(username + " logged in successfully.");
            } else {
                System.out.println("Incorrect password");
            }
            
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Account not found");
        }
        
    }
    /**
     * Validates the password information
     * @param credentials in "username:password" format. 
     */
    private boolean validateCredentials(String credentials) {
        return credentials.equals(username + ":" + password);
    }
}
