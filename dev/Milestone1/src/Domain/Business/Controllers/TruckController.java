package Domain.Business.Controllers;

import Domain.Business.Objects.Truck;
import Domain.DAL.Controllers.TruckDataMapper;
import Globals.Enums.TruckModel;

import java.util.HashMap;

public class TruckController {
    private final TruckDataMapper truckDataMapper = new TruckDataMapper();
    public TruckController() {

    }
    //TODO: Add tru and catch of throw Exception
    public void removeTruck(int licenseNumber) throws Exception {
        truckDataMapper.delete(licenseNumber);
    }

    //TODO not right implementation
    public void addTruck(int licenseNumber, TruckModel model, int netWeight, int maxCapacityWeight) throws Exception {
        Truck truck = new Truck(licenseNumber, model, netWeight, maxCapacityWeight);
        truckDataMapper.insert(truck);
    }
    //TODO not right implementation
    public Truck getTruck(int truckNumber) throws Exception {
        return truckDataMapper.get(truckNumber);
    }
}
