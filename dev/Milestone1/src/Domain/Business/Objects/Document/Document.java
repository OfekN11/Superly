package Backend.BusinessLayer.Objects.Document;

public abstract class Document {
    private static int incSN = 0;
    private int SN;

    public Document() {
        SN = incSN++;
    }

    public int getSN() {
        return SN;
    }
}
