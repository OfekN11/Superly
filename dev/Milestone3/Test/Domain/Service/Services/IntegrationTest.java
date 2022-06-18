package Domain.Service.Services;

import Domain.Business.Controllers.HR.EmployeeController;
import Domain.Business.Controllers.HR.ShiftController;
import Domain.Business.Controllers.Transport.SiteController;
import Domain.DAL.Controllers.EmployeeMappers.EmployeeDataMapper;
import Domain.DAL.Controllers.ShiftDataMappers.ShiftDataMapper;
import Domain.DAL.Controllers.TransportMudel.TransportDAO;
import Domain.Service.Services.HR.EmployeeService;
import Domain.Service.Services.HR.ShiftService;
import Domain.Service.Services.Transport.DocumentService;
import Domain.Service.Services.Transport.OrderService;
import Domain.Service.Services.Transport.TransportService;
import Domain.Service.Services.Transport.TruckService;
import Globals.Enums.Certifications;
import org.junit.*;

import java.time.LocalDate;
import java.util.*;

public class IntegrationTest {
    static LocalDate date=LocalDate.parse("2021-09-19");
    static EmployeeService employeeService = new EmployeeService();
    static ShiftService shiftService = new ShiftService();
    static DocumentService documentService = new DocumentService();
    static OrderService orderService = new OrderService();
    static TransportService transportService = new TransportService();
    static TruckService truckService =new TruckService();
    static InventoryService inventoryService = new InventoryService();
    static SupplierService supplierService = new SupplierService();
    static HashMap<Integer,Integer> productMap = new HashMap<>();

    @Test
    public void carrierInTransport() {
        // note that you need to check that the transport module has inserted those id and the hash map
        orderService.addOrder(1,2,productMap);
    }

    @Test
    public void IssuingAnOrder() {

    }
}
