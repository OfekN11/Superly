package Domain.DAL.Controllers;

import Domain.Business.Objects.Truck;
import Globals.Enums.TruckModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TruckDataMapTest {
    private TruckDataMap tdm;
    private Truck t;
    @Before
    public void setUp() throws Exception {
        tdm = new TruckDataMap();
        t = new Truck(123, TruckModel.A, 10, 30);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void get() {

        Assert.assertNull(tdm.get(123));
        tdm.save(t);
        Assert.assertNotNull(tdm.get(123));
    }

    @Test
    public void save() {
        Assert.assertNull(tdm.get(123));
        tdm.save(t);
        Assert.assertNotNull(tdm.get(123));
    }

    @Test
    public void update() {
        tdm.save(t);
        Assert.assertEquals(TruckModel.A, tdm.get(123).getModel());
        t.setModel(TruckModel.B);
        tdm.update(t);
        Assert.assertEquals(TruckModel.B, tdm.get(123).getModel());
    }
}