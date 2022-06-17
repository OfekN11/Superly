package Presentation.WebPresentation.Screens.ViewModels.Transport.Transport.Update;

import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Truck.TruckManagementMenu;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PlaceCarrier extends Screen {
    private static final String greet = "Place Carrier";
    public PlaceCarrier() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        printForm(resp, new String[]{"LN"}, new String[]{"License number"}, new String[]{"Place", "Cancel"});
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Delete")){
            try {
                int ln = Integer.parseInt(req.getParameter("LN"));
                controller.removeTruck(ln);
                //TODO: Print Successful msg
                setError("e.getMessage()");
                refresh(req, resp);
            }catch (NumberFormatException exception){
                setError("Enter a valid license number!");
                refresh(req, resp);
            }
            catch (Exception e) {
                setError(e.getMessage());
                refresh(req, resp);
            }
        }
        else if(isButtonPressed(req, "Cancel"))
            redirect(resp, TruckManagementMenu.class);
    }

}
