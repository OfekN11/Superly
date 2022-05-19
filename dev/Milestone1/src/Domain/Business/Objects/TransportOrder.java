package Domain.Business.Objects;

import Domain.Business.Objects.Site.Destination;
import Domain.Business.Objects.Site.Source;
import Globals.Enums.OrderStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransportOrder {
    private static int incID = 0;
    private int ID;
    private int srcID;
    private int dstID;
    private HashMap<String, Integer> productList;
    private int transportID;
    private OrderStatus status;

    public TransportOrder(int src, int dst) {
        ID = incID++;
        this.srcID = src;
        this.dstID = dst;
        transportID = -1;
        this.productList = new HashMap<>();
        status = OrderStatus.waiting;
    }

    public TransportOrder(int src, int dst, HashMap<String, Integer> productList) {
        ID = incID++;
        this.srcID = src;
        this.dstID = dst;
        this.productList = productList;
    }

    public TransportOrder(int ID, int srcID, int dstID, int transportID) {
        this.ID = ID;
        this.srcID = srcID;
        this.dstID = dstID;
        this.transportID = transportID;
    }

    public int getID() {
        return ID;
    }

    public int getSrc() {
        return srcID;
    }

    public void setSrc(int src) {
        this.srcID = src;
    }

    public int getDst() {
        return dstID;
    }

    public void setDst(int dst) {
        this.dstID = dst;
    }

    public HashMap<String, Integer> getProductList() {
        return productList;
    }
    public List<String> getProducts(){
        List<String> pro = new ArrayList<>();
        for (String s:productList.keySet()) {
            pro.add(s);
        }
        return pro;
    }

    public void setProductList(HashMap<String, Integer> productList) {
        this.productList = productList;
    }

    public int getTransportID() {
        return transportID;
    }

    public void setTransportID(int transportID) {
        this.transportID = transportID;
    }
    public OrderStatus getStatus(){return status;}
    public void order(){ status = OrderStatus.ordered;}


}
