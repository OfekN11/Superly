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
    private HashMap<Integer, TransportDocument> transportDocuments;
    private HashMap<Integer, DestinationDocument> destinationDocuments;
    private Queue<TransportOrder> orderQueue;

    public TransportController() {
        waitingTransports =  new ArrayList<>();
        inProgressTransports =  new ArrayList<>();
        pastTransports =  new ArrayList<>();
        transportDocuments = new HashMap<>();
        orderQueue = new PriorityQueue<>();
    }

    public TransportDocument getTransportDocuments(int transportDocumentSN)
    {
        if(transportDocuments.containsKey(transportDocumentSN))
        {
            return transportDocuments.get(transportDocumentSN);
        }
        return null;
    }
    public DestinationDocument getDestinationDocuments(int destinationDocumentSN)
    {
        if(destinationDocuments.containsKey(destinationDocumentSN))
        {
            return destinationDocuments.get(destinationDocumentSN);
        }
        return null;
    }


    public boolean placeDriver(int transportSN, String driverName)
    {
        if(transportDocuments.containsKey(transportSN))
        {
            //Test if It's available to place the driver
            //TODO:Decide how to implication
            return true;
        }
        return false;
    }

    public boolean placeTruck(int transportSN, String driverName)
    {
        if(transportDocuments.containsKey(transportSN))
        {
            //Test if It's available to place the driver
            //TODO:Decide how to implication
            return true;
        }
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
