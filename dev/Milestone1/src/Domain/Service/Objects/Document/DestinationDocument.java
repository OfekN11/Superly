package Domain.Service.Objects.Document;

import Domain.Service.ServiceDocumentFactory;

public class DestinationDocument extends Document {
    public DestinationDocument(Domain.Business.Objects.Document.DestinationDocument destinationDoc) {
        super(destinationDoc.getSN());
    }

    @Override
    public Document accept(ServiceDocumentFactory serviceDocumentFactory) {
        return null;
    }
}
