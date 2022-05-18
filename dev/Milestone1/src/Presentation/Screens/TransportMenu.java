package Presentation.Screens;

public class TransportMenu extends Screen{

    private static final String[] menuOptions = {
            "Print employment conditions",  //1
            "Update name",                  //2
            "Update bank details",          //3
            "Update salary per shift",      //4
            "Update certifications",        //5
            "Calculate Salary",             //6
            "Manage work constraints",      //7
            "Print upcoming shifts"         //8
    };
    public TransportMenu(Screen caller) {
        super(caller, menuOptions);
    }

    @Override
    public void run() {

    }
}
