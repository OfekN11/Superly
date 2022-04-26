package Backend.ServiceLayer;

import Backend.BusinessLayer.Controllers.TransportController;
import Backend.Globals.Enums.ShippingAreas;
import Backend.ServiceLayer.Objects.Transport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransportService {
    private TransportController controller;

    public TransportService() {
        this.controller = new TransportController();
    }

    public Result addTransportOrder(int srcID, int dstID, HashMap<String, Integer> productList) {
        try {
            controller.addTransportOrder(srcID, dstID, productList);
            return Result.makeOk(null);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }
    private List<Transport> toServiceTransports(List<Backend.BusinessLayer.Objects.Transport> transports)
    {
        List<Transport> transportList = new ArrayList<>();
        for (Backend.BusinessLayer.Objects.Transport transport: transports) {
            transportList.add(new Transport(transport));
        }
        return transportList;
    }
    public Result getInProgressTransports() {
        try {
            return Result.makeOk(toServiceTransports(controller.getInProgressTransports()));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result getWaitingTransports() {
        try {
            return Result.makeOk(toServiceTransports(controller.getWaitingTransports()));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result getPastTransports() {
        try {
            return Result.makeOk(toServiceTransports(controller.getPastTransports()));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result getTransportOrders() {
        try {
            //Todo: casting to service object
            return Result.makeOk(controller.getTransportOrders());
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result getTransportOrders(ShippingAreas areas) {
        try {
            //Todo: casting to service object
            return Result.makeOk(controller.getTransportOrders(areas));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result startTransport(int transportSN) {
        //TODO
        return  null;
    }

    public Result getRedesignTransports() {
        //TODO:
        return null;
    }
}
