package Domain.DAL.Abstract;

import Domain.Business.Objects.Constraint;
import Domain.DAL.Controllers.ConstraintDataMapper;
import Domain.DAL.Controllers.ConstraintsEmployeesLink;
import Globals.Enums.ShiftTypes;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    Constraint eveningConstraint = new Constraint(birthday,ShiftTypes.Evening,new HashSet<>());

    @Test
    public void get() throws Exception {
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
    public void updateProperty() throws Exception {
        try {
            saveConstraint(morningConstraint);
            if (dataMapper.get(eveningId)!=null)
                removeConstraint(eveningConstraint);
            dataMapper.updateProperty(morningId,3,evening);
            dataMapper.updateProperty(morningId,1,eveningId);
            assertNotNull(dataMapper.get(eveningId));
            assertEquals(dataMapper.get(eveningId).getType(),ShiftTypes.Evening);
            removeConstraint(dataMapper.get(eveningId));
        } catch (SQLException throwables) {
            fail();
        }
    }

    @Test
    public void addToSet() {
        try {
            saveConstraint(morningConstraint);
            assertEquals(link.get(morningId).size(),1);
            dataMapper.addToSet(morningId,"employees","14");
            assertEquals(link.get(morningId).size(),2);
        } catch (SQLException throwables) {
            fail();
        }
    }

    @Test
    public void removeFromSet() {
        try {
            saveConstraint(morningConstraint);
            assertEquals(link.get(morningId).size(),1);
            dataMapper.removeFromSet(morningId,"employees","12");
            assertEquals(link.get(morningId).size(),0);
        } catch (SQLException throwables) {
            fail();
        }
    }

    @Test
    public void replaceSet() {
        try {
            saveConstraint(morningConstraint);
            assertEquals(link.get(morningId).size(),1);
            Set<String> newSet = new HashSet<>();
            newSet.add("14");
            dataMapper.replaceSet(morningId,"employees",newSet);
            assertEquals(link.get(morningId).size(),1);
            assertEquals(new ArrayList<>(link.get(morningId)).get(0),"14");
        } catch (SQLException throwables) {
            fail();
        }
    }

    @Test
    public void save() throws Exception {
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