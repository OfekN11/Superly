package Domain.DAL.Abstract;

import Domain.DAL.ConnectionHandler;
import Globals.Enums.ShiftTypes;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DAOTest {

    ConstraintDAO constraintDataMapper = new ConstraintDAO();
    @Test
    public void testSelect1() {
        try (ConnectionHandler connection = constraintDataMapper.getConnectionHandler()){
            insert();
            ResultSet resultSet = constraintDataMapper.select(connection.get(), Arrays.asList(1,2),Arrays.asList(LocalDate.parse("1998-07-25"),ShiftTypes.Morning));
            while (resultSet.next()) {
                assertEquals(LocalDate.parse("1998-07-25"), resultSet.getDate(2).toLocalDate());
                if (!resultSet.getDate(1).toLocalDate().equals(LocalDate.parse("1998-07-25")) || !resultSet.getString(2).equals(ShiftTypes.Morning.toString()) || !resultSet.getString(3).equals("12"))
                    fail();
            }
            resultSet = constraintDataMapper.select(connection.get(),Arrays.asList(1,2),Arrays.asList(LocalDate.parse("1998-07-25"),ShiftTypes.Morning));
        } catch (SQLException throwables) {
            fail();
        }
    }

    @Test
    public void testSelect2() {
        try (ConnectionHandler connection = constraintDataMapper.getConnectionHandler()){
            ResultSet resultSet = constraintDataMapper.select(connection.get(),Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(LocalDate.parse("1998-07-25"),ShiftTypes.Morning));
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
    public void insert() {
        try {
            constraintDataMapper.remove(LocalDate.parse("1998-07-25").toString() + ShiftTypes.Morning.toString());
            assertEquals(1,constraintDataMapper.insert(Arrays.asList(LocalDate.parse("1998-07-25").toString()+ShiftTypes.Morning,LocalDate.parse("1998-07-25"),ShiftTypes.Morning)));
        } catch (SQLException throwables) {
            fail();
        }
    }

    @Test
    public void testRemove() throws SQLException {
        try{
            constraintDataMapper.insert(Arrays.asList(LocalDate.parse("1998-07-25"),ShiftTypes.Morning,"12"));
        } catch (SQLException throwables) {

        }finally {
            assertEquals(1,constraintDataMapper.remove(Arrays.asList(1,2,3),Arrays.asList(LocalDate.parse("1998-07-25"),ShiftTypes.Morning,"12")));
        }
    }

    @Test
    public void update() {
        insert();
        try (ConnectionHandler connection = constraintDataMapper.getConnectionHandler()){
            insert();
            constraintDataMapper.update(Arrays.asList(3),Arrays.asList(ShiftTypes.Evening),Arrays.asList(2),Arrays.asList(LocalDate.parse("1998-07-25")));
            ResultSet resultSet = constraintDataMapper.select(connection.get(),LocalDate.parse("1998-07-25").toString()+ShiftTypes.Morning);
            resultSet.next();
            assertEquals(ShiftTypes.Evening.toString(),resultSet.getString(3));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }
}