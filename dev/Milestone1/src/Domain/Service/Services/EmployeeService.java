package Domain.Service.Services;

import Domain.Business.Controllers.EmployeeController;
import Domain.Service.Objects.*;
import Domain.Service.ServiceEmployeeFactory;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service controller for employee operations
 *
 * All EmployeeService's methods return Results detailing the success. encapsulating values/error messages
 */
public class EmployeeService {
    private final EmployeeController controller = new EmployeeController();
    private final ServiceEmployeeFactory factory = new ServiceEmployeeFactory();

    /**
     * Calls for data from persistent to load into the business layer
     *
     * @return Result detailing success of operation
     */
    public Result<Object> loadData(){
        try {
            controller.loadData();
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * Calls for employee data deletion
     *
     * @return Result detailing success of operation
     */
    public Result<Object> deleteData(){
        try {
            controller.deleteData();
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * Registers new employee
     *
     * @param title Type of the employee
     * @param id Employee's ID
     * @param name Employee's name
     * @param bankDetails Employee's bank details
     * @param salary Employee's salary
     * @param employmentConditions Employee's employment contract
     * @param startingDate Employee's starting date
     * @param certifications All of the employee's certifications
     * @return Result detailing process' success
     */
    public Result<Object> registerEmployee(JobTitles title, String  id, String name, String bankDetails, int salary, String employmentConditions, LocalDate startingDate, Set<Certifications> certifications){
        try {
            controller.registerEmployee(title, id, name, bankDetails, salary, employmentConditions, startingDate, certifications);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * Getter for employee
     *
     * @param id ID of the employee we want to get
     * @return Result holding requested employee of error message if failed
     */
    public Result<Employee> getEmployee(String id){
        try {
            return Result.makeOk(factory.createServiceEmployee(controller.getEmployee(id)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Edit's employee's name
     *
     * @param id Employee's ID
     * @param name Employee's new name
     * @return Result detailing process' success
     */
    public Result<Object> editEmployeeName(String id, String name){
        try {
            controller.editEmployeeName(id, name);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editEmployeeBankDetails(String id, String bankDetails){
        try {
            controller.editEmployeeBankDetails(id, bankDetails);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editEmployeeCertifications(String id, Set<Certifications> certifications){
        try {
            controller.editEmployeeCertifications(id, certifications);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editCarrierLicenses(String id, Set<String> Licences){
        try {
            controller.editCarrierLicenses(id, Licences);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> removeEmployee(String id){
        try {
            controller.removeEmployee(id);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Set<Employee>> getAllEmployees() {
        try {
            return Result.makeOk(controller.getAllEmployees().stream().map((factory::createServiceEmployee)).collect(Collectors.toSet()));
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Object> checkUnusedEmployeeID(String id) {
        try {
            controller.checkUnusedEmployeeID(id);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> validateID(String id) {
        try {
            controller.validateID(id);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> validateIDs(Set<String> ids) {
        try {
            controller.validateIDs(ids);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editEmployeeSalary(String id, int newSalary) {
        try {
            controller.editEmployeeSalary(id,newSalary);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Set<Employee>> getEmployees(Set<String> employeeIDs) {
        try {
            return Result.makeOk(controller.getEmployees(employeeIDs).stream().map(factory::createServiceEmployee).collect(Collectors.toSet()));
        }catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<String> getEmploymentConditionsOf(String id) {
        try {
            return Result.makeOk(controller.getEmploymentConditionsOf(id));
        }catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }
}
