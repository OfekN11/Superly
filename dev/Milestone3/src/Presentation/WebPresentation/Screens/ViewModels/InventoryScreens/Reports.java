package Presentation.WebPresentation.Screens.ViewModels.InventoryScreens;

import Domain.Service.Objects.InventoryObjects.DefectiveItemReport;
import Domain.Service.util.Result;
import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Reports extends Screen {

    private static final String greet = "Reports";

    private static final String defectiveByStoreButton = "Get defective items report by store";
    private static final String defectiveByCategoryButton = "Get defective items report by category";
    private static final String defectiveByProductButton = "Get defective items report by product";
    private static final String damagedByStoreButton = "Get damaged items report by store";
    private static final String damagedByCategoryButton = "Get damaged items report by category";
    private static final String damagedByProductButton = "Get damaged items report by product";
    private static final String expiredByStoreButton = "Get expired items report by store";
    private static final String expiredByCategoryButton = "Get expired items report by category";
    private static final String expiredByProductButton = "Get expired items report by product";

    public static final Set<Class<? extends Employee>> ALLOWED = new HashSet<>();

    public Reports() { super(greet, ALLOWED); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!isAllowed(req, resp)) {
            redirect(resp, Login.class);
        }
        header(resp);
        greet(resp);
        printForm(resp, new String[] {"start date", "end date", "store IDs"}, new String[]{"Start Date (yyyy-mm-dd)", "End Date (yyyy-mm-dd)", "Store IDs (3,8,1)"}, new String[]{defectiveByStoreButton});
        printForm(resp, new String[] {"start date", "end date", "category IDs"}, new String[]{"Start Date (yyyy-mm-dd)", "End Date (yyyy-mm-dd)", "Category IDs (3,8,1)"}, new String[]{defectiveByCategoryButton});
        printForm(resp, new String[] {"start date", "end date", "product IDs"}, new String[]{"Start Date (yyyy-mm-dd)", "End Date (yyyy-mm-dd)", "Product IDs (3,8,1)"}, new String[]{defectiveByProductButton});
        printForm(resp, new String[] {"start date", "end date", "store IDs"}, new String[]{"Start Date (yyyy-mm-dd)", "End Date (yyyy-mm-dd)", "Store IDs (3,8,1)"}, new String[]{damagedByStoreButton});
        printForm(resp, new String[] {"start date", "end date", "category IDs"}, new String[]{"Start Date (yyyy-mm-dd)", "End Date (yyyy-mm-dd)", "Category IDs (3,8,1)"}, new String[]{damagedByCategoryButton});
        printForm(resp, new String[] {"start date", "end date", "product IDs"}, new String[]{"Start Date (yyyy-mm-dd)", "End Date (yyyy-mm-dd)", "Product IDs (3,8,1)"}, new String[]{damagedByProductButton});
        printForm(resp, new String[] {"start date", "end date", "store IDs"}, new String[]{"Start Date (yyyy-mm-dd)", "End Date (yyyy-mm-dd)", "Store IDs (3,8,1)"}, new String[]{expiredByStoreButton});
        printForm(resp, new String[] {"start date", "end date", "category IDs"}, new String[]{"Start Date (yyyy-mm-dd)", "End Date (yyyy-mm-dd)", "Category IDs (3,8,1)"}, new String[]{expiredByCategoryButton});
        printForm(resp, new String[] {"start date", "end date", "product IDs"}, new String[]{"Start Date (yyyy-mm-dd)", "End Date (yyyy-mm-dd)", "Product IDs (3,8,1)"}, new String[]{expiredByProductButton});
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (handleHeader(req, resp))
            return;
        if(isButtonPressed(req, defectiveByStoreButton)){
            if (!isAllowed(req, resp, Report.ALLOWED)) {
                setError("You have no permission to view defective reports");
                refresh(req, resp);
                return;
            }
            try {
                String startDateStr = req.getParameter("start date");
                String endDateStr = req.getParameter("end date");
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);
                String storeIDsString = req.getParameter("IDs");
                List<Integer> storeIDs = (Arrays.asList(storeIDsString.split(","))).stream().map(Integer::parseInt).collect(Collectors.toList());
                Result<List<DefectiveItemReport>> defectiveItemsByStore = controller.getDefectiveItemsByStore(startDate, endDate, storeIDs);
                if(defectiveItemsByStore.isOk() && defectiveItemsByStore.getValue().size()>0)
                    redirect(resp, Presentation.WebPresentation.Screens.ViewModels.InventoryScreens.Report.class, new String[]{"defective or damaged or expired", "by store or by category or by product", "IDs", "start date", "end date"}, new String[]{"defective", "by store", storeIDsString, startDateStr, endDateStr});
                else if (defectiveItemsByStore.isOk()) {
                    setError("no reports");
                    refresh(req, resp);
                }
                else {
                    setError("Failed to get defective reports by store");
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
        if(isButtonPressed(req, defectiveByCategoryButton)){
            if (!isAllowed(req, resp, Report.ALLOWED)) {
                setError("You have no permission to view defective reports");
                refresh(req, resp);
                return;
            }
            try {
                String startDateStr = req.getParameter("start date");
                String endDateStr = req.getParameter("end date");
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);
                String categoryIDsString = req.getParameter("IDs");
                List<Integer> categoryIDs = (Arrays.asList(categoryIDsString.split(","))).stream().map(Integer::parseInt).collect(Collectors.toList());
                Result<List<DefectiveItemReport>> defectiveItemsByCategory = controller.getDefectiveItemsByCategory(startDate, endDate, categoryIDs);
                if(defectiveItemsByCategory.isOk() && defectiveItemsByCategory.getValue().size()>0)
                    redirect(resp, Presentation.WebPresentation.Screens.ViewModels.InventoryScreens.Report.class, new String[]{"defective or damaged or expired", "by store or by category or by product", "IDs", "start date", "end date"}, new String[]{"defective", "by category", categoryIDsString, startDateStr, endDateStr});
                else if (defectiveItemsByCategory.isOk()) {
                    setError("no reports");
                    refresh(req, resp);
                }
                else {
                    setError("Failed to get defective reports by category");
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
        if(isButtonPressed(req, defectiveByProductButton)){
            if (!isAllowed(req, resp, Report.ALLOWED)) {
                setError("You have no permission to view defective reports");
                refresh(req, resp);
                return;
            }
            try {
                String startDateStr = req.getParameter("start date");
                String endDateStr = req.getParameter("end date");
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);
                String productIDsString = req.getParameter("IDs");
                List<Integer> productIDs = (Arrays.asList(productIDsString.split(","))).stream().map(Integer::parseInt).collect(Collectors.toList());
                Result<List<DefectiveItemReport>> defectiveItemsByProduct = controller.getDefectiveItemsByProduct(startDate, endDate, productIDs);
                if(defectiveItemsByProduct.isOk() && defectiveItemsByProduct.getValue().size()>0)
                    redirect(resp, Presentation.WebPresentation.Screens.ViewModels.InventoryScreens.Report.class, new String[]{"defective or damaged or expired", "by store or by category or by product", "IDs", "start date", "end date"}, new String[]{"defective", "by product", productIDsString, startDateStr, endDateStr});
                else if (defectiveItemsByProduct.isOk()) {
                    setError("no reports");
                    refresh(req, resp);
                }
                else {
                    setError("Failed to get defective reports by product");
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
        if(isButtonPressed(req, damagedByStoreButton)){
            if (!isAllowed(req, resp, Report.ALLOWED)) {
                setError("You have no permission to view damaged reports");
                refresh(req, resp);
                return;
            }
            try {
                String startDateStr = req.getParameter("start date");
                String endDateStr = req.getParameter("end date");
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);
                String storeIDsString = req.getParameter("IDs");
                List<Integer> storeIDs = (Arrays.asList(storeIDsString.split(","))).stream().map(Integer::parseInt).collect(Collectors.toList());
                Result<List<DefectiveItemReport>> damagedItemsByStore = controller.getDamagedItemsByStore(startDate, endDate, storeIDs);
                if(damagedItemsByStore.isOk() && damagedItemsByStore.getValue().size()>0)
                    redirect(resp, Presentation.WebPresentation.Screens.ViewModels.InventoryScreens.Report.class, new String[]{"defective or damaged or expired", "by store or by category or by product", "IDs", "start date", "end date"}, new String[]{"damaged", "by store", storeIDsString, startDateStr, endDateStr});
                else if (damagedItemsByStore.isOk()) {
                    setError("no reports");
                    refresh(req, resp);
                }
                else {
                    setError("Failed to get damaged reports by store");
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
        if(isButtonPressed(req, damagedByCategoryButton)){
            if (!isAllowed(req, resp, Report.ALLOWED)) {
                setError("You have no permission to view damaged reports");
                refresh(req, resp);
                return;
            }
            try {
                String startDateStr = req.getParameter("start date");
                String endDateStr = req.getParameter("end date");
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);
                String categoryIDsString = req.getParameter("IDs");
                List<Integer> categoryIDs = (Arrays.asList(categoryIDsString.split(","))).stream().map(Integer::parseInt).collect(Collectors.toList());
                Result<List<DefectiveItemReport>> damagedItemsByCategory = controller.getDamagedItemsByCategory(startDate, endDate, categoryIDs);
                if(damagedItemsByCategory.isOk() && damagedItemsByCategory.getValue().size()>0)
                    redirect(resp, Presentation.WebPresentation.Screens.ViewModels.InventoryScreens.Report.class, new String[]{"defective or damaged or expired", "by store or by category or by product", "IDs", "start date", "end date"}, new String[]{"damaged", "by category", categoryIDsString, startDateStr, endDateStr});
                else if (damagedItemsByCategory.isOk()) {
                    setError("no reports");
                    refresh(req, resp);
                }
                else {
                    setError("Failed to get damaged reports by category");
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
        if(isButtonPressed(req, damagedByProductButton)){
            if (!isAllowed(req, resp, Report.ALLOWED)) {
                setError("You have no permission to view damaged reports");
                refresh(req, resp);
                return;
            }
            try {
                String startDateStr = req.getParameter("start date");
                String endDateStr = req.getParameter("end date");
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);
                String productIDsString = req.getParameter("IDs");
                List<Integer> productIDs = (Arrays.asList(productIDsString.split(","))).stream().map(Integer::parseInt).collect(Collectors.toList());
                Result<List<DefectiveItemReport>> damagedItemsByProduct = controller.getDamagedItemsByProduct(startDate, endDate, productIDs);
                if(damagedItemsByProduct.isOk() && damagedItemsByProduct.getValue().size()>0)
                    redirect(resp, Presentation.WebPresentation.Screens.ViewModels.InventoryScreens.Report.class, new String[]{"defective or damaged or expired", "by store or by category or by product", "IDs", "start date", "end date"}, new String[]{"damaged", "by product", productIDsString, startDateStr, endDateStr});
                else if (damagedItemsByProduct.isOk()) {
                    setError("no reports");
                    refresh(req, resp);
                }
                else {
                    setError("Failed to get damaged reports by product");
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
        if(isButtonPressed(req, expiredByStoreButton)){
            if (!isAllowed(req, resp, Report.ALLOWED)) {
                setError("You have no permission to view expired reports");
                refresh(req, resp);
                return;
            }
            try {
                String startDateStr = req.getParameter("start date");
                String endDateStr = req.getParameter("end date");
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);
                String storeIDsString = req.getParameter("IDs");
                List<Integer> storeIDs = (Arrays.asList(storeIDsString.split(","))).stream().map(Integer::parseInt).collect(Collectors.toList());
                Result<List<DefectiveItemReport>> expiredItemsByStore = controller.getExpiredItemsByStore(startDate, endDate, storeIDs);
                if(expiredItemsByStore.isOk() && expiredItemsByStore.getValue().size()>0)
                    redirect(resp, Presentation.WebPresentation.Screens.ViewModels.InventoryScreens.Report.class, new String[]{"defective or damaged or expired", "by store or by category or by product", "IDs", "start date", "end date"}, new String[]{"expired", "by store", storeIDsString, startDateStr, endDateStr});
                else if (expiredItemsByStore.isOk()) {
                    setError("no reports");
                    refresh(req, resp);
                }
                else {
                    setError("Failed to get expired reports by store");
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
        if(isButtonPressed(req, expiredByCategoryButton)){
            if (!isAllowed(req, resp, Report.ALLOWED)) {
                setError("You have no permission to view expired reports");
                refresh(req, resp);
                return;
            }
            try {
                String startDateStr = req.getParameter("start date");
                String endDateStr = req.getParameter("end date");
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);
                String categoryIDsString = req.getParameter("IDs");
                List<Integer> categoryIDs = (Arrays.asList(categoryIDsString.split(","))).stream().map(Integer::parseInt).collect(Collectors.toList());
                Result<List<DefectiveItemReport>> expiredItemsByCategory = controller.getExpiredItemsByCategory(startDate, endDate, categoryIDs);
                if(expiredItemsByCategory.isOk() && expiredItemsByCategory.getValue().size()>0)
                    redirect(resp, Presentation.WebPresentation.Screens.ViewModels.InventoryScreens.Report.class, new String[]{"defective or damaged or expired", "by store or by category or by product", "IDs", "start date", "end date"}, new String[]{"expired", "by category", categoryIDsString, startDateStr, endDateStr});
                else if (expiredItemsByCategory.isOk()) {
                    setError("no reports");
                    refresh(req, resp);
                }
                else {
                    setError("Failed to get expired reports by category");
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
        if(isButtonPressed(req, expiredByProductButton)){
            if (!isAllowed(req, resp, Report.ALLOWED)) {
                setError("You have no permission to view expired reports");
                refresh(req, resp);
                return;
            }
            try {
                String startDateStr = req.getParameter("start date");
                String endDateStr = req.getParameter("end date");
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);
                String productIDsString = req.getParameter("IDs");
                List<Integer> productIDs = (Arrays.asList(productIDsString.split(","))).stream().map(Integer::parseInt).collect(Collectors.toList());
                Result<List<DefectiveItemReport>> expiredItemsByProduct = controller.getExpiredItemsByProduct(startDate, endDate, productIDs);
                if(expiredItemsByProduct.isOk() && expiredItemsByProduct.getValue().size()>0)
                    redirect(resp, Presentation.WebPresentation.Screens.ViewModels.InventoryScreens.Report.class, new String[]{"defective or damaged or expired", "by store or by category or by product", "IDs", "start date", "end date"}, new String[]{"expired", "by product", productIDsString, startDateStr, endDateStr});
                else if (expiredItemsByProduct.isOk()) {
                    setError("no reports");
                    refresh(req, resp);
                }
                else {
                    setError("Failed to get expired reports by product");
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
    }

}