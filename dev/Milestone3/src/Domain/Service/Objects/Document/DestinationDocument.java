package Domain.Service.Objects.Document;

import Presentation.CLIPresentation.Factories.PresentationDocumentFactory;

import java.util.List;

public class DestinationDocument extends Document {
    private int destID;
    private List<String> providedProducts;
    public DestinationDocument(Domain.Business.Objects.Document.DestinationDocument destinationDoc) {
        super(destinationDoc.getID());
        destID = destinationDoc.getDestID();
        providedProducts = destinationDoc.getProvidedProducts();
    }

    public int getDestID() {
        return destID;
    }

    public List<String> getProvidedProducts() {
        return providedProducts;
    }

    @Override
    public Presentation.CLIPresentation.Objects.Document.Document accept(PresentationDocumentFactory presentationDocumentFactory) {
        return presentationDocumentFactory.createPresentationDocument(this);
    }
}
