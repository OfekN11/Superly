package Presentation.WebPresentation.Screens.ViewModels.Suppliers;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class AddItemToAgreement extends Screen {


    private static final String greet = "Add Item";


    public AddItemToAgreement() {
        super(greet);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        //int supId = getSupplierId( req, resp);

        printForm(resp, new String[] {"productId", "idBySupplier", "manufacturer", "pricePerUnit", "bulkPrices"}
        , new String[]{"Product ID", "ID by Supplier", "Manufacturer", "Price Per Unit", "Bulk Prices"}, new String[]{"Add Item To Agreement"});


        handleError(resp);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        if (isButtonPressed(req, "Add Item To Agreement")) {
            try {
                int supId = getSupplierId( req, resp);
                int productId = Integer.parseInt(req.getParameter("productId"));
                int idBySupplier = Integer.parseInt(req.getParameter("idBySupplier"));
                String manufacturer = req.getParameter("manufacturer");
                float ppu = Float.parseFloat(req.getParameter("pricePerUnit"));
                String bulkString = req.getParameter("bulkPrices");

                String[] bulkArr = bulkString.replaceAll("\\s+","").split(",");
                if(bulkArr.length % 2 != 0){
                    System.out.println("Inserted wrong or not complete values, please try again");
                }
                Map<Integer, Integer> bulkMap = new HashMap<>();
                for(int i=0; i<bulkArr.length; i++){
                    bulkMap.put(Integer.parseInt(bulkArr[i]), Integer.parseInt(bulkArr[i+1]));
                    i++;
                }
                if(controller.addItemToAgreement(supId, productId, idBySupplier, manufacturer, ppu, bulkMap)) {

                    setError(String.format("Added New Item."));
                    refresh(req, resp);
                }
                else{
                    setError("Item wasn't added!");
                    refresh(req, resp);
                }
            } catch (NumberFormatException e1){
                setError("Please enter a number!");
                refresh(req, resp);
            }
            catch (Exception e) {
                setError(e.getMessage());
                refresh(req, resp);
            }

        }
    }



    private int getSupplierId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int supId = -1;
        supId = Integer.parseInt(getCookie("supplierId", req, resp, 5));
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
