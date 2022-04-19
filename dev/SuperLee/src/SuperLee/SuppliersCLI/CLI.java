package SuperLee.SuppliersCLI;

import SuperLee.ServiceLayer.SupplierService;

import java.util.Scanner;


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

        homePage();
    }

    private void homePage(){
        int input;

        while(!terminateSystem){
            System.out.println("What would you like to do?\nPress the number of the operation you wish and then press \"Enter\" to continue.\n");

        }

    }



}
