package Domain.Business.Objects.Document;

import Domain.Service.ServiceDocumentFactory;

public abstract class Document {
    private static int incSN = 0;
    private int SN;

    public Document() {
        SN = incSN++;
    }

    public int getSN() {
        return SN;
    }

    public abstract Domain.Service.Objects.Document.Document accept(ServiceDocumentFactory serviceDocumentFactory);
}
