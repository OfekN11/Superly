package Presentation.WebPresentation.Screens.ViewModels.Suppliers;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ManageContacts extends Screen {

    private static final String greet = "Contacts Management";

    public ManageContacts() {
        // TODO: Supplier pass SupplierId
        super(greet);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        int supId = getSupplierId(req, resp);

        printMenu(resp, new String[]{"Show Contacts"});
        printForm(resp, new String[] {"nameAdd", "phone" }, new String[]{"Name", "Phone number"}, new String[]{"Add Contact"});
        printForm(resp, new String[] {"nameRemove" }, new String[]{"Name"}, new String[]{"Remove Contact"});

        if (req.getParameter("showContacts") != null && req.getParameter("showContacts").equals("true")){
            showContacts(req, resp, supId);
        }

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
            resp.sendRedirect("/ManageContacts?showContacts=true");
            //showContacts(req, resp);
        }

    }

    private void addContact(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String name = req.getParameter("nameAdd");
            String phone = req.getParameter("phone");

            int supId = getSupplierId(req, resp);

            if(controller.addSupplierContact(supId, name, phone) ){

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

            int supId = getSupplierId(req, resp);
            if(controller.removeContact(supId, name)){

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

    private void showContacts(HttpServletRequest req, HttpServletResponse resp, int supId) throws IOException {
        try {

            List<String> contacts = controller.getAllContacts(supId);
            if(contacts.size() > 0){
                PrintWriter out = resp.getWriter();
                out.println("<h4>");
                for(String s : contacts){
                    out.println(s + "<br>");
                }
                out.println("</h4>");
            }
            else{
                setError("[NO CONTACTS!]");
                //refresh(req, resp);
            }
        } catch (Exception e) {
            setError(e.getMessage());
            //refresh(req, resp);
        }

    }



    private int getSupplierId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int supId = -1;
        supId = Integer.parseInt(getCookie("supplierId", req, resp, 10));
        return supId;
    }

    private String getCookie(String name, HttpServletRequest req, HttpServletResponse resp, int time) throws IOException {
        String cookie = "";
        for (Cookie c : req.getCookies()) {
            if (c.getName().equals(name)) {
                cookie = c.getValue();
            }
            c.setMaxAge((int) TimeUnit.MINUTES.toSeconds(time)); //time of life of the cookie, if bot listed its infinite
            resp.addCookie(c);
        }
        return cookie;
    }



}
