package Backend.BusinessLayer.Objects.Document;

import java.util.List;

public class DestinationDocument extends  Document{
    private int destID;
    private List<String> providedProducts;

    public DestinationDocument(int destID, List<String> providedProducts) {
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
}
