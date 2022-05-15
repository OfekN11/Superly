package Domain.DAL.Abstract;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class ObjectDateMapper<T> extends DataMapper {
    public ObjectDateMapper(String tableName) {
        super(tableName);
    }

    public T get(String id) throws Exception {
        Map<String,T> map = getMap();
        T output = map.get(id);
        if (output != null)
            return output;

        try(Connection connection = getConnection()){
            ResultSet instanceResult = select(connection, id);
            if (!instanceResult.next())
                return null;


            output = buildObject(instanceResult);
            map.put(id, output);
            return output;
        }
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
    public  <K> void updateProperty (String id, int propertyColumnNumber,K toUpdate) throws RuntimeException, SQLException {
       /* T instance = get(id); // throw exception if not found
        getUpdateFunction(propertyColumnNumber).update(instance,toUpdate); */
        super.update(Arrays.asList(propertyColumnNumber),Arrays.asList(toUpdate),Arrays.asList(1),Arrays.asList(id));
    }
   // protected abstract <K>UpdateFunction<T,K> getUpdateFunction(int propertyColumnNumber);

    public <K> void addToSet(String id, String listName, K toAdd) throws SQLException{
        getLinkDTO(listName).add(id,toAdd);
    }

    public <K> void removeFromSet(String id, String listName, K toRemove) throws SQLException{
        getLinkDTO(listName).remove(id,toRemove);
    }

    public <K> void replaceSet(String id, String listName, Set<K> toReplace) throws SQLException{
        getLinkDTO(listName).replaceSet(id,toReplace);
    }

    public void save(String id, T instance) throws SQLException{
        insert(instance);
        getMap().put(id,instance);
    }

    protected void removeFromIdentityMap(String id){
        getMap().remove(id);
    }
    public int delete(String id, T instance) throws SQLException {
        Set<LinkDAO> links = getAllLinkDTOs();

        for (LinkDAO link : links)
            link.remove(id);
        return remove(id);
    }

    public Set<T>getAll()throws SQLException{
        Set<T> output = new HashSet<>();
        try(Connection connection =getConnection()){
            ResultSet resultSet = super.select(connection);
            while (resultSet.next())
                output.add(buildObject(resultSet));
        }
        return output;
    }
    protected abstract Map<String, T> getMap();
    protected abstract  LinkDAO getLinkDTO(String setName);
    protected abstract T buildObject(ResultSet instanceResult) throws SQLException;
    public abstract void insert(T instance) throws SQLException;

    /**
     *
     * @return a set of all the linkDAOs that the objects holds
     */
    protected abstract Set<LinkDAO> getAllLinkDTOs();
}

