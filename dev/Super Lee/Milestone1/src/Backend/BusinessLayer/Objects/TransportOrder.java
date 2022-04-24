package Backend.BusinessLayer.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransportOrder {
    //private boolean status;
    private Source src;
    private Destination dst;
    private HashMap<String, Integer> productList;

    public TransportOrder(Source src, Destination dst) {
        this.src = src;
        this.dst = dst;
        productList = new HashMap<>();
    }

    public void addProduct(String productName, int countOfProduct) throws Exception {
        if(!productList.containsKey(productName))
        {
            productList.put(productName, countOfProduct);
        }
        throw new Exception("The product is already on the list!");

    }

    public void removeProduct(String productName) throws Exception {
        if(productList.containsKey(productName))
        {
            productList.remove(productName);
        }
        throw new Exception("The product does not exist!");

    }

    public void updateProduct(String productName, int countOfProduct) throws Exception {
        if(productList.containsKey(productName))
        {
            productList.replace(productName, countOfProduct);
        }
        throw new Exception("The product does not exist!");

    }

    public Source getSrc() {
        return src;
    }

    public void setSrc(Source src) {
        this.src = src;
    }

    public Destination getDst() {
        return dst;
    }

    public void setDst(Destination dst) {
        this.dst = dst;
    }

    public List<String> getProductsNameList() {
        return productList.keySet().stream().toList();
    }

    public DestinationDocument toDocument()
    {
        return new DestinationDocument(dst.getId(), getProductsNameList());
    }

}
