package Domain.DAL.Abstract;

import Domain.Business.Objects.Carrier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ObjectDateMapper<T> extends DataMapper {
    public ObjectDateMapper(String tableName) {
        super(tableName);
    }

    public T get(List<Integer> columnIdNumber,List<Object> idValues){
        String id = generateId(idValues);
        Map<String,T> map = getMap();
        T output = map.get(id);
        if (output != null)
            return output;

        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection,columnIdNumber,idValues);
            if (!instanceResult.next())
                return null;


            output = buildObject(instanceResult);
            map.put(id,output);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("something went wrong in ObjectDateMapper");
    }

    protected void update(List<Integer> columnIdNumber,List<Object> idValues)


    private String generateId(List<Object> idValues) {
        return idValues.stream().map(Object::toString).collect(Collectors.joining(""));
    }

    protected abstract Map<String,T> getMap();

    protected abstract T buildObject(ResultSet instanceResult) throws SQLException;

}

