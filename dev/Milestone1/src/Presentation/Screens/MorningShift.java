package Presentation.Screens;

import Globals.Enums.JobTitles;
import Globals.Enums.ShiftTypes;

import java.text.SimpleDateFormat;

public class MorningShift extends Shift{
    private static final String[] extraMenuOptions  = {
            "Exit"  //4
    };
    public MorningShift(Screen caller, Domain.Service.Objects.MorningShift sShift) {
        super(caller, sShift, extraMenuOptions);
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Management Menu for Morning shift of " + new SimpleDateFormat("dd-MM-yyyy").format(date) + "!");
        int option = 0;
        while (option != 4) {
            option = runMenu();
            if (option < 4)
                handleBaseOptions(option);
            else if (option == 4)
                endRun();
        }
    }

    @Override
    protected ShiftTypes getType() {
        return ShiftTypes.Morning;
    }
}
