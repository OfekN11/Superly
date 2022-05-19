package Domain.Business.Objects.Document;

import Domain.Service.ServiceDocumentFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//TODO the transport document need to hold the destination documents
public class TransportDocument extends Document{
    private int transportID;
    private LocalDateTime startTime;
    private int truckNumber;
    private String driverName;
    private List<Integer> destinationDocuments;
    private boolean doRedesign;
    private String redesign;//Write what do?

    public TransportDocument(LocalDateTime startTime, int truckNumber, String driverName) {
        this.startTime = startTime;
        this.truckNumber = truckNumber;
        this.driverName = driverName;
        destinationDocuments = new ArrayList<>();
        this.doRedesign = false;
        this.redesign = "";
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
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
    public List<Integer> getDocuments(){
        return destinationDocuments;
    }
    public void addDoc(Integer id){
        destinationDocuments.add(id);
    }

    public boolean isDoRedesign() {
        return doRedesign;
    }

    public void setDoRedesign(boolean doRedesign) {
        this.doRedesign = doRedesign;
    }

    public String getRedesign() {
        return redesign;
    }

    public void setRedesign(String redesign) {
        this.redesign = redesign;
    }

    @Override
    public Domain.Service.Objects.Document.TransportDocument accept(ServiceDocumentFactory serviceDocumentFactory) {
        return serviceDocumentFactory.createServiceDocument(this);
    }
}
