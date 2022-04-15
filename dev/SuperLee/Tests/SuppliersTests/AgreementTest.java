package SuppliersTests;

import SuperLee.BusinessLayer.Agreement.Agreement;
import SuperLee.BusinessLayer.Agreement.NotTransportingAgreement;
import SuperLee.BusinessLayer.AgreementItem;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AgreementTest {

    Agreement agreement;

    @BeforeEach
    public void setUp() throws Exception{
        agreement = new NotTransportingAgreement();
    }

    private List<AgreementItem> makeItemList(){
        List<AgreementItem> list = new ArrayList<>();

        list.add(new AgreementItem(1, "item1", "m1", 5.11f, new HashMap<>()));
        list.add(new AgreementItem(2, "item2", "m2", 7.11f, new HashMap<>()));
        list.add(new AgreementItem(3, "item3", "m3", 12.876f, new HashMap<>()));
        list.add(new AgreementItem(4, "item4", "m4", 184.2f, new HashMap<>()));
        list.add(new AgreementItem(5, "item5", "m5", 1123f, new HashMap<>()));
        list.add(new AgreementItem(6, "item6", "m6", 687248.45621f, new HashMap<>()));

        return list;
    }





}
