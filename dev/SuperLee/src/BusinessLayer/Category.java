package BusinessLayer;

import BusinessLayer.DiscountsAndSales.SaleToCustomer;

import java.util.*;

public class Category {
    private String name;
    private Set<Category> subcategories;
    private Category parentCategory;
    private List<Product> products;
    private List<SaleToCustomer> sales;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
            if (parentCategory!=null)
                parentCategory.addSubcategory(this);
        }
    }

    public Category(String name, Set<Category> subcategories, List<Product> products, Category parentCategory) {
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

//    public Category findCategory(int categoryID) {
//        Category category = null;
//        for (Category c: subcategories) {
//            if (c.id==categoryID) {
//                category = c;
//                break;
//            }
//        }
//        return category;
//    }
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

    public List<SaleToCustomer> getSaleHistory() {
        List<SaleToCustomer> result;
        if (parentCategory==null)
             result = new ArrayList<>();
        else
            result = parentCategory.getSaleHistory();
        for (SaleToCustomer sale : sales) {
            if (sale.isPassed())
                result.add(sale);
        }
        return result;
    }

    public List<SaleToCustomer> getSalesOnDate(Date date) {
        List<SaleToCustomer> result;
        if (parentCategory==null)
            result = new ArrayList<>();
        else
            result = parentCategory.getSalesOnDate(date);
        for (SaleToCustomer sale : sales) {
            if (sale.wasActive(date))
                result.add(sale);
        }
        return result;
    }

    public Collection<DefectiveItems> getExpiredItemReports(Date start, Date end) {
        List<DefectiveItems> eirList = new ArrayList<>();
        for (Product p : products) {
            eirList.addAll(p.getExpiredItemReports(start, end));
        }
        for (Category c: subcategories) {
            eirList.addAll(c.getExpiredItemReports(start, end));
        }
        return eirList;
    }

    public Collection<DefectiveItems> getDamagedItemReports(Date start, Date end) {
        List<DefectiveItems> dirList = new ArrayList<>();
        for (Product p : products) {
            dirList.addAll(p.getDamagedItemReports(start, end));
        }
        for (Category c: subcategories) {
            dirList.addAll(c.getDamagedItemReports(start, end));
        }
        return dirList;
    }
}
