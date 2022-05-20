package Domain.DAL.Controllers.TransportMudel;

import Domain.Business.Objects.Site.Address;
import Domain.Business.Objects.Site.Destination;
import Domain.DAL.Abstract.DAO;
import Globals.Enums.ShippingAreas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DestinationsDAO extends DAO {

    private static Map<Integer, Destination> DESTINATION_IDENTITY_MAP = new HashMap<>();
    public DestinationsDAO() {
        super("Destinations");
        try(Connection connection = getConnection()){
            ResultSet resultSet= select(connection);
            while (resultSet.next()){
                Destination destination= new Destination(resultSet.getInt(1),new Address(ShippingAreas.valueOf(resultSet.getString(5)),resultSet.getString(4)),resultSet.getString(2),resultSet.getString(3));
                DESTINATION_IDENTITY_MAP.put(destination.getId(),destination);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("we could not load data from the db");
        }
    }
    public Destination get(int id){
        DESTINATION_IDENTITY_MAP.get(id);
    }

}
