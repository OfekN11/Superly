package Domain.Service.Objects;

import Domain.Service.ServiceDocumentFactory;

public class DestinationDocument extends Document{
    public DestinationDocument(Domain.Business.Objects.Document.DestinationDocument destinationDoc) {
        super();
    }

    @Override
    public Document accept(ServiceDocumentFactory serviceDocumentFactory) {
        return null;
    }
}
