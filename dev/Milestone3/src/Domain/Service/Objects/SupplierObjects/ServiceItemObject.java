package Domain.Service.Objects.SupplierObjects;

import java.util.HashMap;
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

    //Format : " id , name , manufacturer , pricePerUnit , quantity , percent , quantity , percent ..."
    public ServiceItemObject(String s){
        String[] fields = s.replaceAll("\\s+","").split(",");

        id = Integer.parseInt(fields[0]);
        name = fields[1];
        manufacturer = fields[2];
        pricePerUnit = Float.parseFloat(fields[3]);
        bulkPrices = new HashMap<>();

        for(int i=4; i<fields.length; i++){
            bulkPrices.put(Integer.parseInt(fields[i]), Integer.parseInt(fields[i+1]));
            i++;
        }
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

    public String toString(){
        if(bulkPrices.isEmpty()){
            return "\n" + id + ", " + name + ", " + manufacturer + ", " + pricePerUnit + ", [NO BULK PRICES]";
        }
        else{
            return "\n" + id + ", " + name + ", " + manufacturer + ", " + pricePerUnit + ", " + printBulkMap();
        }
    }

    private String printBulkMap(){
        String toReturn = "";

        for(Integer key : bulkPrices.keySet()){
            toReturn += ("quantity: " + key + ", " + " discount in percent: " + bulkPrices.get(key) + ", ");
        }

        if(toReturn.length() == 0)
            return toReturn;
        return toReturn.substring(0, toReturn.length()-2);
    }

    public void setId(int newID){
        id = newID;
    }
}
