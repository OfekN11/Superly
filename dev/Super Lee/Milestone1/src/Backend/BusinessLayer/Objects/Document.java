package Backend.BusinessLayer.Objects;

import Backend.ServiceLayer.Factory.ServiceDocumentFactory;

public abstract class Document {
    public abstract Backend.ServiceLayer.Objects.Document accept(ServiceDocumentFactory factory);
}
