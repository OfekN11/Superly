package Backend.BusinessLayer.Objects;

import Backend.ServiceLayer.Factory.ServiceDocumentFactory;

import java.util.List;

public class DestinationDocument extends Document{
    private static int destDocumentSN;
    private List<String> providedProducts;

    @Override
    public Backend.ServiceLayer.Objects.Document accept(ServiceDocumentFactory factory) {
        return factory.createServiceDocument(this);
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
}
