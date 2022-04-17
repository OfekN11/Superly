package BusinessLayer;

import java.util.List;

public class Category {
    private int id;
    private String name;
    private List<Category> subcategories;
    private List<Product> products;


    public Category(int id, String name, List<Category> subcategories, List<Product> products) {
        this.id = id;
        this.name = name;
        this.subcategories = subcategories;
        this.products = products;
    }

    public boolean removeSubcategory(int subCategoryID) {
        return false;
    }

    public boolean addSubcategory(int subCategoryID) {
        return false;
    }

    public boolean removeItem(int itemID) {
        return false;
    }

    public boolean addItem(int itemID) {
        return false;
    }
}
