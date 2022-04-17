package BusinessLayer;

import java.util.Date;
import java.util.Dictionary;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private Dictionary<Location, Integer> minAmount; //min amount which indicates for lack of the specific product.
    private Dictionary<Location, Integer> maxAmount; //max amount available to store in the warehouse for the specific product at once.
    private Dictionary<Location, Integer> inStock; //current amount in stock (store and warehouse seperately)
    private int weight;
    private List<Supplier> suppliers;
    private double price;
    private List<Discount> discountHistory;
    private List<Sale> saleHistory;

    private void RemoveItems(int storeId, int amount) { //brought or thrown
    }
    private void MoveItem(int storeId, int amount) { //from warehouse to store
    }
    private void AddItems(int storeId, int amount) { //from supplier to warehouse
    }
    private void ReturnedToStore(int storeId, int amount) { //from customer to store
    }
    private void changeLocation(Location from, Location to, int amount) {

    }
}