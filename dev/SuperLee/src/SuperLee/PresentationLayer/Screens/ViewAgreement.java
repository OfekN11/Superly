package SuperLee.PresentationLayer.Screens;

public class ViewAgreement extends Screen {


    private int supplierID;

    private static final String[] menuOptions = {
            "",          //1
            "t",           //2
            "t",           //3
            "s",       //4
            "s",            //5
            "er",        //6
            "er", //7
            "Exit"                          //8
    };

    public ViewAgreement(Screen caller, int id) {
        super(caller, menuOptions);
        supplierID = id;
    }


    @Override
    public void run() {

    }
}
