package Presentation.Screens;

public class DocumentMenu extends Screen{
    private static final String[] menuOptions = {
            "View existing shifts",     //1
            "Add shifts",               //2
            "Remove shifts",            //3
            "Manage existing shifts",   //4
            "Exit"                      //5
    };
    public DocumentMenu(Screen caller) {
        super(caller, menuOptions);
    }

    @Override
    public void run() {

    }
}
