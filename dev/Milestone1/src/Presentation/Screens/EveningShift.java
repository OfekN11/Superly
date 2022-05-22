package Presentation.Screens;

import Globals.Enums.ShiftTypes;
import Globals.util.HumanInteraction;

import static Globals.util.HumanInteraction.*;

public class EveningShift extends Shift {
    private static final String[] extraMenuOptions = {
            "Exit"  //4
    };

    public EveningShift(Screen caller, Domain.Service.Objects.EveningShift sShift) {
        super(caller, sShift, extraMenuOptions);
    }

    @Override
    public ShiftTypes getType() {
        return ShiftTypes.Evening;
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Management Menu for Morning shift of " + date.format(dateFormat) + "!");
        int option = 0;
        while (option != 4) {
            option = runMenu();
            try {
                if (option < 4)
                    handleBaseOptions(option);
                else if (option == 4)
                    endRun();
            } catch (HumanInteraction.OperationCancelledException ignored) {
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
    }
}