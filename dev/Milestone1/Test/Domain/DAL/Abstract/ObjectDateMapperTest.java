package Domain.DAL.Abstract;

import Domain.Business.Objects.Constraint;
import Domain.DAL.Controllers.ConstraintDataMapper;
import Domain.DAL.Controllers.ConstraintsEmployeesLink;
import Globals.Enums.ShiftTypes;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ObjectDateMapperTest {

    ConstraintDataMapper dataMapper = new ConstraintDataMapper();
    ConstraintsEmployeesLink link = new ConstraintsEmployeesLink();
    LocalDate birthday = LocalDate.parse("1998-07-25");
    ShiftTypes morning = ShiftTypes.Morning;
    ShiftTypes evening = ShiftTypes.Evening;
    String morningId = birthday.toString()+morning.toString();
    String eveningId = birthday.toString()+evening.toString();
    Constraint morningConstraint = new Constraint(birthday,morning,new HashSet<>());
    @Test
    public void get() {
        try {
            removeConstraint(morningConstraint);
            assertNull(dataMapper.get(morningId));
            dataMapper.save(morningId,morningConstraint);
            assertNotNull(dataMapper.get(morningId));
            assertNotNull(dataMapper.get(morningId));
        } catch (SQLException throwables) {
            fail();
        }
    }

    public void removeConstraint(Constraint constraint) throws SQLException {
        String id =constraint.getDate()+constraint.getType().toString();
        link.replaceSet(id,new HashSet<>());
        dataMapper.remove(id);
        dataMapper.removeFromIdentityMap(id);
    }

    public void saveConstraint(Constraint constraint) throws SQLException{
        constraint.getEmployees().add("12");
        String id =constraint.getDate()+constraint.getType().toString();
        dataMapper.save(id,constraint);
    }

    @Test
    public void updateProperty() {
        try {
            saveConstraint(morningConstraint);
            dataMapper.updateProperty(morningId,3,evening);
            dataMapper.updateProperty(morningId,1,eveningId);
            assertNotNull(dataMapper.get(eveningId));
            assertEquals(dataMapper.get(eveningId));
            removeConstraint(dataMapper.get(eveningId));
        } catch (SQLException throwables) {
            fail();
        }
    }

    @Test
    public void addToSet() {
    }

    @Test
    public void removeFromSet() {
    }

    @Test
    public void replaceSet() {
    }

    @Test
    public void save() {
        try {
            removeConstraint(morningConstraint);
            morningConstraint.getEmployees().add("12");
            dataMapper.save(morningId,morningConstraint);
            assertNotNull(dataMapper.get(morningId));
            assertTrue(link.get(morningId).size()>0);
        } catch (SQLException throwables) {
            fail();
        }
    }
}