package Backend.BusinessLayer.Controllers;

import Backend.BusinessLayer.Objects.DestinationDocument;
import Backend.BusinessLayer.Objects.Transport;
import Backend.BusinessLayer.Objects.TransportDocument;
import Backend.BusinessLayer.Objects.TransportOrder;
import Backend.Globals.Enums.ShippingAreas;

import java.util.*;

public class TransportController {
    private HashMap<Integer, Transport> waitingTransports;
    private HashMap<Integer, Transport> inProgressTransports;
    private HashMap<Integer, Transport> pastTransports;
    private Queue<TransportOrder> orderQueue;

    public TransportController() {
        waitingTransports =  new HashMap<>();
        inProgressTransports =  new HashMap<>();
        pastTransports =  new HashMap<>();
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

    public HashMap<Integer, Transport> getInProgressTransports() {
        return inProgressTransports;
    }

    public HashMap<Integer, Transport> getWaitingTransports() {
        return waitingTransports;
    }

    public HashMap<Integer, Transport> getPastTransports() {
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

    public void startTransport(int transportSN) throws Exception {
        if(waitingTransports.containsKey(transportSN))//TODO: add redesign?
        {
            Transport transport = waitingTransports.get(transportSN);
            if(!transport.readyToGo())
            {
                throw new Exception("The transport can not start!");
            }
        }
        throw new Exception("The transport can not start!");
    }
}
