package Domain.Service.Objects.Document;

import Presentation.CLIPresentation.Factories.PresentationDocumentFactory;

public abstract class Document {
    private int documentSN;

    public Document(int documentSN) {
        this.documentSN = documentSN;
    }

    public int getDocumentSN() {
        return documentSN;
    }

    public abstract Presentation.CLIPresentation.Objects.Document.Document accept(PresentationDocumentFactory presentationDocumentFactory);
}
