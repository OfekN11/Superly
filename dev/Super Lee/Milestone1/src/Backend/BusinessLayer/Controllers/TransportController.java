package Backend.BusinessLayer.Controllers;

import Backend.BusinessLayer.Objects.DestinationDocument;
import Backend.BusinessLayer.Objects.Transport;
import Backend.BusinessLayer.Objects.TransportDocument;
import Backend.BusinessLayer.Objects.TransportOrder;
import Backend.Globals.Enums.ShippingAreas;

import java.util.*;

public class TransportController {
    private List<Transport> waitingTransports;
    private List<Transport> inProgressTransports;
    private List<Transport> pastTransports;
    private Queue<TransportOrder> orderQueue;

    public TransportController() {
        waitingTransports =  new ArrayList<>();
        inProgressTransports =  new ArrayList<>();
        pastTransports =  new ArrayList<>();
        orderQueue = new PriorityQueue<>();
    }

    public boolean placeDriver(int transportSN, String driverName)
    {

        return false;
    }

    public boolean placeTruck(int transportSN, String driverName)
    {

        return false;
    }

    public void addTransportOrder(int srcID, int dstID, HashMap<String, Integer> productList) {
        TransportOrder transportOrder = null;//new TransportOrder(srcID, dstID, productList);
        orderQueue.add(transportOrder);
    }

    public List<Transport> getInProgressTransports() {
        return inProgressTransports;
    }

    public List<Transport> getWaitingTransports() {
        return waitingTransports;
    }

    public List<Transport> getPastTransports() {
        return pastTransports;
    }

    public Queue<TransportOrder> getTransportOrders() {
        return orderQueue;
    }

    public Queue<TransportOrder> getTransportOrders(ShippingAreas area) {
        Queue<TransportOrder> orders = new PriorityQueue<>();
        for(TransportOrder transportOrder: orderQueue)
        {
            if(transportOrder.isInThisArea(area))
            {
                orders.add(transportOrder);
            }
        }
        return orders;
    }
}
