package SuperLee.ServiceLayer;

import java.util.Map;

public class ServiceItemObject {

    //fields
    private int id;
    private String name;
    private String manufacturer;
    private float pricePerUnit;
    private Map<Integer, Integer> bulkPrices; // <quantity, percent>


    public ServiceItemObject(int _id, String _name, String _manu, float _price, Map<Integer, Integer> _bulkPrices){
        id = _id;
        name = _name;
        manufacturer = _manu;
        pricePerUnit = _price;
        bulkPrices = _bulkPrices;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public float getPricePerUnit(){
        return pricePerUnit;
    }

    public Map<Integer, Integer> getBulkPrices(){
        return bulkPrices;
    }
}
