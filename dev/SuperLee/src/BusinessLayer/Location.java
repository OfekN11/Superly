package BusinessLayer;

public class Location {
    private int storeID;
    private boolean inWarehouse;
    private int shelf;

    public Location(int storeID, boolean inWarehouse, int shelf) {
        this.storeID = storeID;
        this.inWarehouse = inWarehouse;
        this.shelf = shelf;
    }

    public int getStoreID() {
        return storeID;
    }
    public boolean getInWarehouse() {
        return inWarehouse;
    }
    public int getShelf() {
        return shelf;
    }

    public void move(int storeID, boolean inWarehouse, int shelf) {
        this.storeID = storeID;
        this.inWarehouse = inWarehouse;
        this.shelf = shelf;
    }
}
