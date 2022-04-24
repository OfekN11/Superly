package Backend.BusinessLayer.Controllers;

import Backend.BusinessLayer.Objects.DestinationDocument;
import Backend.BusinessLayer.Objects.TransportDocument;

import java.util.HashMap;

public class DocumentController {
    private HashMap<Integer, TransportDocument> transportDocuments;
    private HashMap<Integer, DestinationDocument> destinationDocuments;

    public DocumentController() {
        transportDocuments = new HashMap<>();
        destinationDocuments = new HashMap<>();
    }

}
