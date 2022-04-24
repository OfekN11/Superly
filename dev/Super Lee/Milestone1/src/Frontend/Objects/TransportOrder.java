package Frontend.Objects;

import Backend.BusinessLayer.Objects.Destination;
import Backend.BusinessLayer.Objects.Source;

import java.util.HashMap;

public class TransportOrder {
    private int srcID;
    private int dstID;
    private HashMap<String, Integer> productList;

    public TransportOrder(int srcID, int dstID) {
        this.srcID = srcID;
        this.dstID = dstID;
        productList = new HashMap<>();
    }

    public boolean isValidOrder()
    {
        return productList.size() > 0;
    }
    public void addProduct(String productName, int countOfProduct) {
        if(!productList.containsKey(productName))
        {
            productList.put(productName, countOfProduct);
        }
        else {
            System.out.println("The product is already on the list!");
        }
    }

    public void removeProduct(String productName) {
        if(productList.containsKey(productName))
        {
            productList.remove(productName);
        }
        else {
            System.out.println("The product does not exist!");
        }
    }

    public void updateProduct(String productName, int countOfProduct) {
        if(productList.containsKey(productName))
        {
            productList.replace(productName, countOfProduct);
        }
        else {
            System.out.println("The product does not exist!");
        }
    }

    public int getSrcID() {
        return srcID;
    }

    public void setSrcID(int srcID) {
        this.srcID = srcID;
    }

    public int getDstID() {
        return dstID;
    }

    public void setDstID(int dstID) {
        this.dstID = dstID;
    }

    public HashMap<String, Integer> getProductList() {
        return productList;
    }

    public void setProductList(HashMap<String, Integer> productList) {
        this.productList = productList;
    }
}
