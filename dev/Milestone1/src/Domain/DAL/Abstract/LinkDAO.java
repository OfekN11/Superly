package Domain.DAL.Abstract;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
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

    protected void replaceAll(String mainObjectId ,List<T> newValues) throws SQLException {
        remove(mainObjectId);
        for(T instance : newValues)
            insert(Arrays.asList(mainObjectId,instance));
    }

    protected void addRow(String id, T instance) throws SQLException {
        insert(Arrays.asList(id,instance));
    }

    protected void removeInstance(String mainObjectId, T secondInstanceID) throws SQLException{
        remove(Arrays.asList(1,2),Arrays.asList(mainObjectId,secondInstanceID));
    }


    protected abstract T buildObject(ResultSet resultSet);
}
