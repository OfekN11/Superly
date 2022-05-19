package Domain.Business.Controllers;

import Domain.Business.Objects.Product;
import Domain.Business.Objects.Site.*;
import Domain.Business.Objects.TransportOrder;

import java.util.HashMap;
//TODO not finished methods (ADD and GET)
public class OrderController {
    private HashMap<Integer, Product> products;

    public OrderController() {
        products = new HashMap<>();
        products.put( 1,new Product(1,"Milk",2));
        products.put( 2,new Product(2,"Bread",1));
        products.put( 3,new Product(3,"Eggs",4));
        products.put( 4,new Product(4,"Chocolate",10));
        products.put( 5,new Product(5,"cheese",5));
        products.put( 6,new Product(6,"sugar",7));
        products.put( 6,new Product(7,"salt",9));

    }
    public void addTransportOrder(int srcID, int dstID, HashMap<Integer, Integer> productList) throws Exception {
        //TODO not implemented yet
    }
    public TransportOrder getTransportOrder(int orderID) throws Exception {
        //TODO not implemented yet
        return null;
    }

    public int getExtraWeight(TransportOrder order) {
        int weight = 0;
        for (String productID : order.getProductList().keySet()) {
            weight = weight + (int) (products.get(productID).getWeight() * order.getProductList().get(productID));
        }
        return weight;
    }

}
