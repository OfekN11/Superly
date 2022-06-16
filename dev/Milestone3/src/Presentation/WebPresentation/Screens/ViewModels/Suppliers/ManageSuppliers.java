package Presentation.WebPresentation.Screens.ViewModels.Suppliers;

import Globals.Pair;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;


public class ManageSuppliers extends Screen {

    private static final String greet = "Manage Suppliers for Storekeeper and Store Manager";
    private static final String removeButton = "Remove Supplier";
    private static final String addButton = "Add Supplier";


    public ManageSuppliers() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        printForm(resp, new String[] {"ID"}, new String[]{"Supplier ID"}, new String[]{removeButton});

        printForm(resp, new String[] {"name", "bankNumber", "address", "payingAgreement", "contacts", "manufacturers"},
                        new String[]{"Name", "Bank Number", "Address", "Paying agreement", "Contacts", "Manufacturers" }, new String[]{addButton});
        printInstructions(req, resp);

        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, removeButton)){
            try {
                int supplierId = Integer.parseInt(req.getParameter("ID"));
                if(controller.removeSupplier(supplierId) ){

                    // TODO: Supplier It opens a new window!, if I put it in goGet it works
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format("Removed supplier %d", supplierId)));
                    // TODO: Supplier change this to normal print!
                    //setError(String.format("Removed supplier %d", supplierId));
                    //refresh(req, resp);
                }
                else{
                    setError("Supplier wasn't removed!");
                    refresh(req, resp);
                }
            }catch (NumberFormatException e1){
                setError("Please enter a number!");
                refresh(req, resp);
            }
            catch (Exception e) {
                setError(e.getMessage());
                refresh(req, resp);
            }
        }
        else if(isButtonPressed(req, addButton)){
            addSupplier(req, resp);
        }

    }


    private void printInstructions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            PrintWriter out = resp.getWriter();
            out.println("Enter Contacts like this : Name1, phone-number1, Name2, phone-number2  ");
            // TODO: Supplier Fix the \n
            // TODO: Supplier change this to normal print!
            out.println(" For example:Israel, 0591234567");
            out.println(" Enter manufacturers divided by , like this : Osem, Elit");

        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req,resp);
        }

    }

    private void addSupplier(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            String name = req.getParameter("name");
            int bankNumber = Integer.parseInt(req.getParameter("bankNumber"));
            String address = req.getParameter("address");
            String payingAgreement = req.getParameter("payingAgreement");

            ArrayList<Pair<String, String>> contacts = splitContatcts(req, resp);

            String manu = req.getParameter("manufacturers");
            ArrayList<String> manufacturers = new ArrayList<>(Arrays.asList(manu.split(",")));
            if(isError()){
                refresh(req, resp);
                return;
            }
            int supplierId = controller.addSupplier(name, bankNumber, address, payingAgreement, contacts, manufacturers);
            if(supplierId != -1){
                PrintWriter out = resp.getWriter();
                out.print("Supplier ");
                out.print(supplierId);
                out.print(" was added successfully to the data base");
                // TODO: Supplier change this to normal print!
            }
            else{
                setError("Supplier wasn't added!");
                refresh(req, resp);
            }
        }
        catch (NumberFormatException e1){
        setError("Please enter a number!");
        refresh(req, resp);
    }
        catch (Exception e){
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private ArrayList<Pair<String, String>> splitContatcts(HttpServletRequest req, HttpServletResponse resp) {
        ArrayList<Pair<String, String>> contacts = new ArrayList<>();
        String[] splitContact;
        String contact = req.getParameter("contacts");
        splitContact = contact.split(",");
        for(int i = 0; i < splitContact.length; i += 2){
            if(i+1 >= splitContact.length){
                setError("Missing details in Contacts!");
                return new ArrayList<>();
            }
            contacts.add(new Pair<>(splitContact[0], splitContact[1]));
        }
        return contacts;
    }


}
