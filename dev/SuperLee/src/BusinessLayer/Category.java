package BusinessLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Category {
    private int id;
    private String name;
    private Set<Category> subcategories;
    private Category parentCategory;
    private List<Product> products;
    private List<SaleToCustomer> sales;
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Category> getSubcategories() {
        return subcategories;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void changeParentCategory(Category newParentCategory) {
        if (parentCategory!=null && parentCategory!=newParentCategory) {
            parentCategory.removeSubcategory(this);
        }
        else {
            parentCategory = newParentCategory;
            parentCategory.addSubcategory(this);
        }
    }

    public Category(int id, String name, Set<Category> subcategories, List<Product> products, Category parentCategory) {
        this.id = id;
        this.name = name;
        this.subcategories = subcategories;
        for (Category c : subcategories)
            c.changeParentCategory(this);
        this.products = products;
        this.parentCategory = parentCategory;
        if (parentCategory!=null)
            parentCategory.addSubcategory(this);
        sales = new ArrayList<>();
    }

    public List<Product> getProducts() {
        List<Product> output = products;
        for (Category c : subcategories) {
            output.addAll(c.getProducts());
        }
        return output;
    }
    public boolean removeSubcategory(Category category) {
        return subcategories.remove(category);
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

    public String getParentCategoryName() {
        if (parentCategory==null)
            return "";
        else return parentCategory.getName();
    }
}
