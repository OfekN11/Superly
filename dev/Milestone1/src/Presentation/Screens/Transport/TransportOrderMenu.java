package Presentation.Screens.Transport;

import Presentation.Objects.Transport.TransportOrder;
import Presentation.Screens.Screen;

public class TransportOrderMenu extends Screen {
    private static final String[] menuOptions = {
            "Add product",               //1
            "Remove product",            //2
            "Update product quantity",   //3
            "Close order",               //4
            "Exit"                       //5
    };

    public TransportOrderMenu(Screen caller) {
        super(caller, menuOptions);
    }

    @Override
    public void run() {
        System.out.println("\nCreate Transport Order:");
        int srcID = getSiteID("source");
        int dstID = getSiteID("destination");
        TransportOrder to = new TransportOrder(srcID, dstID);
        int option = 0;
        while (option != 4 && option != 5) {
            option = runMenu();
            try {
                switch (option) {
                    case 1:
                        addProduct(to);
                        break;
                    case 2:
                        removeProduct(to);
                        break;
                    case 3:
                        updateProduct(to);
                        break;
                    case 4:
                        closeOrder(to);
                        break;
                    case 5:
                        endRun();
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
    }

    private void closeOrder(TransportOrder to) throws Exception {
        to.canCloseOrder();
        controller.addTransportOrder(to.getSrcID(), to.getDstID(), to.getProductList());
    }

    private void updateProduct(TransportOrder to) throws Exception {
        int productSN = getSerialNumber();
        int productQuantity = getProductQuantity();
        to.updateProduct(productSN, productQuantity);
    }

    private void removeProduct(TransportOrder to) throws Exception {
        int productSN = getSerialNumber();
        to.removeProduct(productSN);
    }

    private void addProduct(TransportOrder to) throws Exception {
        int productSN = getSerialNumber();
        int productQuantity = getProductQuantity();
        to.addProduct(productSN, productQuantity);
    }

    private int getSiteID(String siteType)
    {
        System.out.println("Enter " + siteType + " ID:");
        int siteID = scanner.nextInt();
        while(siteID < 0){
            System.out.println("Please insert legal ID:");
            siteID = scanner.nextInt();
        }
        return siteID;
    }
    private int getSerialNumber()
    {
        System.out.println("Enter product serial number:");
        int serialNumber = scanner.nextInt();
        while(serialNumber < 0){
            System.out.println("Please insert legal product serial number:");
            serialNumber = scanner.nextInt();
        }
        return serialNumber;
    }
    private int getProductQuantity()
    {
        System.out.println("Enter product quantity:");
        int quantity = scanner.nextInt();
        while(quantity < 0){
            System.out.println("Please insert legal product quantity:");
            quantity = scanner.nextInt();
        }
        return quantity;
    }
    
}
