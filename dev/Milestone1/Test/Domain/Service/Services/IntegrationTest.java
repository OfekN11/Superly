package Domain.Service.Services;

import Domain.Business.Controllers.EmployeeController;
import Domain.Business.Controllers.ShiftController;
import Domain.Business.Controllers.SiteController;
import Domain.DAL.Controllers.EmployeeMappers.EmployeeDataMapper;
import Domain.DAL.Controllers.ShiftDataMappers.ShiftDataMapper;
import Domain.DAL.Controllers.TransportDataMapper;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;
import Globals.Enums.ShiftTypes;
import org.junit.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
public class IntegrationTest {
    static LocalDate date=LocalDate.parse("2021-06-19");
    static ShiftController shiftController = new ShiftController();
    static EmployeeController employeeController = new EmployeeController();
    static Set<Certifications> certifications = new HashSet<>();
    static EmployeeDataMapper employeeDataMapper =new EmployeeDataMapper();
    static ShiftDataMapper shiftDataMapper = new ShiftDataMapper();
    static TransportService transportService = new TransportService();
    static SiteController siteController = new SiteController();
    static HashMap<String,Integer> productMap = new HashMap<>();
    static TransportDataMapper transportDataMapper = new TransportDataMapper();

    @BeforeClass
    public static void beforeAll() throws Exception {
        employeeDataMapper.delete("206618175");
        employeeDataMapper.delete("2061");
        productMap.put("Snitzel",5);
        productMap.put("Roi'Homus",200);
    }

    @AfterClass
    public static void afterAll() throws Exception {
        employeeDataMapper.delete("206618175");
        employeeDataMapper.delete("2061");
        shiftDataMapper.delete(date,ShiftTypes.Morning);
    }

    @Before
    public void before() throws Exception {
        employeeController.registerEmployee(JobTitles.Cashier,"206618175","ofek","d",10,"con",date,certifications);
        employeeController.registerEmployee(JobTitles.Carrier,"1","ofek","d",10,"con",date,certifications);
        shiftController.createShift(date,ShiftTypes.Morning,"206618175",2,2,2,2,2,2,2);
    }

    @After
    public void after() throws Exception {
        employeeDataMapper.delete("206618175");
        employeeDataMapper.delete("2061");
        shiftDataMapper.delete(date, ShiftTypes.Morning);
    }
    @Test
    public void carrierInTransport() {
        // note that you need to check that the transport module has inserted those id and the hash map
        transportService.addTransportOrder(1,2,productMap);
    }
}
