package Backend.BusinessLayer.Objects;

import Backend.ServiceLayer.Factory.ServiceDocumentFactory;

import java.util.Date;
import java.util.List;

public class TransportDocument extends Document{
    private Date date;
    private int truckNumber;
    private String driverName;
    private String sources;
    private String destinations;
    private List<Integer> destsSN;


    @Override
    public Backend.ServiceLayer.Objects.Document accept(ServiceDocumentFactory factory) {
        return factory.createServiceDocument(this);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTruckNumber() {
        return truckNumber;
    }

    public void setTruckNumber(int truckNumber) {
        this.truckNumber = truckNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getDestinations() {
        return destinations;
    }

    public void setDestinations(String destinations) {
        this.destinations = destinations;
    }

    public List<Integer> getDestsSN() {
        return destsSN;
    }

    public void setDestsSN(List<Integer> destsSN) {
        this.destsSN = destsSN;
    }
}
