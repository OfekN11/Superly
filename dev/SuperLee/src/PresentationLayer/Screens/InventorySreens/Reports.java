package PresentationLayer.Screens.InventorySreens;

import Domain.ServiceLayer.InventoryObjects.DefectiveItemReport;
import Domain.ServiceLayer.InventoryObjects.PurchaseFromSupplierReport;
import Domain.ServiceLayer.InventoryObjects.StockReport;
import Domain.ServiceLayer.Result;
import PresentationLayer.Screens.Screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reports extends Screen {

    private static final String[] menuOptions = {
            "Print employment conditions",  //1
            "Update name",                  //2
            "Update bank details",          //3
            "Update salary per shift",      //4
            "Update certifications",        //5
            "Calculate Salary",             //6
            "Manage work constraints",      //7
            "Print upcoming shifts"         //8
    };

    public Reports(Screen caller, String[] extraMenuOptions) {
        super(caller, Stream.concat(Arrays.stream(menuOptions), Arrays.stream(extraMenuOptions)).toArray(String[]::new));
    }

    public void run() {
        System.out.println("\nWelcome to the Management Menu of Sale Management");
        int option = 0;
        while (option != 9) {
            option = runMenu();
            try {
                if (option <= 8)
                    handleBaseOptions(option);
                else if (option == 9)
                    endRun();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
    }


    protected void handleBaseOptions(int option) throws Exception {
        switch (option) {
            case 1:
//                System.out.println(employmentConditions);
                break;
            case 2:
//                updateName();
                break;
            case 3:
//                updateBankDetails();
                break;
            case 4:
//                updateSalary();
                break;
            case 5:
//                updateCertifications();
                break;
            case 6:
//                calculateSalary();
                break;
            case 7:
//                manageConstraints();
                break;
            case 8:
//                printUpcomingShifts();
        }
    }

    public void storeStockReport() {
        System.out.println("Please insert store IDs, separated by commas without spaces");
        System.out.println("Current store IDs are:");
        System.out.println(controller.getStoreIDs().getValue());
        System.out.println("If you would like all stores, you can enter a blank list");
        String input = scanner.nextLine();
        List<Integer> storeIDs;
        if (input.equals(""))
            storeIDs = controller.getStoreIDs().getValue();
        else
            storeIDs = Arrays.asList(input.split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert category IDs, separated by commas without spaces");
        listCategoryIDs();
        System.out.println("If you would like all categories, you can enter a blank list");
        input = scanner.nextLine();
        List<Integer> categoryIDs;
        if (input.equals(""))
            categoryIDs = getCatIDs();
        else
            categoryIDs = Arrays.asList(input.split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        Result<List<StockReport>> r = controller.storeStockReport(storeIDs, categoryIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<StockReport> stockReports = r.getValue();
            for (StockReport s : stockReports)
                System.out.println(s);
            if (stockReports.isEmpty())
                System.out.println("the store has no products registered to it in the system or it has been removed");
        }
    }

    public void getMinStockReport() {
        Result<List<StockReport>> r = controller.getMinStockReport();
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<StockReport> stockReports = r.getValue();
            for (StockReport s : stockReports)
                System.out.println(s);
            if (stockReports.isEmpty())
                System.out.println("There are no products under the min amount");
        }
    }

    public void getDiscountFromSupplierHistory() {
        System.out.println("Please insert product ID for which you would like to see history");
        int id = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<List<PurchaseFromSupplierReport>> r = controller.getDiscountFromSupplierHistory(id);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<PurchaseFromSupplierReport> purchaseFromSupplierReports = r.getValue();
            for (PurchaseFromSupplierReport dr : purchaseFromSupplierReports)
                System.out.println(dr);
            if (purchaseFromSupplierReports.isEmpty())
                System.out.println("there are no discounts from suppliers for this product in the system");
        }
    }

    public void getPurchaseFromSupplierHistory() {
        System.out.println("Please insert product ID for which you would like to see history");
        int id = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<List<PurchaseFromSupplierReport>> r = controller.getPurchaseFromSupplierHistory(id);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<PurchaseFromSupplierReport> purchaseFromSupplierReports = r.getValue();
            for (PurchaseFromSupplierReport dr : purchaseFromSupplierReports)
                System.out.println(dr);
            if (purchaseFromSupplierReports.isEmpty())
                System.out.println("there are no purchases from suppliers for this product in the system");
        }
    }



    public void reportExpired() {
        int store = getStoreID();
        System.out.println("Which product is expired? (insert ID)");
        int productID = scanner.nextInt();
        System.out.println("How much of the product is expired?");
        int amount = scanner.nextInt();
        System.out.println("Please enter your ID");
        int employeeID = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        System.out.println("Please add a description (not mandatory)");
        String description = scanner.nextLine();
        Result<DefectiveItemReport> r = controller.reportExpired(store, productID, amount, employeeID, description);
        if (r.isError())
            System.out.println(r.getError());
        else {
            DefectiveItemReport eir = r.getValue();
            System.out.println(eir);
            isUnderMin(store, productID);
        }
    }

    public void expiredItems() {
        System.out.println("Please insert for which items you would like to see expired item history: (choose the corresponding number)");
        System.out.println("1: A product/products");
        System.out.println("2: A category/categories");
        System.out.println("3: A store/number of stores");
        System.out.println("4: all products");
        int expireCase = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        switch (expireCase) {
            case (1):
                expiredItemsByProduct();
                break;
            case (2):
                expiredItemsByCategory();
                break;
            case (3):
                expiredItemsByStore();
                break;
            case (4):
                expiredItemsAll();
                break;
            default:
                System.out.println("Incorrect command, please try again");
        }
    }

    public void expiredItemsAll() {
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = controller.getExpiredItemReportsByStore(start, end, new ArrayList<>());
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport eir : reportList)
                System.out.println(eir);
            if (reportList.isEmpty())
                System.out.println("There were no reports matching search");
        }
    }

    public void expiredItemsByStore() {
        System.out.println("Please insert store IDs, separated by commas without spaces");
        List<Integer> storeIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = controller.getExpiredItemReportsByStore(start, end, storeIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport eir : reportList)
                System.out.println(eir);
            if (reportList.isEmpty())
                System.out.println("There were no reports matching search");
        }
    }

    public void expiredItemsByCategory() {
        System.out.println("Please insert category IDs, separated by commas without spaces");
        List<Integer> categoryIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = controller.getExpiredItemReportsByCategory(start, end, categoryIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport eir : reportList)
                System.out.println(eir);
            if (reportList.isEmpty())
                System.out.println("There were no reports matching search");
        }
    }

    public void expiredItemsByProduct() {
        System.out.println("Please insert product IDs, separated by commas without spaces");
        List<Integer> productIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = controller.getExpiredItemReportsByProduct(start, end, productIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport eir : reportList)
                System.out.println(eir);
            if (reportList.isEmpty())
                System.out.println("There were no reports matching search");
        }
    }

    public void reportDamaged() {
        int store = getStoreID();
        System.out.println("Which product is damaged? (insert ID)");
        int productID = scanner.nextInt();
        System.out.println("How much of the product is damaged?");
        int amount = scanner.nextInt();
        System.out.println("Please enter your ID");
        int employeeID = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        System.out.println("Please describe the damage");
        String description = scanner.nextLine();
        Result<DefectiveItemReport> r = controller.reportDamaged(store, productID, amount, employeeID, description);
        if (r.isError())
            System.out.println(r.getError());
        else {
            DefectiveItemReport dir = r.getValue();
            System.out.println(dir);
            isUnderMin(store, productID);
        }
    }

    public void defectiveItems() {
        System.out.println("Please insert for which items you would like to see defect item history: (choose the corresponding number)");
        System.out.println("1: A product/products");
        System.out.println("2: A category/categories");
        System.out.println("3: A store/number of stores");
        System.out.println("4: all products");
        int defectCase = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        switch (defectCase) {
            case (1):
                defectiveItemsByProduct();
                break;
            case (2):
                defectiveItemsByCategory();
                break;
            case (3):
                defectiveItemsByStore();
                break;
            case (4):
                defectiveItemsAll();
                break;
            default:
                System.out.println("Incorrect command, please try again");
        }
    }
    public void defectiveItemsAll() {
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = controller.getDefectiveItemsByStore(start, end, new ArrayList<>());
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
            if (reportList.isEmpty())
                System.out.println("There were no reports matching search");
        }
    }

    public void defectiveItemsByStore() {
        System.out.println("Please insert store IDs, separated by commas without spaces");
        List<Integer> storeIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = controller.getDefectiveItemsByStore(start, end, storeIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
            if (reportList.isEmpty())
                System.out.println("There were no reports matching search");
        }
    }

    public void defectiveItemsByCategory() {
        System.out.println("Please insert category IDs, separated by commas without spaces");
        List<Integer> categoryIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = controller.getDefectiveItemsByCategory(start, end, categoryIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
            if (reportList.isEmpty())
                System.out.println("There were no reports matching search");
        }
    }

    public void defectiveItemsByProduct() {
        System.out.println("Please insert product IDs, separated by commas without spaces");
        List<Integer> productIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = controller.getDefectiveItemsByProduct(start, end, productIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
            if (reportList.isEmpty())
                System.out.println("There were no reports matching search");
        }
    }

    public void damagedItems() {
        System.out.println("Please insert for which items you would like to see damaged item history: (choose the corresponding number)");
        System.out.println("1: A product/products");
        System.out.println("2: A category/categories");
        System.out.println("3: A store/number of stores");
        System.out.println("4: all products");
        int damagedCase = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        switch (damagedCase) {
            case (1):
                damagedItemsByProduct();
                break;
            case (2):
                damagedItemsByCategory();
                break;
            case (3):
                damagedItemsByStore();
                break;
            case (4):
                damagedItemsAll();
                break;
            default:
                System.out.println("Incorrect command, please try again");
        }
    }

    public void damagedItemsAll() {
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = controller.getDamagedItemsReportByStore(start, end, new ArrayList<>());
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
            if (reportList.isEmpty())
                System.out.println("There were no reports matching search");
        }
    }

    public void damagedItemsByStore() {
        System.out.println("Please insert store IDs, separated by commas without spaces");
        List<Integer> storeIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = controller.getDamagedItemsReportByStore(start, end, storeIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
            if (reportList.isEmpty())
                System.out.println("There were no reports matching search");
        }
    }

    public void damagedItemsByCategory() {
        System.out.println("Please insert category IDs, separated by commas without spaces");
        List<Integer> categoryIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = controller.getDamagedItemsReportByCategory(start, end, categoryIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
            if (reportList.isEmpty())
                System.out.println("There were no reports matching search");
        }
    }

    public void damagedItemsByProduct() {
        System.out.println("Please insert product IDs, separated by commas without spaces");
        List<Integer> productIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = controller.getDamagedItemsReportByProduct(start, end, productIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
            if (reportList.isEmpty())
                System.out.println("There were no reports matching search");
        }
    }
}
