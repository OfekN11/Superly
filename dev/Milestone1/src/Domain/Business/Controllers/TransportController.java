package Domain.Business.Controllers;

import Domain.Business.Objects.*;
import Domain.Business.Objects.Site.Destination;
import Domain.Business.Objects.Site.Source;
import Domain.Business.Objects.Employee.Carrier;
import Globals.Enums.ShiftTypes;
import Globals.Enums.ShippingAreas;
import Globals.Enums.TransportStatus;
import Globals.Pair;

import java.time.LocalDate;
import java.util.*;

public class TransportController {
    private HashMap<Integer, Transport> pendingTransports;
    private HashMap<Integer, Transport> inProgressTransports;
    private HashMap<Integer, Transport> redesignTransports;
    private HashMap<Integer, Transport> completedTransports;
    private TruckController truckController;
    private OrderController orderController;
    private EmployeeController employeeController;
    private DocumentController documentController;
    private SiteController siteController;
    private ShiftController shiftController;

    public TransportController() {
        pendingTransports =  new HashMap<>();
        inProgressTransports =  new HashMap<>();
        redesignTransports =  new HashMap<>();
        completedTransports =  new HashMap<>();
        truckController = new TruckController();
        employeeController = new EmployeeController();
        orderController = new OrderController();
        siteController = new SiteController();
        shiftController = new ShiftController();
    }
    //TODO need to be implemented in DAL objects
    public void createTransport(Pair<LocalDate,ShiftTypes> shift) throws Exception {
        if(shiftController.getShift(shift.getLeft(),shift.getRight()).getSorterCount()>0){
            //TODO create new transport and save in DataBase
        }
        else{
            throw new Exception("there is no sorter in this shift");
        }

    }
    //TODO implement with DAL objects
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
    public void addOrderToTransport(int transportSN, int orderID) throws Exception {
        Transport transport = getTransport(transportSN);
        if(transport.getStatus()== TransportStatus.padding)
        {
            if(transport.isPlacedTruck()){
                TransportOrder order = orderController.getTransportOrder(orderID);
                int extraWeight  = orderController.getExtraWeight(order);
                updateWeight(transport,extraWeight);
                ShippingAreas sourceShip = siteController.getSource(order.getSrc()).getAddress().getShippingAreas();
                ShippingAreas destShip = siteController.getDestination(order.getDst()).getAddress().getShippingAreas();
                transport.addOrder(order,sourceShip,destShip);
                orderController.deleteOrder(orderID);
            }
            else{
                throw new Exception("the truck is not placed yet");
            }
        }
        else {
            throw new Exception("The transport is not on the list of pending transport!");
        }
    }
    public void updateWeight(Transport transport,int newWeight) throws Exception {
            Truck truck = truckController.getTruck(transport.getTruckNumber());
            if(!transport.updateWeight(newWeight, truck.getMaxCapacityWeight()))
            {
                throw new Exception("Weight Warning!");
            }
        }
    public void placeTruck(int transportSN, int licenseNumber) throws Exception {
        Transport transport = getTransport(transportSN);
        if(transport.getStatus()==TransportStatus.padding){
            Truck truck = truckController.getTruck(licenseNumber);
            if(truck.isFree(transport.getShift())&&transport.placeTruck(licenseNumber))
            {
                truck.addShift(transport.getShift());
            }
            else{
                throw new Exception("truck cant be placed");
            }
        }
        else{
            throw new Exception("the transport is not in padding list");
        }
    }
    //TODO need to combine with shift
    public void placeDriver(int transportSN, String empID) throws Exception {
        Transport transport = getTransport(transportSN);
        if(transport.isPlacedTruck()){
            Carrier carrier = employeeController.getCarrier(empID);// = driverController.getDriver(driverName);
            Truck truck = truckController.getTruck(transport.getTruckNumber());
            if(truck.canDriveOn(carrier.getLicenses()))
            {
                if(!transport.isPlacedCarrier()) {
                    //TODO check if the carrier is free in the shift
                    transport.placeDriver(empID, carrier.getName());
                }
                else{
                    throw new Exception("carrier is already placed");
                }
            }
            else {
                throw new Exception("The carrier can't drive on this truck!");
            }
        }
        else{
            throw new Exception("carrier cannot be placed before the truck");
        }

    }

    public void startTransport(int transportSN) throws Exception {
        Transport transport = getTransport(transportSN);
        if(transport.getStatus()==TransportStatus.padding){
            if(transport.readyToGo()){
                transport.startTransport();
            }
            else{
                throw new Exception("this is not a padding transport");
            }
        }
        else{
            throw new Exception("this is not a padding transport");
        }
    }

    public void endTransport(int transportSN) throws Exception {
        Transport transport = getTransport(transportSN);
        if(transport.getStatus()==TransportStatus.inProgress){
            if(transport.isDoneTransport()){//TODO implement isDoneTransport() in transport class
                transport.endTransport();
            }
            else{
                throw new Exception("this transport is not done yet");
            }
        }
        else{
            throw new Exception("this is not a inProgress transport");
        }
    }
    //TODO see if the function is needed
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
    //TODO the function will check the valid of the function.
    //TODO the function will remove from the transport the site that visited site
    //TODO the in case of destination visit the function will create new destination document
    public void advanceSite(int transportSN,int siteID){

    }
    //GETTERS
    //TODO fix the getters after DAL fix
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


    public List<TransportOrder> getTransportOrders() {
        List<TransportOrder> orderList = new ArrayList<>();
        for(int orderID: orders.keySet())
        {
            orderList.add(orders.get(orderID));
        }
        return orderList;
    }
}
