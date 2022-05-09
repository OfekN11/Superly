package Domain.Business.Objects;

import Backend.BusinessLayer.Objects.Document.DestinationDocument;
import Domain.Business.Objects.Source;

import javax.print.attribute.standard.Destination;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransportOrder {
    private static int incID = 0;
    private int ID;
    private Source src;
    private Destination dst;
    private HashMap<String, Integer> productList;

    public TransportOrder(Source src, Destination dst) {
        ID = incID++;
        this.src = src;
        this.dst = dst;
        this.productList = new HashMap<>();
    }

    public TransportOrder(Source src, Destination dst, HashMap<String, Integer> productList) {
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

    public HashMap<String, Integer> getProductList() {
        return productList;
    }

    public void setProductList(HashMap<String, Integer> productList) {
        this.productList = productList;
    }

    private HashMap<String, Integer> splitProductList()
    {
        HashMap<String, Integer> newProductList = new HashMap<>();
        for (String item: productList.keySet()) {
            if (productList.size() / 2 > newProductList.size())
            {
                newProductList.put(item, productList.get(item));
            }
        }
        productList.remove(newProductList);
        return newProductList;
    }

    public TransportOrder splitOrder()
    {
        if(productList.size() == 0)
        {
            return null;
        }
        else {
            TransportOrder newOrder = new TransportOrder(src, dst);
            newOrder.productList = splitProductList();
            return newOrder;
        }
    }

    private List<String> getProductNameList()
    {
        List<String> pl = new ArrayList<>();
        for(String product: productList.keySet())
        {
            pl.add(product);
        }
        return pl;
    }

    public DestinationDocument toDocument()
    {
        return new DestinationDocument(dst.getId(), getProductNameList());
    }
}
