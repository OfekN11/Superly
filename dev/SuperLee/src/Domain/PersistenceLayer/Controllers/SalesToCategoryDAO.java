package Domain.PersistenceLayer.Controllers;

import Domain.PersistenceLayer.Abstract.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SalesToCategoryDAO extends DAO {
    private final static int SALE_COLUMN = 1;
    private final static int CATEGORY_COLUMN = 2;
    public SalesToCategoryDAO() {
        super("SalesToCategory");
    }
    public List<Integer> getCategoriesOfSale(int sale) {
        try {
            ResultSet rs = select(getConnection(), Arrays.asList(CATEGORY_COLUMN), Arrays.asList(SALE_COLUMN), Arrays.asList(sale));
            List<Integer> categories = new ArrayList<>();
            while (rs.next()) {
                categories.add(rs.getInt(1));
            }
            return categories;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Integer> getSales(int category) {
        try {
            ResultSet rs = select(getConnection(), Arrays.asList(SALE_COLUMN), Arrays.asList(CATEGORY_COLUMN), Arrays.asList(category));
            List<Integer> sales = new ArrayList<>();
            while (rs.next()) {
                sales.add(rs.getInt(1));
            }
            return sales;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
