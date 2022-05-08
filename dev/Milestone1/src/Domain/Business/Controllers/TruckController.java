package Domain.Business.Controllers;

import Domain.Business.Objects.Truck;
import Globals.Enums.TruckModel;

import java.util.HashMap;

public class TruckController {
    private HashMap<TruckModel, HashMap<Integer, Truck>> trucksFleet;
    public TruckController() {
        trucksFleet = new HashMap<>();
    }


    public void removeTruck(int licenseNumber) throws Exception {
        boolean deleted = false;
        for(TruckModel tm: trucksFleet.keySet())
        {
            if(trucksFleet.get(tm).containsKey(licenseNumber))
            {
                trucksFleet.get(tm).remove(licenseNumber);
                deleted = true;
            }
        }
        if(!deleted){
            throw new Exception("The truck doesn't exist in the truck fleet!");
        }
    }


    public void addTruck(int licenseNumber, TruckModel model, int netWeight, int maxCapacityWeight) throws Exception {
        if (!trucksFleet.containsKey(model))
        {
            trucksFleet.put(model, new HashMap<>());
        }
        if(!trucksFleet.get(model).containsKey(licenseNumber))
        {
            trucksFleet.get(model).put(licenseNumber, new Truck(licenseNumber, model, netWeight, maxCapacityWeight));
        }
        else {
            throw new Exception("The truck fleet contain the truck!");
        }
    }

    public Truck getTruck(int truckNumber) throws Exception {
        for(TruckModel tm: trucksFleet.keySet())
        {
            if(trucksFleet.get(tm).containsKey(truckNumber))
            {
                return trucksFleet.get(tm).get(truckNumber);
            }
        }
        throw new Exception("The truck doesn't exist!");
    }
}
