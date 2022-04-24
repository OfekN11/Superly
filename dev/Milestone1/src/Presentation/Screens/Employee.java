package Presentation.Screens;

public abstract class Employee extends Screen{
    public Employee(Screen caller, Domain.Service.Objects.Employee sEmployee) {
        super(caller, menuOptions);
    }
}
