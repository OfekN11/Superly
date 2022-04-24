package SuperLee.BusinessLayer;

import java.util.Map;

public class AgreementItem {

    //fields
    private int id;
    private String name;
    private String manufacturer;
    private float pricePerUnit;
    //int quantity;
    private Map<Integer, Integer> bulkPrices; // <quantity, percent>

    //POSSIBLE FIELD
    private int idBySupplier = -1; // not yet decided if it's possible

    public AgreementItem(int _id, String _name, String _manu, float _price, Map<Integer, Integer> _bulkPrices){
        id = _id;
        name = _name;
        manufacturer = _manu;
        pricePerUnit = _price;
        //quantity = _quan;
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

    /*public int getQuantity(){
        return quantity;
    }*/

    public Map<Integer, Integer> getBulkPrices(){
        return bulkPrices;
    }

    public void setId(int _id){
        id = _id;
    }

    public void setName(String newName){
        name = newName;
    }

    // In case the manufacturer changed its name for some reason
    public void setManufacturer(String newManufacturer){
        manufacturer = newManufacturer;
    }

    public void setPrice(float newPrice){
        pricePerUnit = newPrice;
    }

    /*public void setQuantity(int _quantity) {
        this.quantity = _quantity;
    }*/

    public void setBulkPrices(Map<Integer, Integer> _bulk) throws Exception {
        NotNull.Check(_bulk);

        bulkPrices = _bulk;
    }

    public void addBulkPrice(int quantity, int discount) throws Exception {
        if(bulkPrices.containsKey(quantity)){
            throw new Exception("This quantity already exists!");
        }

        bulkPrices.put(quantity, discount);
    }

    public void removeBulkPrice(int quantity) throws Exception {
        if(!bulkPrices.containsKey(quantity)){
            throw new Exception("This quantity does not exits in the agreement!");
        }

        bulkPrices.remove(quantity);
    }

    public void editBulkPrice(int quantity, int discount) throws Exception {
        removeBulkPrice(quantity);
        addBulkPrice(quantity, discount);
    }

    public double calculateTotalPrice(int quantity){
        int closerQuantity = 0;
        double finalPrice;

        for(int q : bulkPrices.keySet()){
            if(q <= quantity && q > closerQuantity){
                closerQuantity = q;
            }
        }

        finalPrice = quantity*pricePerUnit;

        if(closerQuantity != 0){
            finalPrice *= (1-((double)bulkPrices.get(closerQuantity)/100));
        }

        return finalPrice;
    }


    //" id , name , manufacturer , pricePerUnit , quantity1 , percent1 , quantity2 , percent2 ...  "
    public String getInfoInStringFormat() {
        String result = "";
        result += String.valueOf(id) + ",";
        result += name + ",";
        result += manufacturer + ",";
        result += String.valueOf(pricePerUnit);
        for( Map.Entry<Integer, Integer> curr : bulkPrices.entrySet()){
            result += ",";
            result += String.valueOf(curr.getKey()) + ",";
            result += String.valueOf(curr.getValue());
        }
        return result;
    }
    //Format : " id , name , manufacturer , pricePerUnit , quantity , percent , quantity , percent ..."
    public String toString(){
        return "" + id + ", " + name + ", " + manufacturer + ", " + pricePerUnit + ", " + printBulkMap();
    }

    private String printBulkMap(){
        String toReturn = "";

        for(Integer key : bulkPrices.keySet()){
            toReturn += (key + ", " + bulkPrices.get(key) + ", ");
        }

        if(toReturn.length() == 0)
            return toReturn;
        return toReturn.substring(0, toReturn.length()-2);
    }

    public int getDiscount(int quantity) {
        return 4;
    }


}
