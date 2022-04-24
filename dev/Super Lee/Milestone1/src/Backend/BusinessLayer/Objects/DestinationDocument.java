package Backend.BusinessLayer.Objects;

import Backend.ServiceLayer.Factory.ServiceDocumentFactory;

import java.util.List;

public class DestinationDocument extends Document{
    private static int incSN = 0;
    private int destDocumentSN = 0;
    private List<String> providedProducts;

    public DestinationDocument(List<String> providedProducts) {
        destDocumentSN = incSN;
        this.providedProducts = providedProducts;
        incSN += 1;
    }

    public int getDestDocumentSN() {
        return destDocumentSN;
    }

    public List<String> getProvidedProducts() {
        return providedProducts;
    }

    public void setProvidedProducts(List<String> providedProducts) {
        this.providedProducts = providedProducts;
    }

    @Override
    public Backend.ServiceLayer.Objects.Document accept(ServiceDocumentFactory factory) {
        return factory.createServiceDocument(this);
    }
}
