package Backend.BusinessLayer.Controllers;

import java.util.HashMap;

public class OrderController {
    private static OrderController instance = null;

    private OrderController() {
    }

    public static OrderController getInstance(){
        if (instance == null) {
            instance = new OrderController();
        }
        return instance;
    }
}
