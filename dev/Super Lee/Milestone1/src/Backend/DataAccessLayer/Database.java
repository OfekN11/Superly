package Backend.DataAccessLayer;

import Backend.BusinessLayer.Objects.DestinationDocument;
import Backend.BusinessLayer.Objects.TransportDocument;
import Backend.BusinessLayer.Objects.Truck;

import java.util.*;

public class Database{
    private List<Truck> trucks;
    private List<TransportDocument> transportDocuments;
    private List<DestinationDocument> destinationDocuments;
    private static Database single_instance = null;


    private Database()
    {
        trucks = new LinkedList<>();
        transportDocuments = new LinkedList<>();
        destinationDocuments = new LinkedList<>();
    }

    public static Database getInstance()
    {
        if (single_instance == null)
            single_instance = new Database();
        return single_instance;
    }
}
