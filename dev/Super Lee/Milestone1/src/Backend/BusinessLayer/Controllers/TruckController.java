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


    public void removeTruck(int truckLN) throws Exception {
        if(trucks.containsKey(truckLN))
        {
            trucks.remove(truckLN);
        }
        else {
            throw new Exception("Truck does not exist");
        }
    }

    public boolean updateTruck(Truck updatedTruck)
    {
        //TODO: Implement in future
        return false;
    }

    public List<Truck> getAvailableTrucks(LocalDateTime time, LicenseTypes model)
    {
        //TODO: implement
        return null;
    }

    public void addTruck(int licenseNumber, LicenseTypes model, int netWeight, int maxCapacityWeight) throws Exception {
        if(!trucks.containsKey(licenseNumber))
        {
            trucks.put(licenseNumber, new Truck(licenseNumber, model, netWeight, maxCapacityWeight));
        }
        else {
            throw new Exception("A truck with this license number already exists");
        }
    }
}
