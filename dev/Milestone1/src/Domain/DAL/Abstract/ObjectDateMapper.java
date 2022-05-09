package Domain.DAL.Abstract;

import Domain.Business.Objects.Carrier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ObjectDateMapper<T> extends DataMapper {
    public ObjectDateMapper(String tableName) {
        super(tableName);
    }

    public T get(String id){
        Map<String,T> map = getMap();
        T output = map.get(id);
        if (output != null)
            return output;

        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection,id);
            if (!instanceResult.next())
                return null;


            output = buildObject(instanceResult);
            map.put(id,output);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("No such Instance");
    }

    private List<Integer> generateIdNumberColumnsList(int size) {
        List<Integer> output = new LinkedList<>();
        for (int i=1;i<=size;i++)
            output.add(i);

        return output;
    }

    protected <K> void update (String id, int propertyColumnNumber,K toUpdate) throws RuntimeException, SQLException {
        T instance = get(id); // throw exception if not found
        getUpdateFunction(propertyColumnNumber).update(instance,toUpdate);
        super.update(Arrays.asList(propertyColumnNumber),Arrays.asList(toUpdate),Arrays.asList(1),Arrays.asList(id));
    }

    protected abstract Map<String,T> getMap();

    protected abstract T buildObject(ResultSet instanceResult) throws SQLException;

    protected abstract <K>UpdateFunction<T,K> getUpdateFunction(int propertyColumnNumber);

}

