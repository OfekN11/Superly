package Backend.BusinessLayer.Controllers;

import Backend.BusinessLayer.Objects.DestinationDocument;
import Backend.BusinessLayer.Objects.TransportDocument;
import Backend.ServiceLayer.Result;

import java.util.HashMap;

public class DocumentController {
    private HashMap<Integer, TransportDocument> transportDocuments;
    private HashMap<Integer, DestinationDocument> destinationDocuments;

    public DocumentController() {
        transportDocuments = new HashMap<>();
        destinationDocuments = new HashMap<>();
    }
    public DestinationDocument getDestinationDocument(int destinationDocumentSN) throws Exception {
        if(destinationDocuments.containsKey(destinationDocumentSN))
        {
            return destinationDocuments.get(destinationDocumentSN);
        }
        throw new Exception("The document you requested does not exist!");
    }

    public Object getTransportDocument(int transportDocumentSN) throws Exception {
        if(transportDocuments.containsKey(transportDocumentSN))
        {
            return transportDocuments.get(transportDocumentSN);
        }
        throw new Exception("The document you requested does not exist!");
    }
}
