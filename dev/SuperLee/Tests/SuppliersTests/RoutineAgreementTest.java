package SuppliersTests;

import Domain.BusinessLayer.Supplier.Agreement.RoutineAgreement;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class RoutineAgreementTest {

    private RoutineAgreement agreement = new RoutineAgreement("1 2 3 4 5 6 7");;

    @BeforeEach
    public void setUp(){
        //agreement = new RoutineAgreement("1 2 3 4 5 6 7");
    }

    @Test
    public void test_isTransporting(){
        assertTrue(agreement.isTransporting());
    }

    @Test
    public void test_getDaysOfDelivery(){
        List<Integer> list = new ArrayList<>();
        list.add(1); list.add(2); list.add(3); list.add(4); list.add(5); list.add(6); list.add(7);

        assertEquals(list, agreement.getDaysOfDelivery());
    }

    @Test
    public void test_setDaysOfDelivery(){
        String s = "1 2 3 8";
        List<Integer> list = new ArrayList<>();
        list.add(1); list.add(2); list.add(3);

        try{
            agreement.setDaysOfDelivery(s);

            assertEquals(list, agreement.getDaysOfDelivery());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_addDaysOfDelivery(){
        String s = "5 6";
        List<Integer> list = new ArrayList<>();
        list.add(1); list.add(2); list.add(5); list.add(6);

        try{
            agreement.setDaysOfDelivery("1 2");

            agreement.addDaysOfDelivery(s);

            assertEquals(list, agreement.getDaysOfDelivery());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_removeDayOfDelivery(){
        agreement.removeDayOfDelivery(5);

        List<Integer> list = new ArrayList<>();
        list.add(1); list.add(2); list.add(3); list.add(4); list.add(6); list.add(7);

        assertEquals(list, agreement.getDaysOfDelivery());
    }
}
