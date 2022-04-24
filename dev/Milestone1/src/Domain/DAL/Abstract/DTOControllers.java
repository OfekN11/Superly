package Domain.DAL.Abstract;

import java.util.Set;

public abstract class DTOControllers<T extends DTO> extends DalController<T> {
    public DTOControllers(String tableName) {
        super(tableName);
    }
    public void persistAll(Set<T> toPersist){
        toPersist.forEach((t -> t.setPersist(true)));
    }
}
