package Domain.Service.Objects.Document;

import Domain.Service.Objects.Document.Document;
import Domain.Service.ServiceDocumentFactory;

public class TransportDocument extends Document {
    public TransportDocument(Domain.Business.Objects.Document.TransportDocument transportDoc) {
        super(transportDoc.getSN());
    }

    @Override
    public Document accept(ServiceDocumentFactory serviceDocumentFactory) {
        return null;
    }
}
