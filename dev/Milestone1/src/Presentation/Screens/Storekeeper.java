package Presentation.Screens;

import Globals.util.HumanInteraction;

public class Storekeeper extends Employee {
    private static final String[] extraMenuOptions = {
            "Exit"              //9
    };

    public Storekeeper(Screen caller, Domain.Service.Objects.Storekeeper sEmployee) {
        super(caller, sEmployee, extraMenuOptions);
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Management Menu of " + name + " the Storekeeper!");
        int option = 0;
        while (option != 9) {
            try {
                option = runMenu();
                if (option <= 8)
                    handleBaseOptions(option);
                else if (option == 9)
                    endRun();
            } catch (HumanInteraction.OperationCancelledException ignored) {
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
    }
}