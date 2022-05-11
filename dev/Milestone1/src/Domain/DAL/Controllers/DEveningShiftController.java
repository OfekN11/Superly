package Domain.DAL.Controllers;

import Domain.Business.Objects.EveningShift;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Abstract.ObjectDateMapper;
import Domain.DAL.Objects.DEveningShift;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DEveningShiftController extends ObjectDateMapper<EveningShift> {
    public DEveningShiftController() {
        super("Place holder");
    }

    @Override
    protected Map<String, EveningShift> getMap() {
        return null;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        return null;
    }

    @Override
    protected EveningShift buildObject(ResultSet instanceResult) throws Exception {
        return null;
    }

    @Override
    public void insert(EveningShift instance) throws SQLException {

    }
}
