package Domain.Service.Services;

import Domain.Business.Controllers.EmployeeController;
import Domain.Business.Controllers.ShiftController;
import Domain.Business.Controllers.SiteController;
import Domain.Business.Objects.Employee.*;
import Domain.DAL.Controllers.EmployeeMappers.EmployeeDataMapper;
import Domain.DAL.Controllers.ShiftDataMappers.ShiftDataMapper;
import Domain.DAL.Controllers.TransportMudel.TransportDAO;
import Domain.Service.Shift.EveningShift;
import Domain.Service.Shift.MorningShift;
import Domain.Service.Shift.Shift;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;
import Globals.Enums.LicenseTypes;
import Globals.Enums.ShiftTypes;
import org.junit.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntegrationTest {
    static LocalDate date=LocalDate.parse("2021-09-19");
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

    @Test
    public void carrierInTransport() {
        // note that you need to check that the transport module has inserted those id and the hash map
        orderService.addOrder(1,2,productMap);
    }




}
