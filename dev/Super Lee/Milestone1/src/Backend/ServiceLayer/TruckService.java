package Backend.ServiceLayer;

import Backend.BusinessLayer.Controllers.TruckController;
import Backend.Globals.Enums.LicenseTypes;

public class TruckService {
    private TruckController controller;

    public TruckService() {
        this.controller = new TruckController();
    }

    /**
     * Calls for data from persistent to load into the business layer
     *
     * @return Result detailing success of operation
     */
    public Result<Object> loadData(){
        try {
            //controller.loadData();
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }


    public Result addTruck(int licenseNumber, LicenseTypes model, int netWeight, int maxCapacityWeight)
    {
        try {
            controller.addTruck(licenseNumber, model, netWeight, maxCapacityWeight);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }


    public Result removeTruck(int licenseNumber)
    {
        try {
            controller.removeTruck(licenseNumber);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }
}
