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

    public boolean inCategory(List<Integer> categories) { //if this category or its subcategories have one of the given IDs.
        if (categories.contains(id))
            return true;
        else if (subcategories.size()!=0)
            for (Category c: subcategories) {
                if (c.inCategory(categories))
                    return true;
            }
        return false;
    }
}
