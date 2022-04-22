package Presentation;

import Domain.Service.*;

public class BackendController {
    private final EmployeeService employeeService;
    private final ShiftService shiftService;

    public BackendController(){
        employeeService = new EmployeeService();
        shiftService = new ShiftService();
    }

    public BackendController(EmployeeService employeeService, ShiftService shiftService){
        this.employeeService = employeeService;
        this.shiftService = shiftService;
    }


}
