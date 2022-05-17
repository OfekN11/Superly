package Domain.Business.Controllers;

import Domain.Business.Objects.Product;
import Domain.Business.Objects.Site.*;
import Domain.Business.Objects.TransportOrder;

import java.util.HashMap;

public class OrderController {
    private HashMap<Integer, Product> products;

    public OrderController() {
        products = new HashMap<>();
    }
    public void createProducts(){
        //TODO not implemented yet, creates the products list
    }
    public void addTransportOrder(int srcID, int dstID, HashMap<Integer, Integer> productList) throws Exception {
        //TODO not implemented yet
    }
    public TransportOrder getTransportOrder(int orderID) throws Exception {
        //TODO not implemented yet
        return null;
    }

    public void deleteOrder(int orderID){
        //TODO not implemented yet
    }
    public int getExtraWeight(TransportOrder order) {
        int weight = 0;
        for (Integer productID : order.getProductList().keySet()) {
            weight = weight + (int) (products.get(productID).getWeight() * order.getProductList().get(productID));
        }
        return weight;
    }

}
