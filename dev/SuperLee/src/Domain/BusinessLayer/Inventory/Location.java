package Domain.BusinessLayer.Inventory;

import java.util.List;

public class Location {
    private int locationID;
    private int storeID;
    private boolean inWarehouse;
    private List<Integer> shelves;

    public Location(int locationID, int storeID, boolean inWarehouse, List<Integer> shelves) {
        this.locationID=locationID;
        this.storeID = storeID;
        this.inWarehouse = inWarehouse;
        this.shelves = shelves;
    }

    public int getLocationID() {
        return locationID;
    }
    public int getStoreID() {
        return storeID;
    }
    public boolean getInWarehouse() { return inWarehouse; }
    public List<Integer> getShelves() { return shelves; }
}
