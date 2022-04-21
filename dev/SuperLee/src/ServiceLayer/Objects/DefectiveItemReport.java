package ServiceLayer.Objects;

import static BusinessLayer.DefectiveItems.Defect;
import java.util.Date;

public class DefectiveItemReport {
    private final int productID;
    private final int storeID;
    private final int amount;
    private final String description;
    private final Date date;
    private final Defect defect;
    public DefectiveItemReport(BusinessLayer.DefectiveItems report) {
        this.productID=report.getProductID();
        this.storeID=report.getStoreID();
        this.amount=report.getAmount();
        this.description=report.getDescription();
        this.date=report.getDate();
        this.defect=report.getDefect();
    }

    @Override
    public String toString() {
        return "DefectiveItemReport{" +
                "productID=" + productID +
                ", storeID=" + storeID +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", defect=" + defect +
                '}';
    }
}
