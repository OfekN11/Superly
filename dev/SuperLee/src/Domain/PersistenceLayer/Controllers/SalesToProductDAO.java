package Domain.PersistenceLayer.Controllers;

import Domain.PersistenceLayer.Abstract.DAO;
import Domain.ServiceLayer.Result;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SalesToProductDAO extends DAO {
    private final static int SALE_COLUMN = 1;
    private final static int PRODUCT_COLUMN = 2;
    public SalesToProductDAO() {
        super("SalesToProduct");
    }
    public List<Integer> getProductsOfSale(int sale) {
        try {
            ResultSet rs = select(getConnection(), Arrays.asList(PRODUCT_COLUMN), Arrays.asList(SALE_COLUMN), Arrays.asList(sale));
            List<Integer> products = new ArrayList<>();
            while (rs.next()) {
                products.add(rs.getInt(1));
            }
            return products;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Integer> getSales(int product) {
        try {
            ResultSet rs = select(getConnection(), Arrays.asList(SALE_COLUMN), Arrays.asList(PRODUCT_COLUMN), Arrays.asList(product));
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
