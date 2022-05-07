package Domain.BusinessLayer;

import Domain.BusinessLayer.Supplier.Supplier;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SuppliersDAO {


    private HashMap<Integer , Supplier> suppliers;

    public SuppliersDAO(){
        this.suppliers = new HashMap<>();
    }

    public Supplier getSupplier(int id){
        return suppliers.get(id);
    }

    public ArrayList<Supplier> getAllSuppliers(){
        ArrayList<Supplier> result = new ArrayList<>();
        for(Map.Entry<Integer, Supplier> curr : suppliers.entrySet()){
            result.add(curr.getValue());
        }
        return result;
    }

    public void addSupplier(Supplier supplier){
        suppliers.put(supplier.getId(), supplier);
    }

    public void removeSupplier(int id){
        suppliers.remove(id);
    }

    public boolean supplierExist(int id){
        return suppliers.containsKey(id);
    }

    public boolean isEmpty() {
        return suppliers.isEmpty();
    }
}
