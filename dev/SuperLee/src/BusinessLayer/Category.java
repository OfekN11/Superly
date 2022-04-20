package BusinessLayer;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private int id;
    private String name;
    private List<Category> subcategories;
    private Category parentCategory;
    private List<Product> products;
    private List<SaleToCustomer> sales;


    public Category(int id, String name, List<Category> subcategories, List<Product> products, Category parentCategory) {
        this.id = id;
        this.name = name;
        this.subcategories = subcategories;
        this.products = products;
        this.parentCategory = parentCategory;
        sales = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }
    public boolean removeSubcategory(int categoryID) {
        Category c = findCategory(categoryID);
        return subcategories.remove(c);
    }

    public boolean addSubcategory(Category c) {
        return subcategories.add(c);
    }

    public boolean removeProduct(int productID) {
        Product p = findProduct(productID);
        return products.remove(p);
    }

    public boolean addSale(SaleToCustomer s) {
        return sales.add(s);
    }
    public boolean addProduct(Product p) {
        return products.add(p);
    }

    public Product findProduct(int productID) {
        Product product = null;
        for (Product p: products) {
            if (p.getId()==productID) {
                product = p;
                break;
            }
        }
        return product;
    }

    public Category findCategory(int categoryID) {
        Category category = null;
        for (Category c: subcategories) {
            if (c.id==categoryID) {
                category = c;
                break;
            }
        }
        return category;
    }
    public SaleToCustomer findCurrentBestSale(SaleToCustomer currentSale) {
        for (SaleToCustomer sale: sales)
            if ((sale.isActive() && currentSale==null) || (sale.isActive() && currentSale.getPercent()<sale.getPercent()))
                currentSale = sale;
        if (parentCategory!=null)
            currentSale = parentCategory.findCurrentBestSale(currentSale);
        return currentSale;
    }
}
