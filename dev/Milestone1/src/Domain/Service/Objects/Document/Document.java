package Domain.Service.Objects.Document;

import Domain.Service.ServiceDocumentFactory;

public abstract class Document {
    private int documentSN;

    public Document(int documentSN) {
        this.documentSN = documentSN;
    }

    public int getDocumentSN() {
        return documentSN;
    }

    public abstract Document accept(ServiceDocumentFactory serviceDocumentFactory);
}
