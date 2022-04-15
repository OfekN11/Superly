package SuperLee.ServiceLayer;

import SuperLee.BusinessLayer.SupplierController;

import java.util.ArrayList;

public class SupplierService {

    private SupplierController controller;

    public SupplierService(){
        controller = new SupplierController();
    }



    public void addAgreement(){

        //Get all the things I need from another function maybe?
        //turning them into ServiceItemObject

        ServiceItemObject obj1 = new ServiceItemObject();
        ServiceItemObject obj2 = new ServiceItemObject();
        ArrayList<ServiceItemObject> list = new ArrayList<>();
        list.add(obj1);
        list.add(obj2);

        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for( ServiceItemObject item : list){
            ArrayList<String> temp = new ArrayList<>();

            // TODO: 14/04/2022 YONE
            //HOW CAN I SEND FROM SERVICE TO CONTROLLER WITHOUT OBJECTS

            //temp.add(item.getId());
            //temp.add(item.getName());
            //temp.add(item.getQuantity());
            //temp.add(item.getPricePerUnit());
            //temp.add(item.getBulkDiscount());   //BUT ITS HASHMAP!!!
            result.add(temp);
        }

        controller.addAgreement(1,result);
    }

}
