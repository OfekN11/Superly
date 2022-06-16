package Presentation.WebPresentation.Screens.ViewModels.Suppliers;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ManageManufacturers extends Screen {

    private static final String greet = "Manage Manufacturers";
    //private final int supplierId;

    public ManageManufacturers() {
        // TODO: Supplier pass SupplierId
        super(greet);
        //supplierId = 1;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        //int supId = getSupplierId(req, resp);

        printMenu(resp, new String[]{"Show Manufacturers"});
        printForm(resp, new String[] {"nameAdd"}, new String[]{"Name"}, new String[]{"Add Manufacturer"});
        printForm(resp, new String[] {"nameRemove"}, new String[]{"Name"}, new String[]{"Remove Manufacturer"});


        handleError(resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Add Manufacturer")) {
            addManufacturer(req, resp);
        }
        else if(isButtonPressed(req, "Remove Manufacturer")){
            removeManufacturer(req, resp);
        }
        if(getIndexOfButtonPressed(req) == 0){
            showManufacturers(req, resp);
        }

    }

    private void showManufacturers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int supId = getSupplierId(req, resp);
            List<String> list = controller.getManufacturers(supId);
            if(list.isEmpty()){
                // TODO: Supplier change this to normal print!
                setError("[THERE ARE NO REPRESENTED MANUFACTURERS BY THIS SUPPLIER]");
                refresh(req, resp);
            }
            else{
                for(String s : list){
                    // TODO: Supplier change this to normal print!
                    setError(s);
                    refresh(req, resp);
                }
            }
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private void addManufacturer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String name = req.getParameter("nameAdd");
            int supId = getSupplierId(req, resp);
            if(controller.addSupplierManufacturer(supId, name)){

                // TODO: Supplier change this to normal print!
                setError(String.format("Added manufacturer %s", name));
                refresh(req, resp);
            }
            else{
                setError("Manufacturer wasn't added!");
                refresh(req, resp);
            }
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private void removeManufacturer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int supId = getSupplierId(req, resp);
            String name = req.getParameter("nameRemove");
            if(controller.removeManufacturer(supId, name)){

                // TODO: Supplier change this to normal print!
                setError(String.format("Removed manufacturer %s", name));
                refresh(req, resp);
            }
            else{
                setError("Manufacturer wasn't removed!");
                refresh(req, resp);
            }
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
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
