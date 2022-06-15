package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ManageContacts extends Screen {

    private static final String greet = "Contacts Management";
    private final int supplierId;

    public ManageContacts() {
        // TODO: Supplier pass SupplierId
        super(greet);
        supplierId = 1;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        printMenu(resp, new String[]{"Show Contacts"});
        printForm(resp, new String[] {"nameAdd", "phone" }, new String[]{"Name", "Phone number"}, new String[]{"Add Contact"});
        printForm(resp, new String[] {"nameRemove" }, new String[]{"Name"}, new String[]{"Remove Contact"});


        handleError(resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Add Contact")) {
            addContact(req, resp);
        }
        else if(isButtonPressed(req, "Remove Contact")){
            removeContact(req, resp);
        }
        if(getIndexOfButtonPressed(req) == 0){
            showContacts(req, resp);
        }

    }

    private void addContact(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String name = req.getParameter("nameAdd");
            String phone = req.getParameter("phone");
            if(controller.addSupplierContact(supplierId, name, phone) ){

                // TODO: Supplier change this to normal print!
                setError(String.format("Added Contact %s", name));
                refresh(req, resp);
            }
            else{
                setError("Contact wasn't added!");
                refresh(req, resp);
            }
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private void removeContact(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String name = req.getParameter("nameRemove");
            if(controller.removeContact(supplierId, name)){

                // TODO: Supplier change this to normal print!
                setError(String.format("Removed Contact %s", name));
                refresh(req, resp);
            }
            else{
                setError("Contact wasn't removed!");
                refresh(req, resp);
            }
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private void showContacts(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<String> contacts = controller.getAllContacts(supplierId);

            // TODO: Supplier change this to normal print!
            if(contacts.size() > 0){
                for(String s : contacts){
                    // TODO: Supplier change this to normal print!
                    setError(s);
                    refresh(req, resp);
                }
            }
            else{
                // TODO: Supplier change this to normal print!
                setError("[NO CONTACTS!]");
                refresh(req, resp);            }
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }

    }

}
