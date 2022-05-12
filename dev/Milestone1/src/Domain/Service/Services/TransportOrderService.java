package Domain.Service.Services;

import Domain.Business.Controllers.ShiftController;
import Domain.Business.Controllers.TransportOrderController;
import Domain.Service.Objects.Result;

public class TransportOrderService {
    private final TransportOrderController controller = new TransportOrderController();

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



}
