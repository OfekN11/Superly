package BusinessLayer;

import java.util.List;

public class Location {
    private int storeID;
    private boolean inWarehouse;
    private List<Integer> shelves;

    public Location(int storeID, boolean inWarehouse, List<Integer> shelves) {
        this.storeID = storeID;
        this.inWarehouse = inWarehouse;
        this.shelves = shelves;
    }

    public int getStoreID() {
        return storeID;
    }


}
