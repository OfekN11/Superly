package Backend.ServiceLayer;


import Backend.Globals.Enums.LicenseTypes;

public class Service {
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
    public Result getInProgressTransports(int transportSN)
    {
        //TODO: implement
        return null;
    }
    public Result getWaitingTransports(int transportSN)
    {
        //TODO: implement
        return null;
    }
    public Result getPastTransports(int transportSN)
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
