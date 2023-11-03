package com.techelevator;

import com.techelevator.view.Menu;

import java.util.Scanner;

public class Purchase {

    //All of our options to print to screen
    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";

    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";

    private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";

    private static final String PURCHASE_MENU_DISPLAY_FOR_FEED_MONEY = "Please feed the machine money, whole dollar amounts only (1|5|10|20)";

    private static final String PURCHASE_MENU_ASK_FOR_SELECTION = "Please Select the item slot, of the item you would like to purchase";

    private static final String PURCHASE_CHIP = "Crunch Crunch, Yum!";

    private static final String PURCHASE_CANDY = "Munch Munch, Yum!";

    private static final String PURCHASE_DRINK = "Glug Glug, Yum!";

    private static final String PURCHASE_GUM = "Chew Chew, Yum!";

    private static final String PURCHASE_SOLD_OUT = "SOLD OUT :(";

    private static final String PURCHASE_NOT_ENOUGH_FUNDS = "Please Feed Me Money First!";

    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

    private Menu purchaseMenu;

    private VendingMachine options;

    private double moneyProvided = 0.00;

    //CONSTRUCTOR
    public Purchase(Menu purchaseMenu,VendingMachine options){
        this.options = options;
        this.purchaseMenu = purchaseMenu;
    }



    //Runs the Purchase Menu to display, now we can choose an Option.
    public void run() {
        while (true) {
            //Display current amount of money in the Purchase Menu
            System.out.println("Current Money Provided: $"+ moneyProvided);

            String choice = (String) purchaseMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);



            if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {

                //select amount to feed into the vending machine
                Scanner scanner = new Scanner(System.in);

                System.out.println(PURCHASE_MENU_DISPLAY_FOR_FEED_MONEY); //Displays message for inputting money
                   String amountPutIn = scanner.nextLine(); //holds the amount typed in STRING FORM
                    double moneyPutIn = Double.parseDouble(amountPutIn); //Converts amount fed into double form.

                //***********************STILL ALLOWS .75 TO GO THROUGH************************

                if(moneyPutIn > 0 && moneyPutIn <= 20) {
                    moneyProvided += moneyPutIn; //Add the amount into the provided menu.
                }
                else{
                    System.out.println("Please enter a valid amount");
                }


            }
            if (choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {

                //Get the product, print out string with product quote, and update quantity left.
                options.display(); //Display current products and availability of current products
                System.out.println(PURCHASE_MENU_ASK_FOR_SELECTION); //Print out a message asking for item choice
                Scanner scanner = new Scanner(System.in); //Allow for input of item choice
                String itemChoice = scanner.nextLine(); //This holds the item input


                //Now we have to do if statements asking about the selection choice, then printing out the associated message
                //With the price being subtracted from the "Current Money Provided" Along with updating the item Quantity.
               if(moneyProvided > 0) {
                   //**************************************************************
                   //If the product code doesn't exist, the vending machine informs the customer and returns them to the Purchase menu.
                   //**************************************************************

                   if (options.getItemList().containsKey(itemChoice)) {
                       VendingMachineItems item = options.getItemList().get(itemChoice);

                       if (item.getQuantity() == 0) {
                           System.out.println(PURCHASE_SOLD_OUT + "  Please Select another option");

                       }
                       if (item.getQuantity() > 0 && moneyProvided >= item.getPrice()) {
                           moneyProvided -= item.getPrice();
                           item.setQuantity(item.getQuantity() - 1);

                           if (item.getType().equals("Chip") && moneyProvided >= item.getPrice()) {
                               options.display();
                               System.out.println(PURCHASE_CHIP);

                           } else if (item.getType().equals("Candy") && moneyProvided >= item.getPrice()) {
                               options.display();
                               System.out.println(PURCHASE_CANDY);

                           } else if (item.getType().equals("Drink") && moneyProvided >= item.getPrice()) {
                               options.display();
                               System.out.println(PURCHASE_DRINK);

                           } else if (item.getType().equals("Gum") && moneyProvided >= item.getPrice()) {
                               options.display();
                               System.out.println(PURCHASE_GUM);

                           }else{
                                   System.out.println(PURCHASE_NOT_ENOUGH_FUNDS);
                               }
                           }
                       }
                   }

               if(moneyProvided <= 0){
                   System.out.println(PURCHASE_NOT_ENOUGH_FUNDS);
               }
            }
            if (choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
                //Returns customers input money, balance updates to 0, return main menu
                double quarter = .25;
                double dime = .10;
                double nickel = .05;

                int quarterCounter = 0;
                int dimeCounter = 0;
                int nickelCounter = 0;

                while(moneyProvided > 0){
                    if(moneyProvided >= .25){
                        moneyProvided-=quarter;
                        quarterCounter++;
                    }
                    else if(moneyProvided >= .10){
                        moneyProvided -= dime;
                        dimeCounter++;
                    }
                    else if (moneyProvided >= .5){
                        moneyProvided -= nickel;
                        nickelCounter++;
                    }
                    else{
                        moneyProvided -= moneyProvided;
                    }
                }
                System.out.println("Your change is: ");
                System.out.println(quarterCounter + "Quarters");
                System.out.println(dimeCounter + "Dimes");
                System.out.println(nickelCounter + "Nickels");


                break;
            }
        }
    }


}
