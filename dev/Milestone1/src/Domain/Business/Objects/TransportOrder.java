package Domain.Business.Objects;

import Domain.Business.Objects.Site.Destination;
import Domain.Business.Objects.Site.Source;

import java.util.HashMap;

public class TransportOrder {
    private static int incID = 0;
    private int ID;
    private Source src;
    private Destination dst;
    private HashMap<Integer, Integer> productList;

    public TransportOrder(Source src, Destination dst) {
        ID = incID++;
        this.src = src;
        this.dst = dst;
        this.productList = new HashMap<>();
    }

    public TransportOrder(Source src, Destination dst, HashMap<Integer, Integer> productList) {
        ID = incID++;
        this.src = src;
        this.dst = dst;
        this.productList = productList;
    }

    public int getID() {
        return ID;
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

    public HashMap<Integer, Integer> getProductList() {
        return productList;
    }

    public void setProductList(HashMap<Integer, Integer> productList) {
        this.productList = productList;
    }

    private HashMap<Integer, Integer> splitProductList()
    {
        HashMap<Integer, Integer> newProductList = new HashMap<>();
        for (Integer item: productList.keySet()) {
            if (productList.size() / 2 > newProductList.size())
            {
                newProductList.put(item, productList.get(item));
            }
        }
        productList.remove(newProductList);
        return newProductList;
    }


}
