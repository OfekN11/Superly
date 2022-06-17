package Presentation.WebPresentation.Screens.Models.HR;

import Presentation.WebPresentation.Screens.ViewModels.Suppliers.SupplierMainMenuStoreManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Admin extends Employee{

    private static final String GREET = "Admin Page";

    private static final String[] MENU_OPTIONS = {
            "Suppliers Menu"

    };

    public Admin() {
        super(GREET, MENU_OPTIONS);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (getIndexOfButtonPressed(req)){
            case 0:
                redirect(resp, SupplierMainMenuStoreManager.class);
                break;
        }
    }
}
