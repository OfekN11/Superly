package Presentation.WebPresentation.Screens.ViewModels.Transport;

import Presentation.BackendController;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DocumentManagementMenu extends Screen {
    private static final String greet = "DocumentManagement Menu";

    public DocumentManagementMenu() {
        super(greet);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        printForm(resp,new String[] {"Document ID"},new String[]{"Document ID"},new String[]{"Transport Document Print", "Destination Document Print","Exit"});
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        if(isButtonPressed(req,"Transport Document Print")){
            try {
                int id = Integer.parseInt(req.getParameter("Document ID"));
                String info =controller.getTransportDocument(id).webDisplay();
                setError(info);
                PrintWriter out = resp.getWriter();
                out.println("<h2>");
                out.println(info);
                out.println("</h2>");
            }
            catch (NumberFormatException e){
                setError("please enter valid number");
            } catch (Exception e) {
                setError(e.getMessage());
            }
        }
        else{
            if(isButtonPressed(req,"Destination Document Print")){
                try {
                    int id = Integer.parseInt(req.getParameter("Document ID"));
                    String info =controller.getDestinationDocument(id).webDisplay();
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<h1>%s</h1>", info));
                }
                catch (NumberFormatException e){
                    setError("please enter valid number");
                } catch (Exception e) {
                    setError(e.getMessage());
                }
                redirect(resp,this.getClass());
            }
            else{
                if(isButtonPressed(req,"Exit")){
                    redirect(resp,TransportMainMenu.class);
                }
                else {
                    redirect(resp,this.getClass());
                }
            }
        }
    }
}
