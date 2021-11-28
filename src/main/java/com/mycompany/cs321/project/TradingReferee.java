package com.mycompany.cs321.project;

import java.io.IOException;
import java.util.Scanner;

/**
 * Holds the state of the program in text based mode.
 * @author Connor Stewart
 */
public class TradingReferee {
    // Create account and currency for the current ones
    private final Account user; 
    private CurrencyInfo currentCurrency;
    private final Scanner userInput = new Scanner(System.in);
    //LoginMenu log = new LoginMenu();
    
    // State of program, and which menu/submenu to currently display. 
    private int state;
    private static final int LOGIN_MENU = 0;
    private static final int DASHBOARD_MENU = 1;
    private static final int SELECT_CRYPTO_MENU = 2;
    
    // Initialize local copies
    public TradingReferee() {
        this.user = new Account();
        this.currentCurrency = new CurrencyInfo();
        // Start GUI
        // Starting menu
        state = LOGIN_MENU;
    }
    /**
     * Main program loop. Holds state of program. 
     */
    public void run() throws IOException {
        while (true) {
            switch (state) {
                case LOGIN_MENU:
                    loginMenu();
                    break;
                case DASHBOARD_MENU:
                    dashboardMenu();
                    break;
                case SELECT_CRYPTO_MENU:
                    selectCryptoMenu();
                    break;
                default:
                    break;
            }
        }    
    }
    /**
     * Login menu. Initial menu when program starts.
     */
    public void loginMenu() {
        System.out.println("-- Account Menu --");
        System.out.println(
            "1) Create Account\n" +
            "2) Log in\n" +
            "3) Exit"
        );
        
        int selection = userInput.nextInt();
        
        // Stop input skipping
        userInput.nextLine();
        
        switch (selection) {
            case 1:
                user.createAccount();
                break;
            case 2:
                if (user.logIn()) {
                    state = DASHBOARD_MENU;
                    try {
                        // Load user info after successful login
                        user.loadAccountInfo();
                    } catch (IOException e) {
                        System.out.println("Error loading account info");
                    }
                }
                break;
            case 3:
                this.exit();
                break;
        }
    }
    /**
     * Main menu once user is logged in, dashboard to all features.
     */
    public void dashboardMenu() {
        System.out.println("-- Dashboard Menu --");
        System.out.println("-- User: " + user.getUsername() + " --");
        System.out.println(
            "1) Select Cryptocurrency - Current Selection: " + currentCurrency.getName() + "\n" +
            "2) View Account Info\n" +
            "3) Place Order\n" +
            "4) Log out\n"
            );
        
        int selection = userInput.nextInt();
        
        // Stop input skipping
        userInput.nextLine();
        
        switch (selection) {
            case 1:
                state = SELECT_CRYPTO_MENU;
                break;
            case 2:
                user.printAccountInfo();
                break;
            case 3:
                System.out.println("-- Place Order Menu --");
                break;
            case 4:
                System.out.println();
                state = LOGIN_MENU;
                break;
        }  
    }
    
    /**
     * Select cryptocurrency sub-menu. Initializes currentCurrency to the 
     * user selected cryptocurrency. 
     * TODO: Add more supported currencies. 
     */
    public void selectCryptoMenu() throws IOException {
        System.out.println("\n-- Select Cryptocurrency Menu --");
        System.out.println("Selected: " + currentCurrency.getName());
        
        System.out.println(
            "1) Bitcoin, BTC\n" +
            "2) Ethereum, ETH\n" +
            "3) Back"
        );
        
        int selection = userInput.nextInt();
        
        // Stop input skipping
        userInput.nextLine();
        
        switch (selection) {
            case 1:
                currentCurrency = new CurrencyInfo(selection);
                break;
            case 2:
                currentCurrency = new CurrencyInfo(selection);
                break;
            case 3:
                System.out.println();
                state = DASHBOARD_MENU;
        } 
    }
    
    /**
     * Exits the program. 
     * TODO: Make sure everything is saved.
     */
    private void exit() {
        System.out.println("Exiting");
        // Exit program successfuly
        System.exit(0);
    }
}
