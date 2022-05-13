package Domain.PersistenceLayer.Controllers;

import Domain.PersistenceLayer.Abstract.DAO;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RoutineDAO extends DAO {

    public RoutineDAO() {
        super("Routine");
    }


    public void addDaysOfDelivery(int supplierId, String days) {
        List<Integer> daysOfDelivery = daysStringToDay(days);
        for(Integer currDay : daysOfDelivery){
            try {
                insert(Arrays.asList(supplierId, currDay));
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        }
    }




    private List<Integer> daysStringToDay(String days){
        List<Integer> list = new LinkedList<>();
        int d = 0;

        for(int i=0; i<days.length(); i++){

            while(i<days.length() && days.charAt(i) == ' '){
                i++;
            }

            d = days.charAt(i)-'0';

            // if the given number is 0 or more than 7 we ignore it
            if(d>=1 && d<=7){
                list.add(d);
            }

            i++;
        }
        Collections.sort(list);
        return list;
    }
}
