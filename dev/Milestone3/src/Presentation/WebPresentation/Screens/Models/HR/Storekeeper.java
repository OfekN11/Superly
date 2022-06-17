package Presentation.WebPresentation.Screens.Models.HR;

import Presentation.WebPresentation.Screens.ViewModels.Suppliers.SupplierMainMenuStorekeeper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Storekeeper extends Employee{

    private static final String GREETING = "Welcome Storekeeper ";

    private static final String[] EXTRA_OPTIONS = {
            "Suppliers Menu"
    };

    protected Storekeeper(Domain.Service.Objects.Employee.Storekeeper sStorekeeper) {
        super(sStorekeeper, GREETING, EXTRA_OPTIONS);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int index = getIndexOfButtonPressed(req);
        if (index < BASE_OPTIONS_COUNT) {
            super.doPost(req, resp);
            return;
        }
        index -= BASE_OPTIONS_COUNT;
        switch (index) {
            case 0:
                redirect(resp, SupplierMainMenuStorekeeper.class);
                break;
        }
    }
}