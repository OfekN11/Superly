package Domain.Service.Objects.Document;

import Presentation.Factories.PresentationDocumentFactory;

public abstract class Document {
    private int documentSN;

    public Document(int documentSN) {
        this.documentSN = documentSN;
    }

    public int getDocumentSN() {
        return documentSN;
    }

    public abstract Presentation.Objects.Document.Document accept(PresentationDocumentFactory presentationDocumentFactory);
}
