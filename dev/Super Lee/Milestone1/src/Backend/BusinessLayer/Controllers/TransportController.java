package Backend.BusinessLayer.Controllers;

import Backend.BusinessLayer.Objects.Transport;
import Backend.BusinessLayer.Objects.TransportDocument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransportController {
    List<Transport> waitingTransports;
    List<Transport> InProgressTransports;
    List<Transport> DoneTransports;
    HashMap<Integer, TransportDocument> transportDocuments;

    public TransportController() {
        waitingTransports =  new ArrayList<>();
        InProgressTransports =  new ArrayList<>();
        DoneTransports =  new ArrayList<>();
        transportDocuments = new HashMap<>();
    }

    public TransportDocument getTransportDocuments(int transportDocumentSN)
    {
        if(transportDocuments.containsKey(transportDocumentSN))
        {
            return transportDocuments.get(transportDocumentSN);
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

}
