package Domain.Service.Services;

import Domain.Business.Controllers.TransportController;
import Globals.Enums.ShiftTypes;
import Globals.Enums.ShippingAreas;
import Domain.Service.Objects.*;
import Globals.Pair;
import Presentation.Objects.Transport.TransportOrder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransportService {
    private TransportController controller;

    public TransportService() {
        this.controller = new TransportController();
    }

    //TODO fix the function
    private HashMap<Integer, Transport> toServiceTransports(HashMap<Integer, Domain.Business.Objects.Transport> transports)
    {
        HashMap<Integer, Transport> transportList = new HashMap<>();
        for (Integer transportKey: transports.keySet()) {
            transportList.put(transportKey, new Transport(transports.get(transportKey)));
        }
        return transportList;
    }
    //TODO still not fixed in the business
    public Result getInProgressTransports() {
        try {
            return Result.makeOk(toServiceTransports(controller.getInProgressTransports()));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }
    //TODO still not fixed in the business
    public Result getPaddingTransport(){
        try {
            return Result.makeOk(toServiceTransports(controller.getPendingTransports()));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }
    //TODO still not fixed in the business
    public Result getCompletedTransport(){
        try {
            return Result.makeOk(toServiceTransports(controller.getCompletedTransports()));
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
    }

    public Result getTransportOrders() {
        try {
            //Todo: casting to service object
            return Result.makeOk(controller.getTransportOrders());
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }*/

    /*public Result getTransportOrders(ShippingAreas areas) {
        try {
            //Todo: casting to service object
            return Result.makeOk(controller.getTransportOrders(areas));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result getRedesignTransports() {
        //TODO:
        return null;
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

    public Result placeDriver(int transportSN, String driverID) {
        try {
            controller.placeDriver(transportSN, driverID);
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
    public Result createTransport (Pair<LocalDate, ShiftTypes> shift){
        try {
            controller.createTransport(shift);
            return Result.makeOk(null);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }
    public Result addOrderToTransport(int transportSN,int orderId){
        try {
            controller.addOrderToTransport(transportSN,orderId);
            return Result.makeOk(null);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }
    public Result EndTransport(int transportSN){
        try {
            controller.endTransport(transportSN);
            return Result.makeOk(null);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }
    public Result advanceSite(int transportSN,int siteID){
        try {
            controller.advanceSite(transportSN,siteID);
            return Result.makeOk(null);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result addTransportOrder(int srcID, int dstID, HashMap<Integer, Integer> productList) {
        //TODO: Implement
        return null;
    }
}
