package Domain.DAL.Abstract;

import java.util.Set;

public abstract class DalController<T extends DTO> {

    // properties
    protected String tableName; // this field will be valid if we will save data in tables

    // constructor
    public DalController(String tableName) {
        this.tableName = tableName;
    }

    // functions
    public abstract Set<T> createFakeDTOs();
    public abstract Set<T> select();
    public abstract void deleteAll();
}
