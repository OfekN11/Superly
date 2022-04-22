package ServiceLayer.Objects;

import Globals.Defect;

import java.util.Date;

public class DefectiveItemReport {
    private final int productID;
    private final int storeID;
    private final int amount;
    private final int employeeID;
    private final String description;
    private final Date date;
    private final Defect defect;
    public DefectiveItemReport(BusinessLayer.DefectiveItems report) {
        this.productID=report.getProductID();
        this.storeID=report.getStoreID();
        this.amount=report.getAmount();
        this.employeeID = report.getEmployeeID();
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
                ", employeeID=" + employeeID +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", defect=" + defect +
                '}';
    }
}
