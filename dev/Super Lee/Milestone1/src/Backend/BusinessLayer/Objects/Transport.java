package Backend.BusinessLayer.Objects;

import Backend.Globals.Enums.ShippingAreas;

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
    private HashMap<ShippingAreas, Integer> shippingAreas;

    public Transport() {
        driverName = null;
        truckNumber = -1;
        truckWeight = -1;
        sources = new ArrayList<>();
        destinations = new ArrayList<>();
    }


    public boolean startTransport()
    {
        if (startTime == null)
        {
            startTime = LocalDateTime.now();
            calcEndTime();
            return true;
        }
        return false;
    }

    private void calcEndTime()
    {
        //TODO: Calculation by number of sources and ect.
        //Area count - 1 * 1 day
        endTime = startTime.plusDays(shippingAreas.size() - 1);
        //Source count * 3 hours
        endTime = endTime.plusHours(sources.size() * 3);
        //Destination count * 1 Hours
        endTime = endTime.plusHours(destinations.size());
    }

    public TransportDocument toDocument()
    {
        //TODO: Decide how look redesign

        return null;
    }

    public boolean addSource(Source src)
    {
        //TODO: Maybe do warning when add many sources or add site from another area.
        sources.add(src);
        addShippingArea(src.getAddress().getShippingArea());
        return true;
    }

    public boolean removeSource(Source src)
    {
        if(sources.contains(src))
        {
            removeShippingArea(src.getAddress().getShippingArea());
            sources.remove(src);
            return true;
        }
        return false;
    }

    public boolean addDestination(Destination dst)
    {
        //TODO: Maybe do warning when add many sources or add site from another area.
        destinations.add(dst);
        addShippingArea(dst.getAddress().getShippingArea());
        return true;
    }

    public boolean removeDestination(Destination dst)
    {
        if(destinations.contains(dst))
        {
            removeShippingArea(dst.getAddress().getShippingArea());
            sources.remove(dst);
            return true;
        }
        return false;
    }

    public List<ShippingAreas> getTransportAreas()
    {
        //TODO: Check this
        return new ArrayList<>(shippingAreas.keySet());
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
        return !sources.isEmpty() && !destinations.isEmpty() && driverPlaced() && truckPlaced() && truckWeight != -1;
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
            //TODO: Test
        }
        else {
            shippingAreas.remove(sa);
        }
    }

    private void placeTruck(int truckLN)
    {
        truckNumber = truckLN;
    }

    private void placeDriver(String driverName)
    {
        //TODO: I dont think that have something to change maybe check the valid truck by the tour
        driverName = driverName;
    }

    public LocalDateTime getStartTime() {
        return startTime.plusHours(0);
    }

    public LocalDateTime getEndTime() {
        return endTime.plusHours(0);
    }

    public String getDriverName() {
        return driverName;
    }

    public int getTruckNumber() {
        return truckNumber;
    }

    public int getTruckWeight() {
        return truckWeight;
    }

    public List<Source> getSources() {
        return sources;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public HashMap<ShippingAreas, Integer> getShippingAreas() {
        return shippingAreas;
    }
}
