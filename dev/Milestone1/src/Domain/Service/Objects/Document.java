package Domain.Service.Objects;

import Domain.Service.ServiceDocumentFactory;

public abstract class Document {
    public abstract Document accept(ServiceDocumentFactory serviceDocumentFactory);
}
