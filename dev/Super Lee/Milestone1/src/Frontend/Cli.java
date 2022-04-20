package Frontend;

import java.util.Scanner;

public class Cli {
    static Scanner reader = new Scanner(System.in);

    private static void login()
    {
        System.out.println("Login:");
        System.out.println("Please enter your details:");
        System.out.print("Username: ");
        String username = reader.next();
        System.out.println("Password: ");
        String password = reader.next();

    }

    private static int mainMenu()
    {
        int choice = 0;
        do {
            System.out.println("""
                Main menu:
                1. Transport Management
                2. Truck system management
                """);
            choice = reader.nextInt();
        }while(choice > 2 | choice < 1);
        return choice;
    }

    public static void main(String[] args)
    {
        login();
        switch (mainMenu())
        {
            case 1:
                //TODO: Transport Management
                break;
            case 2:
                //TODO: Truck system management
                break;
            default:
        }
    }
}
