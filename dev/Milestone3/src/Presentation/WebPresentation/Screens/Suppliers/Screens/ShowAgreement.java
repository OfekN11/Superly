package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Domain.Service.Objects.SupplierObjects.ServiceItemObject;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ShowAgreement extends Screen {

    private final int supplierId;
    private int agreementType;
    private static final String greet = "Agreement Info";
    private static final String greetRoutine = "Routine Supplier";
    private static final String greetByOrder = "By Order Supplier";
    private static final String greetNotTransporting = "Not Transporting Supplier";


    // TODO: Supplier : tried without extends to other classes, see if it works!
    public ShowAgreement(int supplierId) {
        super(greet);
        this.supplierId = supplierId;

        agreementType = getAgreementType();

    }

    //if not using inheritance, we use this constructor!!!!
    public ShowAgreement(){
        super(greet);
        this.supplierId = 1;

        agreementType = getAgreementType();
    }

    private int getAgreementType() {
        try {
            if(controller.isRoutineAgreement(supplierId))
                return 1;
            else if(controller.isByOrderAgreement(supplierId))
                return 2;
        } catch (Exception e) {
            setError(e.getMessage());
        }
        return 3;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);


        printMenu(resp, new String[]{"Show All Items", "Add item to agreement"});
        printForm(resp, new String[] {"idBySupplier"}, new String[]{"ID By Supplier"}, new String[]{"Remove Item"});
        printForm(resp, new String[] {"idBySupplier2"}, new String[]{"ID By Supplier"}, new String[]{"View Item"});

        // TODO: 11/06/2022 Should we do it? maybe it can cause problems...
        printForm(resp, new String[] {"agreementType", "agreementDays" }, new String[]{"Agreement Type", "Agreement Days"}, new String[]{"Change Agreement Type"});
        printInstructions(resp);

        if(agreementType == 1){  //routine
            printForm(resp, new String[] {"agreementDays2" }, new String[]{"Delivery Days"}, new String[]{"Change Delivery Days"});
            printInstructionsDeliveryDays(req, resp);

        }
        else if(agreementType == 2){  //byOrder
            printForm(resp, new String[] {"day" }, new String[]{"Days Until Delivery"}, new String[]{"Change Days Until Delivery"});
            printInstructionsDaysUntilDelivery(resp);
        }

        handleError(resp);
    }

    private void printInstructions(HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<h4>");
        out.println("Type should be 1, 2 or 3 as follows:");
        out.println("1) Routine agreement");
        out.println("2) By order agreement");
        out.println("3) Self-Transport agreement");
        out.println("Enter Agreement Days with ',' between, like this: 1,3,5,6 ");
        out.println("</h4>");
    }

    private void printInstructionsDaysUntilDelivery(HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<h4>");
        out.println("Enter a number which will be the days until delivery.");
        out.println("</h4>");
    }

    private void printInstructionsDeliveryDays(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<h4>");
        out.println("Enter delivery days with ',' between, like this: 1,3,5,6 ");
        out.println("</h4>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);


        if (isButtonPressed(req, "Remove Item")) {
            removeItemFromAgreement(req, resp);
        }
        else if(isButtonPressed(req, "View Item")){
            viewItem(req, resp);
        }
        else if(isButtonPressed(req, "Change Agreement Type")) {
            changeAgreementType(req, resp);
        }
        else if(isButtonPressed(req, "Change Delivery Days")){
            changeRoutineDays(req, resp);
        }
        else if(isButtonPressed(req, "Change Days Until Delivery")){
            changeByOrderDay(req, resp);
        }


        switch (getIndexOfButtonPressed(req)){
            case 0:
                showAllItems(req, resp);
                break;
            case 1:
                // TODO: Suppliers pass supplierId
                redirect(resp, AddItemToAgreement.class);
                break;

        }
    }

    private void viewItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int num = Integer.parseInt(req.getParameter("idBySupplier2"));
            int itemId = controller.getMatchingProductIdForIdBySupplier(num);
            // TODO: Suppliers pass itemId, supplierId   ItemId = ProductId
            redirect(resp, ShowAgreementItem.class);
        } catch (Exception e) {
            setError("Item is not in the system!, Please enter Id By supplier!");
            refresh(req, resp);
        }
    }

    private void changeByOrderDay(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int day = Integer.parseInt(req.getParameter("day"));
            if(controller.changeDaysUntilDelivery(supplierId, day)){
                // TODO: Supplier change this to normal print!
                setError(String.format("Days until delivery updated to %s", day));
                refresh(req, resp);
            }
            else{
                setError("Day wasn't updated!");
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

    private void changeRoutineDays(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String days = req.getParameter("agreementDays2");
            if(days.length() > 0 && controller.setDaysOfDelivery(supplierId, days) ){

                // TODO: Supplier change this to normal print!
                setError(String.format("Days updated to %s", days));
                refresh(req, resp);
            }
            else{
                setError("Days had not been updated!");
                refresh(req, resp);
            }
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private void changeAgreementType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // TODO: Supplier explain what is 1 2 3
            int type = Integer.parseInt(req.getParameter("agreementType"));
            String days = req.getParameter("agreementDays");
            // TODO: Supplier check what it does when not inserting anything for not transporting! should be ""
            if((type == 1 || type == 2 || type == 3) && controller.changeAgreementType(supplierId, type, days)){

                // TODO: Supplier change this to normal print!
                setError("Agreement type was changed successfully");
                refresh(req, resp);

                agreementType = type;
            }
            else{
                setError("Agreement wasn't changed!");
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


    private void removeItemFromAgreement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //!controller.deleteItemFromAgreement(supplierID, input)
        try {
            // TODO: Supplier change this to normal print!
            setError("This action is dangerous! be aware!");

            refresh(req, resp);
            int num = Integer.parseInt(req.getParameter("idBySupplier"));

            int itemId = controller.getMatchingProductIdForIdBySupplier(num);

            if(controller.deleteItemFromAgreement(supplierId, itemId) ){

                // TODO: Supplier change this to normal print!
                setError(String.format("Deleted Item %d", num));
                refresh(req, resp);
            }
            else{
                setError("Item wasn't deleted!");
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

    private void showAllItems(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            List<ServiceItemObject> list = controller.itemsFromOneSupplier(supplierId);
            if(list.isEmpty()){
                setError("[NO ITEMS ARE IN THE AGREEMENT]");
                refresh(req, resp);
            }

            for(ServiceItemObject item : list){
                setError(item.toString());
                refresh(req, resp);
            }
        }
        catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }


    /*
        private void changeDaysOfDelivery() {
        String input;
        boolean correctInput = false;

        System.out.println("Insert the new days of delivery");

        System.out.println("If you want to go back, please insert \"-1\".\n");

        while(!correctInput){
            input = Screen.scanner.nextLine();

            if(input.equals("-1")){
                System.out.println("Returning..\n");
                return;
            }

            if(input.length() > 0){

                try {
                    if(controller.setDaysOfDelivery(supplierID, input)){
                        correctInput = true;
                    }
                    else{
                        System.out.println("Something went wrong, please try again.");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());;
                }
            }
            else{
                System.out.println("Wrong input, please try again.\n");
            }
        }

        System.out.println("Changes saved, returning\n\n");
    }

     */
}
