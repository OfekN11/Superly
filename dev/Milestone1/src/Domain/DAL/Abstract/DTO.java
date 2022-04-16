package Domain.DAL.Abstract;

public abstract class DTO {

    // properties
    private String id;
    protected boolean persist;
    protected String tableName; // this field will be valid if we will save data in tables
    protected String colID = "ID"; // if will be saved in tables, this field  representing the column ID in its table.


}