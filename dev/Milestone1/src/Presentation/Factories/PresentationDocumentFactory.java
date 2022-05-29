package Presentation.Factories;

import Presentation.Objects.Document.*;

public class PresentationDocumentFactory {
    public Document createPresentationDocument(Domain.Service.Objects.Document.Document doc){
        return doc.accept(this);
    }

    public DestinationDocument createPresentationDocument(Domain.Service.Objects.Document.DestinationDocument destinationDoc){
        return new DestinationDocument(destinationDoc);
    }

    public TransportDocument createPresentationDocument(Domain.Service.Objects.Document.TransportDocument transportDoc){
        return new TransportDocument(transportDoc);
    }
}
