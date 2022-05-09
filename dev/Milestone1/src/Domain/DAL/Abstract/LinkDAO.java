package Domain.DAL.Abstract;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class LinkDAO<T> extends DataMapper{
    public LinkDAO(String tableName) {
        super(tableName);
    }

    protected Set<T> get(List<Integer> columnIdNumber, List<Object> values) throws SQLException {
        Set<T> output = new HashSet<>();
        try(Connection connection = getConnection()){
            ResultSet resultSet = select(connection, columnIdNumber,values);
            while (resultSet.next())
                output.add(buildObject(resultSet));
        }
        return output;
    }

    protected void replaceAll(String id ,List<T>)


    protected abstract T buildObject(ResultSet resultSet);


}
