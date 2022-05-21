package Domain.Service.Services;

import Domain.Business.Controllers.EmployeeController;
import Domain.Business.Controllers.ShiftController;
import Domain.Business.Controllers.SiteController;
import Domain.DAL.Controllers.EmployeeMappers.EmployeeDataMapper;
import Domain.DAL.Controllers.ShiftDataMappers.ShiftDataMapper;
import Domain.DAL.Controllers.TransportMudel.TransportDAO;
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
    static OrderService orderService = new OrderService();
    static SiteController siteController = new SiteController();
    static HashMap<Integer,Integer> productMap = new HashMap<>();
    static TransportDAO transportDataMapper = new TransportDAO();

    @BeforeClass
    public static void beforeAll() throws Exception {
        employeeDataMapper.delete("206618175");
        employeeDataMapper.delete("2061");
        employeeDataMapper.delete("1");
        productMap.put(1,5);
        productMap.put(2,200);
    }

    @AfterClass
    public static void afterAll() throws Exception {
        employeeDataMapper.delete("206618175");
        employeeDataMapper.delete("2061");
        employeeDataMapper.delete("1");
        shiftDataMapper.delete(date,ShiftTypes.Morning);
    }

    @Before
    public void before() throws Exception {
        employeeController.registerEmployee(JobTitles.Cashier,"206618175","ofek","d",10,date,certifications);
        employeeController.registerEmployee(JobTitles.Carrier,"1","ofek","d",10,date,certifications);
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
        orderService.addOrder(1,2,productMap);
    }
}
