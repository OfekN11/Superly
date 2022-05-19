package Domain.Business.Controllers;

import Domain.Business.Objects.Truck;
import Domain.DAL.Controllers.TruckDataMapper;
import Globals.Enums.TruckModel;

import java.util.HashMap;
//TODO not finished methods (ADD,GET,REMOVE)
public class TruckController {
    private final TruckDataMapper truckDataMapper = new TruckDataMapper();
    public TruckController() {
    }

    //TODO Change the exception
    public void removeTruck(int licenseNumber) throws Exception {
        truckDataMapper.delete(licenseNumber);
    }

    public void addTruck(int licenseNumber, TruckModel model, int netWeight, int maxCapacityWeight) throws Exception {
        Truck truck = new Truck(licenseNumber, model, netWeight, maxCapacityWeight);
        truckDataMapper.insert(truck);
    }

    public Truck getTruck(int truckNumber) throws Exception {
        return truckDataMapper.get(truckNumber);
    }
}
