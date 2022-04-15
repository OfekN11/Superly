package SuperLee.ServiceLayer;

import SuperLee.BusinessLayer.Pair;
import SuperLee.BusinessLayer.SupplierController;

import java.util.ArrayList;
import java.util.List;

public class SupplierService {

    private SupplierController controller;

    public SupplierService(){
        controller = new SupplierController();
    }



    public void addSupplier(int id, int bankNumber, String address, String name, String payingAgreement , ArrayList<Pair<String,String>> contacts){
        // TODO: 15/04/2022  Check here if phone number valid
        try {
            controller.addSupplier(id, name, bankNumber, address, payingAgreement, contacts );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addAgreement(int supplierId, int agreementType, String agreementDays){
        try {
            controller.addAgreement(supplierId, agreementType, agreementDays);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAgreementItems(int supplierId, List<String> itemsString) {
        try {
            controller.addItemsToAgreement(supplierId, itemsString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeAgreementType(int supplierId,  int agreementType, String agreementDays) {
        try {
            controller.updateAgreementType(supplierId, agreementType, agreementDays);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAgreement(int supplierId, int agreementType, String agreementDays){
        try {
            controller.setAgreement(supplierId, agreementType, agreementDays);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
