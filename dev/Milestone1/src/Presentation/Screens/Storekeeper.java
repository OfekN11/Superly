package Presentation.Screens;

import Globals.Enums.JobTitles;

public class Storekeeper extends Employee{
    private static final String[] extraMenuOptions  = {
            "Exit"              //9
    };

    public Storekeeper(Screen caller, Domain.Service.Objects.Storekeeper sEmployee) {
        super(caller, sEmployee, extraMenuOptions);
    }

    @Override
    JobTitles getJobTitle() {
        return JobTitles.Storekeeper;
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Management Menu of " + name + "!");
        int option = 0;
        while (option != 10) {
            try {
                option = runMenu();
                if (option <= 8)
                    handleBaseOptions(option);
                else if (option == 9)
                    endRun();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
    }
}
