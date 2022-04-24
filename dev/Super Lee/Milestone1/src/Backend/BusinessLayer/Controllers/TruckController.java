package Backend.BusinessLayer.Controllers;

import Backend.BusinessLayer.Objects.Transport;
import Backend.BusinessLayer.Objects.Truck;
import Backend.Globals.Enums.LicenseTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TruckController {
    HashMap<Integer, Truck> trucks;
    //Maybe part to type

    public TruckController() {
        trucks = new HashMap<>();
    }

    public boolean addTruck(Truck newTruck)
    {
        if(!trucks.containsKey(newTruck.getLicenseNumber()))
        {
            trucks.put(newTruck.getLicenseNumber(), newTruck);
            return true;
        }
        return false;
    }

    public boolean removeTruck(int truckLN)
    {
        if(trucks.containsKey(truckLN))
        {
            trucks.remove(truckLN);
            return true;
        }
        return false;
    }

    public boolean updateTruck(Truck updatedTruck)
    {
        if(trucks.containsKey(updatedTruck.getLicenseNumber()))
        {
            trucks.replace(updatedTruck.getLicenseNumber(), updatedTruck);
            return true;
        }
        return false;
    }

    public List<Truck> getAvailableTrucks(LocalDateTime time, LicenseTypes model)
    {
        //TODO: implement
        return null;
    }

}
