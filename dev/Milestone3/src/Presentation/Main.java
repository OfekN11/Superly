package Presentation;

import Domain.Business.Controllers.SupplierController;
import Domain.Service.Objects.SupplierObjects.ServiceOrderObject;
import Domain.Service.Services.SupplierService;
import Domain.Service.util.Result;
import Presentation.Screens.MainMenu;

public class Main {

    public static void main(String[] args) {
        //new Thread(new MainMenu(new BackendController())).start();


        SupplierService supplierService = new SupplierService();
        Result<ServiceOrderObject>  res = supplierService.getOrder(1,1);
        System.out.println(res.getValue().toString());
    }



}
