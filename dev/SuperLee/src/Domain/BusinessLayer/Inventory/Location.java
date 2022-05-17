package Domain.BusinessLayer.Inventory;

import Domain.PersistenceLayer.Controllers.LocationDataMapper;
import Domain.PersistenceLayer.Controllers.ProductDataMapper;

import java.util.List;

public class Location {
    private int locationID;
    private int storeID;
    private boolean inWarehouse;
    private List<Integer> shelves;
    public static final LocationDataMapper LOCATION_DATA_MAPPER = new LocationDataMapper();

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
