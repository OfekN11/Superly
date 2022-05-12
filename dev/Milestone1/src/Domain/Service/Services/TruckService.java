package Domain.Service.Services;

import Domain.Business.Controllers.ShiftController;
import Domain.Business.Controllers.TruckController;
import Globals.Enums.LicenseTypes;
import Domain.Service.Objects.*;
import Globals.Enums.TruckModel;

public class TruckService {
    private final TruckController controller = new TruckController();

    /**
     * Calls for data from persistent to load into the business layer
     *
     * @return Result detailing success of operation
     */
    public Result<Object> loadData(){
        try {
            //TODO: implement controller.loadData();
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }


    public Result addTruck(int licenseNumber, TruckModel model, int netWeight, int maxCapacityWeight)
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
