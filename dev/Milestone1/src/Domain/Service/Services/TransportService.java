package Domain.Service.Services;

import Domain.Business.Controllers.TransportController;
import Globals.Enums.ShippingAreas;
import Domain.Service.Objects.*;

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
    private HashMap<Integer, Transport> toServiceTransports(HashMap<Integer, Domain.Business.Objects.Transport> transports)
    {
        HashMap<Integer, Transport> transportList = new HashMap<>();
        for (Integer transportKey: transports.keySet()) {
            transportList.put(transportKey, new Transport(transports.get(transportKey)));
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

    /*public Result getWaitingTransports() {
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
    }*/

    public Result getTransportOrders() {
        try {
            //Todo: casting to service object
            return Result.makeOk(controller.getTransportOrders());
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /*public Result getTransportOrders(ShippingAreas areas) {
        try {
            //Todo: casting to service object
            return Result.makeOk(controller.getTransportOrders(areas));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }*/

    public Result startTransport(int transportSN) {
        try {
            controller.startTransport(transportSN);
            return Result.makeOk(null);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result getRedesignTransports() {
        //TODO:
        return null;
    }

    public Result placeDriver(int transportSN, String driverName) {
        try {
            controller.placeDriver(transportSN, driverName);
            return Result.makeOk(null);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result placeTruck(int transportSN, int truckLN) {
        try {
            controller.placeTruck(transportSN, truckLN);
            return Result.makeOk(null);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }
}
