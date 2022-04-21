package Backend.BusinessLayer.Objects;

import java.util.List;

public class DestinationDocument {
    private static int incSN = 0;
    private int destDocumentSN = 0;
    private List<String> providedProducts;

    public DestinationDocument(List<String> providedProducts) {
        destDocumentSN = incSN;
        this.providedProducts = providedProducts;
        incSN += 1;
    }
}
