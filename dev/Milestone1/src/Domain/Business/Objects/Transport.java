package Domain.Business.Objects;

import Domain.Business.Objects.Document.TransportDocument;
import Domain.Business.Objects.Site.Destination;
import Domain.Business.Objects.Site.Source;
import Globals.Enums.ShiftTypes;
import Globals.Enums.ShippingAreas;
import Globals.Enums.TransportStatus;
import Globals.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Transport {
    private static int incSN = 0;
    private int SN;
    private String startTime;
    private String endTime;
    private String driverID;
    private  int truckNumber;
    private  int truckWeight;
    private List<Integer> sourcesID;
    private List<Integer> destinationsID;
    private List<Integer> transportOrders;
    private HashMap<ShippingAreas, Integer> shippingAreas;
    private TransportStatus status;
    //TODO need to change the shift restart. the restart of Transport need to be with shift as argument

    private Pair<LocalDate, ShiftTypes> shift;

    public Transport(Pair<LocalDate, ShiftTypes> shift) {
        SN = incSN++;
        driverID = "";
        truckNumber = -1;
        truckWeight = -1;
        this.startTime = "";
        this.endTime = "";
        sourcesID = new ArrayList<>();
        destinationsID = new ArrayList<>();
        transportOrders = new ArrayList<>();
        status = TransportStatus.padding;
        this.shift = shift;
    }

    public Transport(int SN, String startTime, String endTime, String driverID, int truckNumber, int truckWeight,TransportStatus status,Pair<LocalDate, ShiftTypes> shift, List<Integer> sourcesID, List<Integer> destinationsID,  List<Integer> transportOrders) {
        this.SN = SN;
        incSN++;
        this.startTime = startTime;
        this.endTime = endTime;
        this.driverID = driverID;
        this.truckNumber = truckNumber;
        this.truckWeight = truckWeight;
        this.sourcesID = sourcesID;
        this.destinationsID = destinationsID;
        this.transportOrders = transportOrders;
        this.status = status;
        this.shift = shift;
    }

    public List<Integer> getSourcesID() {
        return sourcesID;
    }

    public List<Integer> getDestinationsID() {
        return destinationsID;
    }

    public List<Integer> getTransportOrders() {
        return transportOrders;
    }

    public HashMap<ShippingAreas, Integer> getShippingAreas() {
        return shippingAreas;
    }

    public int getSN() {
        return SN;
    }

    public void startTransport() throws Exception {
        if (status == TransportStatus.padding)
        {
            startTime = LocalDateTime.now().toString();
            status = TransportStatus.inProgress;
        }
        throw new Exception("transport already started");
    }
    public void endTransport() throws Exception {
        if (status == TransportStatus.inProgress)
        {
            endTime = LocalDateTime.now().toString();
            status = TransportStatus.done;
        }
        throw new Exception("transport is not in Progress");
    }
    public boolean isDoneTransport(){
        //TODO need to be implemented
        return false;
    }

    public boolean placeTruck(int licenseNumber)
    {
        if(truckNumber==-1){
            truckNumber = licenseNumber;
            return true;
        }
        return false;
    }

    public void placeDriver(String driverId)
    {
        this.driverID = driverId;
    }

    public boolean readyToGo()
    {
        return !sourcesID.isEmpty() && !destinationsID.isEmpty() && isPlacedTruck() && isPlacedCarrier() && !transportOrders.isEmpty();
    }
    public Pair<LocalDate,ShiftTypes> getShift(){
        return shift;
    }

    private void addShippingArea(ShippingAreas sa)
    {
        if(!shippingAreas.containsKey(sa))
        {
            shippingAreas.put(sa, 1);
        }
        else {
            shippingAreas.replace(sa, shippingAreas.get(sa) + 1);
        }
    }
    public boolean isPlacedTruck(){
        return truckNumber!=-1;
    }
    public boolean isPlacedCarrier(){
        return (driverID.equals(""));
    }
    private void removeShippingArea(ShippingAreas sa)
    {
        if(shippingAreas.get(sa) > 1)
        {
            shippingAreas.replace(sa, shippingAreas.get(sa) - 1);
        }
        else {
            shippingAreas.remove(sa);
        }
    }
    public String getDriverID(){
        return driverID;
    }

    public int getTruckNumber() {
        return truckNumber;
    }

    private List<Integer> getSrcIDs()
    {
        return sourcesID;
    }

    private List<Integer> getDstIDs()
    {
        return destinationsID;
    }
    /*public TransportDocument toDocument() {
        return new TransportDocument(startTime, truckNumber, driverName, getSrcIDs(), getDstIDs());
    }*/

    public void addOrder(TransportOrder order,ShippingAreas src,ShippingAreas dst)
    {
        sourcesID.add(order.getSrc());
        destinationsID.add(order.getDst());
        transportOrders.add(order.getID());
    }

    public boolean updateWeight(int newWeight, int maxCapacityWeight) throws Exception {
        if(truckWeight+newWeight > maxCapacityWeight){
            status = TransportStatus.redesign;
            return false;
        }
        else{
            truckWeight = truckWeight+newWeight;
            return true;
        }
    }
    public List<Integer> gerOrders(){return transportOrders;}
    public TransportStatus getStatus(){
        return status;
    }
    public void changeStatus(TransportStatus stat){
        status = stat;
    }

    public String  getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getTruckWeight() {
        return truckWeight;
    }

    public boolean destVisit(int siteID) {
        if(sourcesID.size()==0 && destinationsID.contains(siteID)){
            return true;
        }
        return false;
    }

    public boolean visitSite(int siteID) throws Exception {
        if(sourcesID.contains(siteID)){
            for (Integer src:sourcesID) {
                sourcesID.remove(src);
            }
        }
        else{
            if(destVisit(siteID)){
                for (Integer src:destinationsID) {
                    destinationsID.remove(src);
                }
            }
            else{
                throw new Exception("this is not valid site. the site ID is not in the order or all the sources not visited yet");
            }
        }
        return sourcesID.size()==0 && destinationsID.size()==0;
    }
}
