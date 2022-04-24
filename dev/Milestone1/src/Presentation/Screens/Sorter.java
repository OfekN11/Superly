package Presentation.Screens;

import Globals.Enums.JobTitles;

public class Sorter extends Employee{
    private static final String[] extraMenuOptions  = {
            "Exit"              //9
    };

    public Sorter(Screen caller, Domain.Service.Objects.Sorter sEmployee) {
        super(caller, sEmployee, extraMenuOptions);
    }

    @Override
    JobTitles getJobTitle() {
        return JobTitles.Sorter;
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Management Menu of " + name + "!");
        int option = 0;
        while (option != 10) {
            option = runMenu();
            if (option <= 8)
                handleBaseOptions(option);
            else if (option == 9)
                endRun();
        }
    }
}
