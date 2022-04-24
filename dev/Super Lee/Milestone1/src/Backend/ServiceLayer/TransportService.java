package Backend.ServiceLayer;

import Backend.BusinessLayer.Controllers.TransportController;
import Frontend.Objects.TransportOrder;

import java.util.HashMap;

public class TransportService {
    private TransportController tc;

    public TransportService() {
        this.tc = new TransportController();
    }

    public void addTransportOrder(int srcID, int dstID, HashMap<String, Integer> productList) {
        tc.addTransportOrder(srcID, dstID, productList);
    }
}
