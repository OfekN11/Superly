package Presentation.WebPresentation.Screens.ViewModels.Transport.Transport.Update;

import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Objects.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class AddOrderToTransport extends Screen {
    private static final String greet = "Add order to transport";
    private int TransportId;
    public AddOrderToTransport() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        String val;
        if((val = getParamVal(req,"ID"))!=null){
             TransportId = Integer.parseInt(val);
        }
        printForm(resp,new String[] {"Order ID"},new String[] {"Order ID"},new String[]{"submit","Exit"});
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isButtonPressed(req,"submit")){
            String order = getParamVal(req,"Order ID");
            if(is_number(order)){
                try {
                    controller.addOrderToTransport(TransportId,Integer.parseInt(order));
                    setError("order added successfully");
                } catch (Exception e) {
                    setError(e.getMessage());
                }
            }
            refresh(req,resp,new String[]{"ID"},new String[]{getParamVal(req,"ID")});
        }
        else{
            if(isButtonPressed(req,"Exit")){
                redirect(resp,UpdateTransport.class);
            }
            refresh(req,resp,new String[]{"ID"},new String[]{getParamVal(req,"ID")});
        }
    }
    public boolean is_number(String id){
        try{
            int transportId= Integer.parseInt(id);
            return true;
        }
        catch (NumberFormatException e){
            setError("Please Enter a valid id");
            return false;
        }
    }
}
