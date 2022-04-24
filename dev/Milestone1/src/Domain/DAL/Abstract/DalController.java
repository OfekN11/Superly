package Domain.DAL.Abstract;

import Domain.DAL.Objects.DConstraint;

import java.util.Set;

public abstract class DalController<T> {

    // properties
    protected String tableName; // this field will be valid if we will save data in tables

    // constructor
    public DalController(String tableName) {
        this.tableName = tableName;
    }

    // functions
    public abstract Set<T> loadData();
    public abstract void deleteAll();
}
