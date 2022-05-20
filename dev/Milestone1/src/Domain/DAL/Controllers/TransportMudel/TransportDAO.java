package Domain.DAL.Controllers.TransportMudel;

import Domain.Business.Objects.Transport;
import Domain.DAL.Abstract.DAO;
import Globals.Enums.ShiftTypes;
import Globals.Enums.TransportStatus;
import Globals.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TransportDAO extends DAO {
    private final static Map<Integer, Transport> TRUCK_IDENTITY_MAP = new HashMap<>();

    private TransportSorceDAO transportSorceDTO;
    private TransportDestinationsDAO transportDestinationsDAO;
    private TransportTransportOrderDAO transportTransportOrderDAO;
    public TransportDAO() {
        super("Transports");
        transportSorceDTO =new TransportSorceDTO();
        transportDestinationsDAO =new TransportDestinationsDAO();
        transportTransportOrderDAO = new TransportTransportOrderDAO();
        try(Connection connection = getConnection()){
            ResultSet resultSet= select(connection);
            while (resultSet.next()){
                Integer id =resultSet.getInt(1);
                Transport transport= new Transport(resultSet.getInt(1),resultSet.getInt(2),
                        resultSet.getDate(3).toLocalDate(),resultSet.getDate(4).toString(),resultSet.getInt(5),
                        resultSet.getInt(6), TransportStatus.valueOf(resultSet.getString(7)),
                        new Pair<>(resultSet.getDate(8).toLocalDate(), ShiftTypes.valueOf(resultSet.getString(9))),
                        transportSorceDTO.get(id), transportDestinationsDAO.get(id),transportTransportOrderDAO.get(id));
                TRUCK_IDENTITY_MAP.put(transport.getSN(),transport);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("we could not load data from the db");
        }
    }

    public void save(Transport transport){
        if (!TRUCK_IDENTITY_MAP.containsKey(transport.getSN())){
            TRUCK_IDENTITY_MAP.put(transport.getSN(),transport);
        }

        try {
            super.remove(transport.getSN());
            transportSorceDTO.remove(transport.getSN());
            transportDestinationsDAO.remove(transport.getSN());
            transportTransportOrderDAO.remove(transport.getSN());

            super.insert(Arrays.asList(transport.getSN(),transport.getStartTime(),transport.getEndTime(),transport.getDriverID(),transport.getTruckNumber(),transport.getTruckWeight(),transport.getStatus(),transport.getShift().getLeft(),transport.getShift().getRight()));
            for(Integer id:transport.getSourcesID())
                transportSorceDTO.insert(Arrays.asList(transport.getSN()),id);

            for(Integer id:transport.getDestinationsID())
                transportDestinationsDAO.insert(Arrays.asList(transport.getSN()),id);

            for(Integer id:transport.getTransportOrders())
                transportTransportOrderDAO.insert(Arrays.asList(transport.getSN()),id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


}
