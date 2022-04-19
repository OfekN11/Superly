package SuperLee.PresentationLayer.SuppliersCLI;

import SuperLee.BusinessLayer.Pair;
import SuperLee.ServiceLayer.Result;
import SuperLee.ServiceLayer.ServiceSupplierObject;
import SuperLee.ServiceLayer.SupplierService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class CLI {
    private SupplierService service;
    private boolean terminateSystem;
    private static Scanner scan = new Scanner(System.in);


    public CLI(){
        service = null;
        terminateSystem = false;
    }

    private void init(){
        service = new SupplierService();
        boolean terminateSystem = false;

        System.out.println("Welcome to Suppliers Management!\n");
        System.out.println("When you have the ability to choose one out of several options, insert the number of the option and press \"Enter\"\n\n");

        homePage();
    }

    private void homePage(){
        int input;
        boolean correctInput;

        while(!terminateSystem){
            System.out.println("What would you like to do?");
            System.out.println("1) View supplier's card");
            System.out.println("2) Add supplier");
            System.out.println("3) Remove supplier");
            System.out.println("4) Exit\n");

            input = scan.nextInt();
            correctInput = false;

           while(!correctInput){
               switch (input){ // temporary
                   case 1 -> {
                       supplierCard();
                       correctInput = true ;
                   }
                   case 2 -> {
                       addSupplier();
                       correctInput = true ;
                   }
                   case 3 -> {
                       removeSupplier();
                       correctInput = true ;
                   }
                   case 4 -> {
                       terminateSystem = true;
                       correctInput = true ;
                   }
                   default -> System.out.println("You inserted wrong value, please try again.");
               }
           }

        }

        try{
            System.out.println("\n\n\nSee you next time!");
            TimeUnit.SECONDS.sleep(3);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void supplierCard(){
        int input;
        boolean correctInput = false;

        System.out.println("Insert the supplier ID you wish to view, then press \"Enter\" to continue.\n");

        while(!correctInput){
            input = scan.nextInt();

            Result<ServiceSupplierObject> r = service.getSupplierInfo(input);

            if(r.isOk()){
                System.out.println(r.getValue().toString());
                correctInput = true;
            }
            else{
                System.out.println("No such supplier or your input is incorrect, please try again.");
            }
        }

        System.out.println("\n");

        System.out.println("Choose the next operation: ");

        System.out.println("1) Edit card");
        System.out.println("2) View agreement");
        System.out.println("3) New agreement");
        System.out.println("4) View contacts");
        System.out.println("5) View represented manufacturers");
        System.out.println("6) Back to home page\n");
        correctInput = false;

        while (!correctInput){
            input = scan.nextInt();

            switch (input){
                case 1 -> {
                    editCard();
                    correctInput = true;
                }
                case 2 -> {
                    viewAgreement();
                    correctInput = true;
                }
                case 3 -> {
                    newAgreement();
                    correctInput = true;
                }
                case 4 -> {
                    viewContacts();
                    correctInput = true;
                }
                case 5 -> {
                    viewRepresentedManufacturers();
                    correctInput = true;
                }
                case 6 -> {
                    correctInput = true;
                    System.out.println("Returning\n\n");
                }
                default -> System.out.println("You inserted wrong value, please try again.");
            }
        }

        System.out.println("\n\n");

    }

    private void addSupplier(){
        int input;
        boolean _continue = true;

        boolean correctInput;
        int id, bankNumber;
        String name, address, payingAgreement, contact, manufacturer;
        ArrayList<Pair<String, String>> contacts;
        boolean done;
        String[] splitedContact;
        ArrayList<String> manufacturers;

        System.out.println("For each detail you insert, please press \"Enter\" after the insertion to continue.\n");

        while(_continue){
            correctInput = false;
            contacts = new ArrayList<>();
            done = false;
            manufacturers = new ArrayList<>();

            System.out.println("ID: ");
            id = scan.nextInt();
            System.out.println("Name: ");
            name = scan.nextLine();
            System.out.println("Bank number: ");
            bankNumber = scan.nextInt();
            System.out.println("Address: ");
            address = scan.nextLine();
            System.out.println("Paying agreement: ");
            payingAgreement = scan.nextLine();

            System.out.println("Now, please insert contacts in the following format, then press \"Enter\".\nTo end the insertion, write \"Done\" and then press \"Enter\":\n");
            System.out.println("Name, phone-number\nFor example:\nIsrael, 0591234567\n");

            while(!done){
                contact = scan.nextLine();

                if(Objects.equals(contact, "Done")){
                    done = true;
                }
                else{
                    if(contact.length() > 12 && contact.contains(",")){ //12 is the minimal length according to the format
                        splitedContact = contact.split(",");
                        contacts.add(new Pair<>(splitedContact[0], splitedContact[1]));
                    }
                }
            }

            System.out.println("\n\n");

            System.out.println("At last, please enter the names of the manufacturers represented by the supplier. To end the insertion, write \"Done\" and press \'\"Enter\":\n");

            done = false;

            while(!done){
                manufacturer = scan.nextLine();

                if(Objects.equals(manufacturer, "Done")){
                    done = true;
                }
                else{
                    if(!manufacturer.isEmpty()){
                        manufacturers.add(manufacturer);
                    }
                }
            }

            Result<Boolean> r = service.addSupplier(id, name, bankNumber, address, payingAgreement, contacts, manufacturers);

            if(r.isOk()){
                System.out.println("The new supplier was added successfully to the data base.\n\n");
            }
            else{
                System.out.println("Something went wrong, please try again.\n\n");
                return;
            }

            System.out.println("Choose your next action: ");
            System.out.println("1) New agreement to supplier");
            System.out.println("2) Add another supplier");
            System.out.println("3) Back to Home Page\n");

            while(!correctInput){
                input = scan.nextInt();

                switch (input){
                    case 1 -> {
                        newAgreement();
                        correctInput = true;
                        _continue = false;
                    }
                    case 2 -> {
                        correctInput = true;
                    }
                    case 3 -> {
                        correctInput = true;
                        System.out.println("Returning\n\n");
                        _continue = false;
                    }
                    default -> System.out.println("You inserted wrong value, please try again.");
                }
            }
        }



    }

    private void removeSupplier(){
        int input;
        boolean _continue = true, done, correctInput;

        while(_continue){
            done = false;
            System.out.println("Insert the ID of the supplier you wish to delete.");
            System.out.println("WARNING! this action is finite!\n");

            while(!done){
                correctInput = false;
                input = scan.nextInt();

                Result<Boolean> r = service.removeSupplier(input);

                if(r.isOk()){
                    done = true;
                    System.out.println("Supplier was removed successfully, please chose:");
                    System.out.println("1) Remove another supplier");
                    System.out.println("2) Back to home Page");

                    while(!correctInput) {
                        input = scan.nextInt();

                        switch (input) {
                            case 1 -> {
                                correctInput = true;
                                System.out.print("\n\n");
                            }
                            case 2 -> {
                                correctInput = true;
                                _continue = false;
                                System.out.print("Returning\n\n");
                            }
                            default -> System.out.println("You inserted wrong value, please try again.");
                        }
                    }
                }
                else{
                    System.out.println("Something went wrong, please try again.\n");
                }
            }
        }
    }

    private void editCard(){

    }

    private void viewAgreement(){

    }

    private void newAgreement(){}

    private void viewContacts(){

    }

    private void viewRepresentedManufacturers(){}



}
