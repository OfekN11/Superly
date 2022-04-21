package Backend.BusinessLayer.Objects;

import java.time.LocalDateTime;
import java.util.List;

public class Transport {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String driverName;
    private  int truckNumber;
    private  int truckWeight;
    private List<Source> sources;
    private List<Destination> destinations;

}
