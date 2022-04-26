package Backend.ServiceLayer;


import Backend.Globals.Enums.LicenseTypes;
import Backend.Globals.Enums.ShippingAreas;

import java.util.HashMap;

public class Service {
    private TruckService truckSer;
    private TransportService transportSer;
    private DocumentService documentSer;
    public Service() {
        truckSer = new TruckService();
        transportSer = new TransportService();
        documentSer = new DocumentService();
    }

    //Truck:
    public Result addTruck(int licenseNumber, LicenseTypes model, int netWeight, int maxCapacityWeight)
    {
        return truckSer.addTruck(licenseNumber, model, netWeight, maxCapacityWeight);
    }
    public Result removeTruck(int licenseNumber)
    {
        return truckSer.removeTruck(licenseNumber);
    }
    public Result getAvailableTrucks()
    {
        //TODO: implement
        return null;
    }
    //Document:
    public Result getDestinationDocument(int destinationDocumentSN)
    {
        return truckSer.removeTruck(destinationDocumentSN);
    }
    public Result getTransportDocument(int transportDocumentSN)
    {
        return truckSer.removeTruck(transportDocumentSN);
    }

    //Transport:
    public Result getTransportOrders()
    {
        return transportSer.getTransportOrders();
    }
    public Result getTransportOrders(ShippingAreas areas)
    {
        return transportSer.getTransportOrders(areas);
    }
    public Result getInProgressTransports()
    {
        return transportSer.getInProgressTransports();
    }
    public Result getWaitingTransports()
    {
        return transportSer.getWaitingTransports();
    }
    public Result getPastTransports()
    {
        return transportSer.getPastTransports();
    }
    public Result addTransportOrder(int srcID, int dstID, HashMap<String, Integer> productList) {
        return transportSer.addTransportOrder(srcID, dstID, productList);
    }
    public Result startTransport(int transportSN)
    {
        return transportSer.startTransport(transportSN);
    }
    public Result advanceTransportSite(int transportSN)
    {
        //TODO: Impl
        return null;
    }
    public Result redesignTransport(int transportSN)
    {
        //TODO: Impl
        return null;
    }
    public Result weighingTransportInSource(int transportSN, int weight)
    {
        //TODO: Impl
        return null;
    }
    public Result inviteTransport()
    {
        return null;//transportSer.inviteTransport();
    }

    public Result placeDriver()
    {
        //TODO: implement
        return null;
    }
    public Result placeTruck()
    {
        //TODO: implement
        return null;
    }




    //-----------------------------------

    public Result weightTruck(int transportSN)
    {
        //TODO: implement
        return null;
    }

    //Maybe driver and truck will be together


    public Result getAvailableDrivers()
    {
        //TODO: implement
        return null;
    }



}
