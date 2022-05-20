package Domain.DAL.Controllers.TransportMudel;

import Domain.Business.Objects.TransportOrder;
import Domain.DAL.Abstract.DAO;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Abstract.DateMapper;
import Globals.Enums.OrderStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TransportOrderDAO extends DAO {
    private final static Map<Integer, TransportOrder> TRANSPORT_ORDER_MAP = new HashMap<>();

    public TransportOrderDAO() {
        super("Orders");
        try(Connection connection =getConnection()){
            TransportOrder order;
            ResultSet resultSet =select(connection);
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                if (!TRANSPORT_ORDER_MAP.containsKey(id)){
                    order = new TransportOrder(id,resultSet.getInt(2),resultSet.getInt(3), OrderStatus.valueOf(resultSet.getString(4)));
                    TRANSPORT_ORDER_MAP.put(id,order);
                }
                else {
                    order =TRANSPORT_ORDER_MAP.get(id);
                }
                order.getProductList().put(resultSet.getString(5),resultSet.getInt(6));
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    //Methods:
    public TransportOrder get(int licenseNumber) throws Exception {
        return TRANSPORT_ORDER_MAP.get(licenseNumber);
    }

    public void save(TransportOrder order){
        if (!TRANSPORT_ORDER_MAP.containsKey(order.getID()))
            TRANSPORT_ORDER_MAP.put(order.getID(),order);
        try {
            this.remove(order.getID());
            super.remove(order.getID());
            order.getProductList().forEach((k,v)-> {
                try {
                    super.insert(Arrays.asList(order.getID(),order.getSrc(),order.getDst(),order.getStatus(),k,v));
                } catch (SQLException throwables) {
                    throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
                }
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public void remove(int id){
        TRANSPORT_ORDER_MAP.remove(id);
        try {
            super.remove(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }
   }
