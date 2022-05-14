package Domain.DAL.Controllers;

import Domain.DAL.Abstract.LinkDAO;

public class ConstraintsEmployeesLink extends LinkDAO<String> {
    public ConstraintsEmployeesLink() {
        super("ConstraintsEmployees");
    }
}
