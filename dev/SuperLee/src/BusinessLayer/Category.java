package BusinessLayer;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private int id;
    private String name;
    private List<Category> subcategories;
    private List<Product> products;
    private Category parentCategory;
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Category> getSubcategories() {
        return subcategories;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public Category(int id, String name, List<Category> subcategories, List<Product> products) {
        this.id = id;
        this.name = name;
        this.subcategories = subcategories;
        this.products = products;
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

    public List<Integer> findProductsIDs(List<Integer> productsIDs) {
        for (Product p: products)
            productsIDs.add(p.getId());
        for (Category c: subcategories)
            productsIDs.addAll(c.findProductsIDs(productsIDs));
        return productsIDs;
    }

    public boolean contains(Category category) {
        if (category.id == id)
            return true;
        for (Category c: subcategories)
            if (c.contains(category))
                return true;
        return false;
    }

    public String getParentCategoryName() {
        if (parentCategory==null)
            return "";
        else return parentCategory.getName();
    }
}
