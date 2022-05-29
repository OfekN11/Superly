package Domain.Service.Services;

import Domain.Business.Controllers.OrderController;
import Domain.Service.Objects.TransportOrder;
import Domain.Service.Objects.Result;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Domain.Service.Objects.TransportOrder> toTOService(List<Domain.Business.Objects.TransportOrder> orders)
    {
        Set<Domain.Service.Objects.TransportOrder> orderSet = new HashSet<>();
        for (Domain.Business.Objects.TransportOrder order: orders) {
            orderSet.add(new TransportOrder(order));
        }
        return orderSet;
    }
    public Result getPendingOrders() {
        try {
            return Result.makeOk(toTOService(order.getPendingOrder()));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

}
