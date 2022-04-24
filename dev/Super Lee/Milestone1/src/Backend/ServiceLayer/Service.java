package Backend.ServiceLayer;


import Backend.Globals.Enums.LicenseTypes;

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
    public Result inviteTransport()
    {
        //TODO: implement
        return null;
    }
    public Result getInProgressTransports()
    {
        //TODO: implement
        return null;
    }
    public Result getWaitingTransports()
    {
        //TODO: implement
        return null;
    }
    public Result getPastTransports()
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
    public Result getAvailableTrucks()
    {
        //TODO: implement
        return null;
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
    public Result getAvailableDrivers()
    {
        //TODO: implement
        return null;
    }


}
