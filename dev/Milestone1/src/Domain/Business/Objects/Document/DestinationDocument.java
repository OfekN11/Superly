package Domain.Business.Objects.Document;

import Domain.Service.ServiceDocumentFactory;

import java.util.List;

public class DestinationDocument extends  Document{
    private int ID;
    private int destID;
    private List<String> providedProducts;

    public DestinationDocument(int id,int destID, List<String> providedProducts) {
        ID = id;
        this.destID = destID;
        this.providedProducts = providedProducts;
    }

    public int getDestID() {
        return destID;
    }

    public void setDestID(int destID) {
        this.destID = destID;
    }

    public List<String> getProvidedProducts() {
        return providedProducts;
    }

    public void setProvidedProducts(List<String> providedProducts) {
        this.providedProducts = providedProducts;
    }

    @Override
    public Domain.Service.Objects.Document accept(ServiceDocumentFactory serviceDocumentFactory) {
        return serviceDocumentFactory.createServiceDocument(this);
    }
}
