package Domain.Service.Objects;


import Globals.Enums.ShippingAreas;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class Transport {
    private int transportID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String driverID;
    private  int truckNumber;
    private  int truckWeight;
    //Todo:
    //private List<Source> sources;
    //private List<Destination> destinations;
    //private HashMap<ShippingAreas, Integer> shippingAreas;
    public Transport(Domain.Business.Objects.Transport transport) {
        transportID = transport.getSN();
        startTime = transport.getStartTime();
        endTime = transport.getEndTime();
        driverID = transport.getDriverID();
        truckNumber = transport.getTruckNumber();
        truckWeight = transport.getTruckWeight();
    }

    public int getTransportID() {
        return transportID;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getDriverID() {
        return driverID;
    }

    public int getTruckNumber() {
        return truckNumber;
    }

    public int getTruckWeight() {
        return truckWeight;
    }
}
