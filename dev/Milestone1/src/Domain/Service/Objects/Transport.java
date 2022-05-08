package Domain.Service.Objects;


import Globals.Enums.ShippingAreas;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class Transport {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String driverName;
    private  int truckNumber;
    private  int truckWeight;
    //Todo:
    //private List<Source> sources;
    //private List<Destination> destinations;
    //private HashMap<ShippingAreas, Integer> shippingAreas;
    public Transport(Domain.Business.Objects.Transport transport) {
        startTime = transport.getStartTime();
        endTime = transport.getEndTime();
        driverName = transport.getDriverName();
        truckNumber = transport.getTruckNumber();
        truckWeight = transport.getTruckWeight();
    }


}
