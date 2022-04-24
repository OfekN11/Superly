package Backend.ServiceLayer.Factory;

import Backend.ServiceLayer.Objects.DestinationDocument;
import Backend.ServiceLayer.Objects.Document;
import Backend.ServiceLayer.Objects.TransportDocument;

public class ServiceDocumentFactory {
    public Document createServiceDocument(Backend.BusinessLayer.Objects.Document doc){
        return doc.accept(this);
    }

    public DestinationDocument createServiceDocument(Backend.BusinessLayer.Objects.DestinationDocument destinationDoc){
        return new DestinationDocument(destinationDoc);
    }

    public TransportDocument createServiceDocument(Backend.BusinessLayer.Objects.TransportDocument transportDoc){
        return new TransportDocument(transportDoc);
    }

}
