package Backend.BusinessLayer.Controllers;

import Backend.BusinessLayer.Objects.Transport;
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
        if(waitingTransports.containsKey(transportSN))
        {
            Transport transport = waitingTransports.get(transportSN);
            if(transport.truckPlaced())
            {
                //TODO: DriverController.getInstance().canPlaceDriver();
                return true;
            }
        }
        return false;
    }

    public void placeTruck(int transportSN, int truckLN) throws Exception {
        if(waitingTransports.containsKey(transportSN))
        {
            Transport transport = waitingTransports.get(transportSN);
            /*
            if(truckController.getInstance().canPlaceTruck())
            {
                transport.placeTruck(truckLN);
            }
            */
            throw new Exception("Can't place the truck");
        }
        throw new Exception("The transport doesn't exist!");
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
