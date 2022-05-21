package Presentation;

import Domain.Business.Objects.Employee.Carrier;
import Domain.DAL.Controllers.EmployeeMappers.EmployeeDataMapper;
import Presentation.Screens.MainMenu;

public class Main {
    public static void main(String[] args) {
        new Thread(new MainMenu(new BackendController())).start();

     /*   EmployeeDataMapper employeeDataMapper = new EmployeeDataMapper();
        try {
            for (Carrier carrier : employeeDataMapper.getCarrier())
                System.out.println(carrier.getId() + " - " + carrier.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }
}
