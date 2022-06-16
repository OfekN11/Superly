package Presentation.WebPresentation.Screens.ViewModels.InventoryScreens;

import Domain.Service.util.Result;
import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Screen;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Categories extends Screen{

    private static final String greet = "Categories";
    private static final String viewButton = "View category";
    private static final String addButton = "Add category";
    private static final String removeButton = "Remove category";

    public static final Set<Class<? extends Employee>> ALLOWED = new HashSet<>(0);

    public Categories() {
        super(greet, ALLOWED);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        printForm(resp, new String[] {"ID"}, new String[]{"Category ID"}, new String[]{viewButton});
        printForm(resp, new String[] {"ID"}, new String[]{"Category ID"}, new String[]{removeButton});
        printForm(resp, new String[] {"category name", "parent category ID"}, new String[]{"Category name", "Parent category ID"}, new String[]{addButton});
        printCategories(resp);
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        if (isButtonPressed(req, removeButton)){
            try {
                int categoryID = Integer.parseInt(req.getParameter("ID"));
                if(controller.deleteCategory(categoryID).getValue()) {
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format("Removed category %d", categoryID)));

                    //setError(String.format("Removed supplier %d", supplierId));
                    refresh(req, resp);
                }
                else{
                    setError("Category ID " + categoryID + " doesn't exist!");
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
            try {
                String categoryName = req.getParameter("category name");
                int parentCategoryID = Integer.parseInt(req.getParameter("parent category ID"));

                if(controller.addNewCategory(categoryName, parentCategoryID).isOk()) {
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format("Added new category %d", categoryName)));

                    //setError(String.format("Removed supplier %d", supplierId));
                    refresh(req, resp);
                }
                else{
                    setError("Category wasn't added");
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
        else if(isButtonPressed(req, viewButton)){
            try {
                redirect(resp, Presentation.WebPresentation.Screens.ViewModels.InventoryScreens.Category.class);
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
    private void printCategories(HttpServletResponse resp) {
        try {
            Result<List<Domain.Service.Objects.InventoryObjects.Category>> categories = controller.getCategories();
            PrintWriter out = resp.getWriter();
            out.println("number of categories exist: " + categories.getValue().size());
            for (Domain.Service.Objects.InventoryObjects.Category c: categories.getValue()) {
                out.println(c.getName() + ": " + c.getID() + ", Parent category: " + c.getParentCategory());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}