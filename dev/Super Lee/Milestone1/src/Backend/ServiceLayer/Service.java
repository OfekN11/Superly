package Backend.ServiceLayer;


import Backend.Globals.Enums.LicenseTypes;

public class Service {
    private TruckService truckSer;
    private TransportService transportSer;
    public Service() {
    }

    public Result addTruck(int licenseNumber, LicenseTypes model, int netWeight, int maxCapacityWeight)
    {
        //TODO: implement
        return null;
    }
    public Result updateTruck(int licenseNumber, LicenseTypes model, int netWeight, int maxCapacityWeight)
    {
        //TODO: implement
        return null;
    }
    public Result removeTruck(int licenseNumber)
    {
        //TODO: implement
        return null;
    }
    public Result getTransportDocument(int transportDocumentSN)
    {
        //TODO: implement
        return null;
    }
    public Result weightTruck(int transportSN)
    {
        //TODO: implement
        return null;
    }
    public Result inviteTransport()
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
    public Result getDestinationDocument(int destinationDocumentSN)
    {
        //TODO: implement
        return null;
    }

}
