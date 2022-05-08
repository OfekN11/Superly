package Domain.DAL.Abstract;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class LinkDTO<T> extends DataMapper{
    public LinkDTO(String tableName) {
        super(tableName);
    }

    public Set<T> getLicensesOfCarrier(List<Integer> columnIdNumber, List<Object> values) throws SQLException {
        Set<T> output = new HashSet<>();
        try(Connection connection = getConnection()){
            ResultSet resultSet = select(connection, columnIdNumber,values);
            while (resultSet.next())
                output.add(buildObject(resultSet));
        }
        return output;
    }

    protected abstract T buildObject(ResultSet resultSet);


}
