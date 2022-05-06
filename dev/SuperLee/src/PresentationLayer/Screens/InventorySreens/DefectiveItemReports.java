package PresentationLayer.Screens.InventorySreens;

import Domain.ServiceLayer.InventoryObjects.DefectiveItemReport;
import Domain.ServiceLayer.Result;
import PresentationLayer.Screens.Screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefectiveItemReports extends Screen {

    private static final String[] menuOptions = {
            "View Damaged Item Reports",  //1
            "View Expired Item Reports",  //2
            "View Defective Item Reports",  //3
            "exit"        //4
    };

    public DefectiveItemReports(Screen caller, String[] extraMenuOptions) {
        super(caller, Stream.concat(Arrays.stream(menuOptions), Arrays.stream(extraMenuOptions)).toArray(String[]::new));
    }

    public void run() {
        System.out.println("\nPlease choose action");
        int option = 0;
        while (option != 4) {
            option = runMenu();
            try {
                if (option <= 4)
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
                damagedItems();
                break;
            case 2:
                expiredItems();
                break;
            case 3:
                defectiveItems();
                break;
            case 4:
                endRun();
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
