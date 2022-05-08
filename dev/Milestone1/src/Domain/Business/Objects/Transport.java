package Domain.Business.Objects;

import Backend.BusinessLayer.Objects.Document.TransportDocument;
import Globals.Enums.ShippingAreas;

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
    private List<Source> sources;
    private List<Destination> destinations;
    private List<Integer> transportOrders;
    private HashMap<ShippingAreas, Integer> shippingAreas;

    public Transport() {
        driverName = null;
        truckNumber = -1;
        truckWeight = -1;
        sources = new ArrayList<>();
        destinations = new ArrayList<>();
        transportOrders = new ArrayList<>();
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
        return !sources.isEmpty() && !destinations.isEmpty() && driverPlaced() && truckPlaced();
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
        List<Integer> IDs = new ArrayList<>();
        for (Source src: sources) {
            IDs.add(src.getId());
        }
        return IDs;
    }

    private List<Integer> getDstIDs()
    {
        List<Integer> IDs = new ArrayList<>();
        for (Destination dst: destinations) {
            IDs.add(dst.getId());
        }
        return IDs;
    }
    public TransportDocument toDocument() {
        return new TransportDocument(startTime, truckNumber, driverName, getSrcIDs(), getDstIDs());
    }

    public void addOrder(TransportOrder order)
    {
        sources.add(order.getSrc());
        destinations.add(order.getDst());
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
}
