package Presentation.Screens;

import Globals.Enums.ShiftTypes;

import java.text.SimpleDateFormat;

public class EveningShift extends Shift{
    private static final String[] extraMenuOptions  = {
            "Exit"  //4
    };
    public EveningShift(Screen caller, Domain.Service.Objects.EveningShift sShift) {
        super(caller, sShift, extraMenuOptions);
    }

    @Override
    protected ShiftTypes getType() {
        return ShiftTypes.Evening;
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Management Menu for Morning shift of " + new SimpleDateFormat("dd-MM-yyyy").format(date) + "!");
        int option = 0;
        while (option != 4) {
            option = runMenu();
            try {
                if (option < 4)
                    handleBaseOptions(option);
                else if (option == 4)
                    endRun();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
    }
}
