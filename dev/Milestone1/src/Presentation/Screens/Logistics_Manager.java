package Presentation.Screens;

import Globals.Enums.JobTitles;

public class Logistics_Manager extends Employee{
    private static final String[] extraMenuOptions  = {
            "Exit"              //9
    };

    public Logistics_Manager(Screen caller, Domain.Service.Objects.Logistics_Manager sEmployee) {
        super(caller, sEmployee, extraMenuOptions);
    }

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

    @Override
    JobTitles getJobTitle() {
        return JobTitles.Logistics_Manager;
    }
}
