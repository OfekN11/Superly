package ServiceLayer.Objects;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private final String name;
    private final String parentCategory;
    private final List<Category> subCategories;
    private final int numOfProducts;
    public Category(BusinessLayer.Category c) {
        this.name = c.getName();
        this.parentCategory = c.getParentCategoryName();
        List<Category> subCats = new ArrayList<>();
        for (BusinessLayer.Category ca : c.getSubcategories())
            subCats.add(new Category(ca));
        this.subCategories = subCats;
        this.numOfProducts=c.getProducts().size();
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", numOfProducts=" + numOfProducts +
                ", parentCategory='" + parentCategory + '\'' +
                ", subCategories=" + subCategories +
                '}';
    }
}
