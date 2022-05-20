package Domain.DAL.Abstract;

import Globals.Enums.ShiftTypes;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import Domain.DAL.Controllers.ConstraintDAO;

import static org.junit.Assert.*;

public class DAOTest {

    ConstraintDAO constraintDataMapper = new ConstraintDAO();
    @Test
    void testSelect1() {
        try (Connection connection = constraintDataMapper.getConnection()){
            insert();
            ResultSet resultSet = constraintDataMapper.select(connection, Arrays.asList(1,2),Arrays.asList(LocalDate.parse("1998-07-25"),ShiftTypes.Morning));
            while (resultSet.next()) {
                assertEquals(LocalDate.parse("1998-07-25"), resultSet.getDate(1).toLocalDate());
                if (!resultSet.getDate(1).toLocalDate().equals(LocalDate.parse("1998-07-25")) || !resultSet.getString(2).equals(ShiftTypes.Morning.toString()) || !resultSet.getString(3).equals("12"))
                    fail();
            }
            resultSet = constraintDataMapper.select(connection,Arrays.asList(1,2),Arrays.asList(LocalDate.parse("1998-07-25"),ShiftTypes.Morning));
        } catch (SQLException throwables) {
            fail();
        }
    }

    @Test
    void testSelect2() {
        try (Connection connection = constraintDataMapper.getConnection()){
            ResultSet resultSet = constraintDataMapper.select(connection,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(LocalDate.parse("1998-07-25"),ShiftTypes.Morning));
            while (resultSet.next()){
                assertEquals(LocalDate.parse("1998-07-25"),resultSet.getDate(1).toLocalDate());
                assertEquals(ShiftTypes.Morning.toString(),resultSet.getString(2));
                assertThrows(SQLException.class,()->resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            fail();
        }
    }

    @Test
    void insert() {
        try {
            constraintDataMapper.remove(Arrays.asList(1,2,3),Arrays.asList(LocalDate.parse("1998-07-25"),ShiftTypes.Morning,"12"));
            assertEquals(1,constraintDataMapper.insert(Arrays.asList(LocalDate.parse("1998-07-25"),ShiftTypes.Morning,"12")));
        } catch (SQLException throwables) {
            fail();
        }
    }

    @Test
    void testRemove() throws SQLException {
        try{
            constraintDataMapper.insert(Arrays.asList(LocalDate.parse("1998-07-25"),ShiftTypes.Morning,"12"));
        } catch (SQLException throwables) {

        }finally {
            assertEquals(1,constraintDataMapper.remove(Arrays.asList(1,2,3),Arrays.asList(LocalDate.parse("1998-07-25"),ShiftTypes.Morning,"12")));
        }
    }

    @Test
    void update() {
        insert();
        try (Connection connection = constraintDataMapper.getConnection()){
            constraintDataMapper.remove(LocalDate.parse("1998-07-26"));
            constraintDataMapper.update(Arrays.asList(1),Arrays.asList(LocalDate.parse("1998-07-26")),Arrays.asList(2),Arrays.asList(ShiftTypes.Morning));
            ResultSet resultSet = constraintDataMapper.select(connection,LocalDate.parse("1998-07-26"));
            assertTrue(resultSet.next());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }
}