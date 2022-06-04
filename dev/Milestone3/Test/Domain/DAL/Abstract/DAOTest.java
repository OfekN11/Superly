package Domain.DAL.Abstract;

import Domain.DAL.ConnectionHandler;
import Domain.DAL.Controllers.ShiftDataMappers.EveningShiftDAO;
import Globals.Enums.ShiftTypes;
import org.junit.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DAOTest {

    static EveningShiftDAO eveningShiftDataMapper = new EveningShiftDAO();
    static LocalDate localDate = LocalDate.parse("1998-07-25");
    static ShiftTypes morning =ShiftTypes.Morning;
    static String id = localDate.toString()+morning.toString();
    @BeforeClass
    public static void beforeAll() throws Exception {
        eveningShiftDataMapper.delete(id);
    }

    @AfterClass
    public static void afterAll() throws Exception {
        eveningShiftDataMapper.delete(id);
    }

    @Before
    public void before() throws Exception {
        eveningShiftDataMapper.insert(Arrays.asList(id,localDate,-1,10,10,10,10,10,10,10));
    }

    @After
    public void after() throws Exception {
        eveningShiftDataMapper.delete(id);
    }


    @Test
    public void testSelect1() {
        try (ConnectionHandler connection = eveningShiftDataMapper.getConnectionHandler()){
            insert();
            ResultSet resultSet = eveningShiftDataMapper.select(connection.get(), Arrays.asList(1),Arrays.asList(id));
            while (resultSet.next()) {
                assertEquals(localDate, resultSet.getDate(2).toLocalDate());
            }
        } catch (SQLException throwables) {
            fail();
        }
    }

    @Test
    public void testSelect2() {
        try (ConnectionHandler connection = eveningShiftDataMapper.getConnectionHandler()){
            ResultSet resultSet = eveningShiftDataMapper.select(connection.get(),Arrays.asList(2,3),Arrays.asList(1),Arrays.asList(id));
            while (resultSet.next()){
                assertEquals(localDate,resultSet.getDate(1).toLocalDate());
                assertEquals(ShiftTypes.Morning.toString(),resultSet.getString(2));
                assertThrows(SQLException.class,()->resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            fail();
        }
    }

    @Test
    public void insert() {
        try {
            eveningShiftDataMapper.remove(id);
            assertEquals(1, eveningShiftDataMapper.insert(Arrays.asList(id,localDate,-1,10,10,10,10,10,10,10)));
        } catch (SQLException throwables) {
            fail();
        }
    }

    @Test
    public void testRemove() throws SQLException {
        try{
            eveningShiftDataMapper.insert(Arrays.asList(id,localDate,-1,10,10,10,10,10,10,10));
        } catch (SQLException throwables) {

        }finally {
            assertEquals(1, eveningShiftDataMapper.remove(id));
        }
    }

    @Test
    public void update() {
        try (ConnectionHandler connection = eveningShiftDataMapper.getConnectionHandler()){
            assertEquals(1,eveningShiftDataMapper.update(Arrays.asList(4),Arrays.asList(9),Arrays.asList(1),Arrays.asList(id)));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }
}