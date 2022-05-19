package Domain.Business.Controllers;

import Domain.Business.Objects.*;
import Domain.Business.Objects.Document.DestinationDocument;
import Domain.Business.Objects.Document.TransportDocument;
import Domain.Business.Objects.Site.Destination;
import Domain.Business.Objects.Site.Source;
import Domain.Business.Objects.Employee.Carrier;
import Domain.Business.Objects.Shift.Shift;
import Globals.Enums.OrderStatus;
import Globals.Enums.ShiftTypes;
import Globals.Enums.ShippingAreas;
import Globals.Enums.TransportStatus;
import Globals.Pair;

import java.time.LocalDate;
import java.util.*;
//TODO not finished methods(ADD,GET,getAllTransports,getPendingTransports,getInProgressTransports,getCompletedTransports)
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
    //TODO not implemented yet, can do after adding DAL objects
    public List<Transport> getAllTransports(){
        return null;
    }



    public void advanceSite(int transportSN,int siteID) throws Exception {
        Transport transport = getTransport(transportSN);
        if(transport.getStatus()==TransportStatus.inProgress){
            boolean isDestVisit = transport.destVisit(siteID);
            boolean lastSite = transport.visitSite(siteID);
            if(isDestVisit){
                List<Integer> orders = transport.gerOrders();
                for (Integer orderID:orders) {
                   TransportOrder order = orderController.getTransportOrder(orderID);
                   if(siteID==order.getDst()){
                       DestinationDocument document = new DestinationDocument(orderID,siteID,order.getProducts());
                       documentController.uploadDestinationDocument(document);
                       TransportDocument trd = documentController.getTransportDocument(transportSN);
                       if(trd == null){
                           trd = new TransportDocument(transport.getStartTime(),transport.getTruckNumber(),transport.getDriverID());
                           documentController.uploadTransportDocument(trd);
                       }
                       trd.addDoc(orderID);
                   }
                }
            }
            if(lastSite){
                endTransport(transportSN);
            }
        }
        else{
            throw new Exception("This transport is not in progress");
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
                if(order.getStatus()== OrderStatus.waiting){
                    int extraWeight  = orderController.getExtraWeight(order);
                    updateWeight(transport,extraWeight);
                    ShippingAreas sourceShip = siteController.getSource(order.getSrc()).getAddress().getShippingAreas();
                    ShippingAreas destShip = siteController.getDestination(order.getDst()).getAddress().getShippingAreas();
                    transport.addOrder(order,sourceShip,destShip);
                    order.order();
                }
                else{
                    throw new Exception("this order already out");
                }
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
            List<Transport> allTransports = getAllTransports();
            if(!(isAvailable(allTransports,truck)&&transport.placeTruck(licenseNumber)))
            {
                throw new Exception("truck cant be placed");
            }
        }
        else{
            throw new Exception("the transport is not in padding list");
        }
    }
    public void placeDriver(int transportSN, String empID) throws Exception {
        Transport transport = getTransport(transportSN);
        if(transport.isPlacedTruck()){
            Carrier carrier = employeeController.getCarrier(empID);
            Truck truck = truckController.getTruck(transport.getTruckNumber());
            if(truck.canDriveOn(carrier.getLicenses()))
            {
                if(!transport.isPlacedCarrier()) {
                    Shift shift = shiftController.getShift(transport.getShift().getLeft(),transport.getShift().getRight());
                    Set<String> carriersInShift = shift.getCarrierIDs();
                    if(carriersInShift.contains(carrier.getId())){
                        List<Transport> allTransports = getAllTransports();
                        if(!isAvailable(allTransports,carrier)){
                            throw new Exception("The carrier is already in a transport in this shift");
                        }
                        else{
                            transport.placeDriver(empID);
                        }
                    }
                    else{
                        throw new Exception("The Carrier is not in this shift");
                    }

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
            transport.endTransport();
        }
        else{
            throw new Exception("this is not a inProgress transport");
        }
    }
    private boolean isAvailable(List<Transport> transports,Truck c){
        for (Transport t:transports) {
            if(t.getTruckNumber()==c.getLicenseNumber()){
                return false;
            }
        }
        return true;
    }
    public boolean isAvailable(List<Transport> transports,Carrier c){
        for (Transport t:transports) {
            if(t.getDriverID()==c.getId()){
                return false;
            }
        }
        return true;
    }
    //GETTERS
    //TODO fix the getters after DAL fix
    public HashMap<Integer, Transport> getPendingTransports() {
        return pendingTransports;
    }

    public HashMap<Integer, Transport> getInProgressTransports() {
        return inProgressTransports;
    }

    public HashMap<Integer, Transport> getCompletedTransports() {
        return completedTransports;
    }

    //TODO will be added in the next assignment
    /*
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
    }*/
}
