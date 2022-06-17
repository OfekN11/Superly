package Presentation.WebPresentation.Screens.ViewModels.Transport.Transport.Update;

import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Objects.Order;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Objects.Transport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ViewPendingOrders extends Screen {
    private static final String greet = "View Pending Orders";
    public ViewPendingOrders() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        String val;
        if((val = getParamVal(req,"ID"))!=null){
            int TransportId = Integer.parseInt(val);
            try {
                Set<Order> pending = controller.getPendingOrders();
                printTransport(pending,resp);
            }
            catch (Exception e) {
                setError("failure");
            }
        }
        printMenu(resp,new String[]{"Exit"});
    }
    public List<String> split(String s){
        List<String> l = new ArrayList<>();
        while (s.indexOf("/")!=-1){
            int index = s.indexOf("/");
            l.add(s.substring(0,index));
            s=s.substring(index+1);
        }
        return l;
    }
    public void printTransport(Set<Order> lst, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        if (lst.size()==0){
            out.println("<h2>" + "There is no orders in this status" + "</h2>");
        }
        List<String> all = new ArrayList<>();
        for (Order o: lst){
            all.add(o.displayWeb());
        }
        for(String s1 : all){
            for(String s2 :split(s1)){
                out.println("<h4>" + s2 + "</h4>");
            }
            out.println("<br>");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(getIndexOfButtonPressed(req) == 0){
            redirect(resp,UpdateTransport.class);
        }
    }
}
