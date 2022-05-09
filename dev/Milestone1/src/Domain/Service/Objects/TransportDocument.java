package Domain.Service.Objects;

import Domain.Service.ServiceDocumentFactory;

public class TransportDocument extends Document{
    public TransportDocument(Domain.Business.Objects.Document.TransportDocument transportDoc) {
        super();
    }

    @Override
    public Document accept(ServiceDocumentFactory serviceDocumentFactory) {
        return null;
    }
}
