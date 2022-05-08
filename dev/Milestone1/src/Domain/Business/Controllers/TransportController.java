package Backend.BusinessLayer.Controllers;

import Backend.BusinessLayer.Objects.*;
import Globals.Enums.ShippingAreas;

import java.util.*;

public class TransportController {
    private static TransportController instance = null;
    private HashMap<Integer, Transport> pendingTransports;
    private HashMap<Integer, Transport> inProgressTransports;
    private HashMap<Integer, Transport> redesignTransports;
    private HashMap<Integer, Transport> completedTransports;
    private HashMap<Integer, TransportOrder> orders;
    private TruckController truckController;
    private DriverController driverController;
    private DocumentController documentController;
    private SiteController siteController;

    private TransportController() {
        pendingTransports =  new HashMap<>();
        inProgressTransports =  new HashMap<>();
        redesignTransports =  new HashMap<>();
        completedTransports =  new HashMap<>();
        orders = new HashMap<>();
        truckController = TruckController.getInstance();
        driverController = DriverController.getInstance();
    }

    public static TransportController getInstance(){
        if (instance == null) {
            instance = new TransportController();
        }
        return instance;
    }

    public Transport getTransport(int transportSN) throws Exception {
        if (pendingTransports.containsKey(transportSN))
        {
            return pendingTransports.get(transportSN);
        }
        else if (inProgressTransports.containsKey(transportSN))
        {
            return inProgressTransports.get(transportSN);
        }
        else if (redesignTransports.containsKey(transportSN))
        {
            return redesignTransports.get(transportSN);
        }
        else if (completedTransports.containsKey(transportSN))
        {
            return completedTransports.get(transportSN);
        }
        else {
            throw new Exception("The transport doesn't exist!");
        }
    }

    public void addTransportOrder(int srcID, int dstID, HashMap<String, Integer> productList) throws Exception {
        Source src = siteController.getSource(srcID);
        Destination dst = siteController.getDestination(dstID);
        TransportOrder transportOrder = new TransportOrder(src, dst, productList);
        orders.put(transportOrder.getID(), transportOrder);
    }

    public void placeTruck(int transportSN, int licenseNumber) throws Exception {
        if(pendingTransports.containsKey(transportSN))
        {
            Transport transport = pendingTransports.get(transportSN);
            if(!transport.placeTruck(licenseNumber))
            {
                throw new Exception("Can't place this truck to the transport!");
            }
        }
        else {
            throw new Exception("The transport is not on the list of pending transport!");
        }
    }

    public void placeDrive(int transportSN, String driverName) throws Exception {
        if(pendingTransports.containsKey(transportSN))
        {
            Transport transport = pendingTransports.get(transportSN);
            Truck truck = truckController.getTruck(transport.getTruckNumber());
            //throw new Exception("First, a truck must be place for transport!");
            Driver driver = driverController.getDriver(driverName);
            if(truck.canDriveOn(driver.getLicenseTypes()))
            {
                transport.placeDriver(driverName);
            }
            else {
                throw new Exception("The driver can't drive on this truck!");
            }
        }
        else {
            throw new Exception("The transport is not on the list of pending transport!");
        }
    }

    public void startTransport(int transportSN) throws Exception {
        if(pendingTransports.containsKey(transportSN))
        {
            Transport transport = pendingTransports.get(transportSN);
            if(transport.readyToGo())
            {
                inProgressTransports.put(transportSN, transport);
                pendingTransports.remove(transportSN);
            }
            else {
                throw new Exception("Transport not ready to go!");
            }
        }
        else {
            throw new Exception("The transport is not on the list of pending transport!");
        }
    }

    public void endTransport(int transportSN) throws Exception {
        if(inProgressTransports.containsKey(transportSN))
        {
            Transport transport = inProgressTransports.get(transportSN);
            if(true)//transport.end())
            {
                completedTransports.put(transportSN, transport);
                inProgressTransports.remove(transportSN);
                transport.toDocument();
            }
            else {
                throw new Exception("Transport not ready to go!");
            }
        }
        else {
            throw new Exception("The transport is not on the list of in progress transport!");
        }
    }

    public void redesignTransport(int transportSN) throws Exception {
        if(inProgressTransports.containsKey(transportSN))
        {
            Transport transport = redesignTransports.get(transportSN);
            if(true)
            {
                //TODO: 1. Place another truck with bigger capacity by the transport wight
            }
            else if(true)
            {
                //TODO: 2. Split the transport orders
            }
            else if (false){
                //TODO: 3. Transport order number is 1 -> split transport order product list
            }
        }
        else {
            throw new Exception("The transport is not on the list of redesign transport!");
        }
    }

    public HashMap<Integer, Transport> getPendingTransports() {
        return pendingTransports;
    }

    public HashMap<Integer, Transport> getInProgressTransports() {
        return inProgressTransports;
    }

    public HashMap<Integer, Transport> getRedesignTransports() {
        return redesignTransports;
    }

    public HashMap<Integer, Transport> getCompletedTransports() {
        return completedTransports;
    }

    private TransportOrder getTransportOrder(int orderID) throws Exception {
        if(orders.containsKey(orderID))
        {
            return orders.get(orderID);
        }
        else {
            throw new Exception("The transport order doesn't exist!");
        }
    }
    public void addOrderToTransport(int transportSN, int orderID) throws Exception {
        if(pendingTransports.containsKey(transportSN))
        {
            Transport transport = pendingTransports.get(transportSN);
            TransportOrder order = getTransportOrder(orderID);
            transport.addOrder(order);
            orders.remove(orderID);
        }
        else {
            throw new Exception("The transport is not on the list of pending transport!");
        }
    }

    private List<TransportOrder> getTransportOrderInSameArea(List<ShippingAreas> as) throws Exception {
        List<TransportOrder> orderList = new ArrayList<>();
        for (Integer orderID: orders.keySet())
        {
            //TODO: Update
            if(as.contains(null))
            {
                orderList.add(orders.get(orderID));
            }
        }
        return orderList;
    }

    public List<TransportOrder> getTransportOrderInSameArea(int transportSN) throws Exception {
        if(pendingTransports.containsKey(transportSN))
        {
            Transport transport = pendingTransports.get(transportSN);
            //TODO: return getTransportOrderInSameArea(transport.getSA());
            return null;
        }
        else {
            throw new Exception("The transport is not on the list of pending transport!");
        }
    }

    public void updateWeight(int transportSN,int newWeight) throws Exception {
        if(inProgressTransports.containsKey(transportSN))
        {
            Transport transport = inProgressTransports.get(transportSN);
            Truck truck = truckController.getTruck(transport.getTruckNumber());
            if(!transport.updateWeight(newWeight, truck.getMaxCapacityWeight()))
            {
                inProgressTransports.remove(transportSN);
                redesignTransports.put(transportSN, transport);
                throw new Exception("Weight Warning!");
            }
        }
        else{
            throw new Exception("The transport can not start!");
        }

    }
    public List<TransportOrder> getTransportOrders() {
        List<TransportOrder> orderList = new ArrayList<>();
        for(int orderID: orders.keySet())
        {
            orderList.add(orders.get(orderID));
        }
        return orderList;
    }
}
