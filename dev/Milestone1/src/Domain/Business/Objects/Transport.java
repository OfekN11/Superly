package Domain.Business.Objects;

import Domain.Business.Objects.Document.TransportDocument;
import Domain.Business.Objects.Site.Destination;
import Domain.Business.Objects.Site.Source;
import Globals.Enums.ShippingAreas;
import Globals.Enums.TransportStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Transport {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String driverName;
    private  int truckNumber;
    private  int truckWeight;
    private List<Integer> sourcesID;
    private List<Integer> destinationsID;
    private List<Integer> transportOrders;
    private HashMap<ShippingAreas, Integer> shippingAreas;
    private TransportStatus status;

    public Transport() {
        driverName = null;
        truckNumber = -1;
        truckWeight = -1;
        sourcesID = new ArrayList<>();
        destinationsID = new ArrayList<>();
        transportOrders = new ArrayList<>();
        status = TransportStatus.padding;
    }

    public boolean startTransport()
    {
        if (startTime == null)
        {
            startTime = LocalDateTime.now();
            //TODO: calcEndTime();
            return true;
        }
        return false;
    }

    public boolean placeTruck(int licenseNumber)
    {
        truckNumber = licenseNumber;
        return true;
    }

    public void placeDriver(String driverName)
    {
        this.driverName = driverName;
    }
    public boolean driverPlaced()
    {
        return driverName != null;
    }

    public boolean truckPlaced()
    {
        return driverName != null;
    }

    public boolean readyToGo()
    {
        return !sourcesID.isEmpty() && !destinationsID.isEmpty() && driverPlaced() && truckPlaced();
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

    public String getDriverName() {
        return driverName;
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
    public TransportDocument toDocument() {
        return new TransportDocument(startTime, truckNumber, driverName, getSrcIDs(), getDstIDs());
    }

    public void addOrder(TransportOrder order)
    {
        sourcesID.add(order.getSrc());
        destinationsID.add(order.getDst());
        addShippingArea(order.getSrc().getAddress().getShippingAreas());
        addShippingArea(order.getDst().getAddress().getShippingAreas());
        transportOrders.add(order.getID());
    }

    public boolean updateWeight(int newWeight, int maxCapacityWeight) throws Exception {
        if(newWeight > maxCapacityWeight){
            return false;
        }
        else{
            truckWeight = newWeight;
            return true;
        }
    }
    public TransportStatus getStatus(){
        return status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getTruckWeight() {
        return truckWeight;
    }
}
