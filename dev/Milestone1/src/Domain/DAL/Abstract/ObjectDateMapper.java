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
        throw new RuntimeException("this is bad");
    }

    /**
     *
     * @param id the id of instance you want to update
     * @param propertyColumnNumber the columnNumber you want to update
     * @param toUpdate the value to update
     * @param <K> whatever
     * @throws RuntimeException
     * @throws SQLException
     */
    protected <K> void updateProperty (String id, int propertyColumnNumber,K toUpdate) throws RuntimeException, SQLException {
       /* T instance = get(id); // throw exception if not found
        getUpdateFunction(propertyColumnNumber).update(instance,toUpdate); */
        super.update(Arrays.asList(propertyColumnNumber),Arrays.asList(toUpdate),Arrays.asList(1),Arrays.asList(id));
    }
   // protected abstract <K>UpdateFunction<T,K> getUpdateFunction(int propertyColumnNumber);

    protected abstract <K> void addToList(String id, String listName,K toAdd);
    protected abstract <K> void removeFromList(String id, String listName,K toRemove);
    protected abstract <K> void replaceList(String id, String listName,K toRemove);
    protected abstract Map<String,T> getMap();
    protected abstract T buildObject(ResultSet instanceResult) throws SQLException;


}

