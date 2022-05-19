package Domain.Service.Services;

import Domain.Business.Controllers.OrderController;
import Domain.Service.Objects.Result;

import java.util.HashMap;

public class OrderService {
    private OrderController order;
    public OrderService(){
        order = new OrderController();
    }
    public Result addOrder(int src,int dst,HashMap<Integer,Integer> product) {
        try {
            order.addTransportOrder(src,dst,product);
            return Result.makeOk(null);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }
    public Result getPendingOrders() {
        try {
            return Result.makeOk(order.getPendingOrder());
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

}
