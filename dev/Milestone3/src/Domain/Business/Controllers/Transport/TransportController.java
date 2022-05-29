package Domain.Business.Controllers.Transport;

import Domain.Business.Controllers.HR.EmployeeController;
import Domain.Business.Controllers.HR.ShiftController;
import Domain.Business.Objects.*;
import Domain.Business.Objects.Document.DestinationDocument;
import Domain.Business.Objects.Document.TransportDocument;
import Domain.Business.Objects.Employee.Carrier;
import Domain.DAL.Controllers.TransportMudel.TransportDAO;
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
    private final TransportDAO transportDataMapper = new TransportDAO();
    private TruckController truckController;
    private OrderController orderController;
    private EmployeeController employeeController;
    private DocumentController documentController;
    private SiteController siteController;
    private ShiftController shiftController;

    public TransportController() {
        truckController = new TruckController();
        employeeController = new EmployeeController();
        orderController = new OrderController();
        siteController = new SiteController();
        shiftController = new ShiftController();
        documentController = new DocumentController();
    }
    public void createTransport(Pair<LocalDate,ShiftTypes> shift) throws Exception {
        if(shiftController.getShift(shift.getLeft(),shift.getRight()).getStorekeeperCount()>0){
            Transport transport = new Transport(shift);
            transportDataMapper.save(transport);
        }
        else{
            throw new Exception("there is no sorter in this shift");
        }

    }

    public List<Transport> getAllTransports(){
        return transportDataMapper.getAll();
    }



    public void advanceSite(int transportSN,int siteID) throws Exception {
        Transport transport = getTransport(transportSN);
        if(transport.getStatus()==TransportStatus.inProgress){
            boolean isDestVisit = transport.destVisit(siteID);
            boolean lastSite = transport.visitSite(siteID);
            transportDataMapper.save(transport);
            if(isDestVisit){
                List<Integer> orders = transport.gerOrders();
                for (Integer orderID:orders) {
                   TransportOrder order = orderController.getTransportOrder(orderID);
                   if(siteID==order.getDst()){
                       DestinationDocument document = new DestinationDocument(orderID,siteID,order.getProducts());
                       documentController.uploadDestinationDocument(document);
                       TransportDocument trd;
                       try{
                           trd = documentController.getTransportDocument(transportSN);
                       }
                       catch (Exception e) {
                           trd = new TransportDocument(transport.getSN(), transport.getStartTime().toString(), transport.getTruckNumber(), transport.getDriverID());
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

    public Transport getTransport(int transportSN) throws Exception {
        Transport transport = transportDataMapper.get(transportSN);
        if(transport == null){
            throw new Exception("Transport not found");
        }
        return transport;
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
                    transportDataMapper.save(transport);
                    order.order();
                    orderController.updateOrder(order);
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
            if(!(isAvailable(allTransports,truck) && transport.placeTruck(licenseNumber)))
            {
                throw new Exception("truck cant be placed");
            }
            else{
                transportDataMapper.save(transport);
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
                if(transport.isPlacedCarrier()) {
                    Shift shift = shiftController.getShift(transport.getShift().getLeft(),transport.getShift().getRight());
                    Set<String> carriersInShift = shift.getCarrierIDs();
                    if(carriersInShift.contains(carrier.getId())){
                        List<Transport> allTransports = getAllTransports();
                        if(!isAvailable(allTransports,carrier)){
                            throw new Exception("The carrier is already in a transport in this shift");
                        }
                        else{
                            transport.placeDriver(empID);
                            transportDataMapper.save(transport);
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
                transportDataMapper.save(transport);
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
            transportDataMapper.save(transport);
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
        List<Transport> allTransports = getAllTransports();
        HashMap<Integer,Transport> padding = new HashMap<>();
        for(Transport t : allTransports){
            if(t.getStatus()==TransportStatus.padding){
                padding.put(t.getSN(),t);
            }
        }
        return padding;
    }

    public HashMap<Integer, Transport> getInProgressTransports() {
        List<Transport> allTransports = getAllTransports();
        HashMap<Integer,Transport> inProgress = new HashMap<>();
        for(Transport t : allTransports){
            if(t.getStatus()==TransportStatus.inProgress){
                inProgress.put(t.getSN(),t);
            }
        }
        return inProgress;
    }

    public HashMap<Integer, Transport> getCompletedTransports() {
        List<Transport> allTransports = getAllTransports();
        HashMap<Integer,Transport> complete = new HashMap<>();
        for(Transport t : allTransports){
            if(t.getStatus()==TransportStatus.done){
                complete.put(t.getSN(),t);
            }
        }
        return complete;
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
