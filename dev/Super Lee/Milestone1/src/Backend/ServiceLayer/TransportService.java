package Backend.ServiceLayer;

import Backend.BusinessLayer.Controllers.TransportController;

public class TransportService {
    private TransportController tc;

    public TransportService() {
        this.tc = new TransportController();
    }

}
