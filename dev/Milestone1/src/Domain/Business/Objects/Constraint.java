package Domain.Business.Objects;
import java.util.Date;
public class Constraint {

    // properties
    private Date date;
    private String type; // morning evening

    public Constraint(Date date, String type) {
        this.date = date;
        this.type = type;
    }
}
