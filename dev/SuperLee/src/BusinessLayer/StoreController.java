package BusinessLayer;

import java.util.ArrayList;
import java.util.List;

public class StoreController {
    private List<Integer> storeIds;
    private List<Integer> categoryIds;

    //make singleton??
    public StoreController() {
        storeIds = new ArrayList<>();
        categoryIds = new ArrayList<>();
    }

    public void init() {
        //initialize stuff
    }

    public List<DiscountFromSupplier> getDiscountFromSupplierHistory(int productID) {
        return null;
    }

    public List<SaleToCustomer> getSaleHistory(int productID) {
        return null;
    }

    public List<Product> getDamagedItems() {
        return null;
    }

    public List<Product> getItems() {
        return null;
    }

    public List<Product> getItemsFromCategory(int categoryID) {
        return null;
    }
}
