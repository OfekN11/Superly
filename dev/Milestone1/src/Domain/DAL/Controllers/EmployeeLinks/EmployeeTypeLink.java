package Domain.DAL.Controllers.EmployeeLinks;

import Domain.DAL.Abstract.LinkDAO;

public class EmployeeTypeLink extends LinkDAO<String> {
    public EmployeeTypeLink() {
        super("EmployeesType");
    }
}
