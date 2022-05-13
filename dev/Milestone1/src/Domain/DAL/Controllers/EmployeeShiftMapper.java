package Domain.DAL.Controllers;

import Domain.DAL.Abstract.DataMapper;
import Domain.DAL.Objects.DShift;
import Globals.Enums.ShiftTypes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EmployeeShiftMapper extends DataMapper {

    // properties
    public EmployeeShiftMapper() {
        super("ShiftsEmployees");
    }

    public void updateSetOfEmployees(LocalDate shiftDate,ShiftTypes type, Set<String> newEmployeeIds) throws SQLException {
        remove(Arrays.asList(1,2),Arrays.asList(shiftDate,type));
        for(String eId: newEmployeeIds)
            insert(Arrays.asList(shiftDate,type,eId));
    }

    public Set<String> getEmployeesOfShift(LocalDate shiftDate,ShiftTypes type) throws SQLException {
        Set<String> output = new HashSet<>();
        try(Connection connection = getConnection()){
            ResultSet resultSet = select(connection,Arrays.asList(1,2),Arrays.asList(shiftDate,type));
            while (resultSet.next())
                output.add(resultSet.getString(3));
        }
        return output;
    }
}
