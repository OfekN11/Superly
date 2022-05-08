package Domain.DAL.Abstract;

import java.util.Set;

public abstract class DTOControllers<T extends DTO> extends DataMapper{
    public DTOControllers(String tableName) {
        super(tableName);
    }
    public void persistAll(Set<T> toPersist){
        toPersist.forEach((t -> t.setPersist(true)));
    }
    public abstract void delete(T toDelete);
}
