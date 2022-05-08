package Backend.BusinessLayer;

import Backend.BusinessLayer.Controllers.*;
import Backend.BusinessLayer.Objects.Document.DestinationDocument;
import Backend.BusinessLayer.Objects.Document.TransportDocument;
import Backend.BusinessLayer.Objects.Driver;
import Backend.BusinessLayer.Objects.Transport;
import Backend.BusinessLayer.Objects.TransportOrder;
import Backend.BusinessLayer.Objects.Truck;
import Globals.Enums.TruckModel;

import java.util.HashMap;
import java.util.List;

public class Facade {
    private static Facade instance = null;
    private DocumentController documentController;
    private DriverController driverController;
    private OrderController orderController;
    private SiteController siteController;
    private TransportController transportController;
    private TruckController truckController;

    private Facade() {
        documentController = DocumentController.getInstance();
        driverController = DriverController.getInstance();
        orderController = OrderController.getInstance();
        siteController = SiteController.getInstance();
        transportController = TransportController.getInstance();
        truckController = TruckController.getInstance();
    }

    public static Facade getInstance(){
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    //Document:
    public DestinationDocument getDestinationDocument(int destinationDocumentSN) throws Exception {
        return documentController.getDestinationDocument(destinationDocumentSN);
    }

    public TransportDocument getTransportDocument(int transportDocumentSN) throws Exception {
        return documentController.getTransportDocument(transportDocumentSN);
    }

    //Driver:

    //Order:

    //Site:

    //Transport:
    public Transport getTransport(int transportSN) throws Exception {
        return transportController.getTransport(transportSN);
    }

    public void addTransportOrder(int srcID, int dstID, HashMap<String, Integer> productList) throws Exception {
        transportController.addTransportOrder(srcID, dstID, productList);
    }

    public void placeTruck(int transportSN, int licenseNumber) throws Exception {
        transportController.placeTruck(transportSN, licenseNumber);
    }

    public void placeDrive(int transportSN, String driverName) throws Exception {
        transportController.placeDrive(transportSN, driverName);
    }

    public void startTransport(int transportSN) throws Exception {
        transportController.startTransport(transportSN);
    }

    public void endTransport(int transportSN) throws Exception {
        transportController.endTransport(transportSN);
    }

    public void redesignTransport(int transportSN) throws Exception {
        transportController.redesignTransport(transportSN);
    }

    public HashMap<Integer, Transport> getPendingTransports() {
        return transportController.getPendingTransports();
    }

    public HashMap<Integer, Transport> getInProgressTransports() {
        return transportController.getInProgressTransports();
    }

    public HashMap<Integer, Transport> getRedesignTransports() {
        return transportController.getRedesignTransports();
    }

    public HashMap<Integer, Transport> getCompletedTransports() {
        return transportController.getCompletedTransports();
    }

    //Truck:
    public void removeTruck(int licenseNumber) throws Exception {
        truckController.removeTruck(licenseNumber);
    }

    public void addTruck(int licenseNumber, TruckModel model, int netWeight, int maxCapacityWeight) throws Exception {
        truckController.addTruck(licenseNumber, model, netWeight, maxCapacityWeight);
    }

    public Truck getTruck(int truckNumber) throws Exception {
        return truckController.getTruck(truckNumber);
    }

    public void addOrderToTransport(int transportSN, int orderID) throws Exception {
        transportController.addOrderToTransport(transportSN, orderID);
    }

    public List<TransportOrder> getTransportOrderInSameArea(int transportSN) throws Exception {
        return transportController.getTransportOrderInSameArea(transportSN);
    }

    public List<TransportOrder> getTransportOrders()
    {
        return transportController.getTransportOrders();
    }

}
