package Presentation.WebPresentation.Screens.ViewModels.Transport.Transport;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateTransport extends Screen {
    private static final String greet = "Transport Management Menu";
    private static final String[] forumOptions = {
            "Place truck",                  //1
            "Place carrier",                //2
            "Start transport",              //3
            "View orders",                  //4
            "Add order",                    //5
            "Exit"                          //6
    };
    public UpdateTransport() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        printForm(resp,new String[] {"Transport ID"},new String[] {"Transport ID"},forumOptions);
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req,resp);
        //int id = Integer.parseInt(req.getParameter("Transport ID"));
        if(isButtonPressed(req,"Place truck")){

        }
        else {
            if(isButtonPressed(req,"Place carrier")){

            }
            else {
                if(isButtonPressed(req,"Start transport")){

                }
                else{
                    if(isButtonPressed(req,"View orders")){

                    }
                    else {
                        if(isButtonPressed(req,"Add order")){

                        }
                        else{
                            if(isButtonPressed(req,"Exit")){

                            }
                            else {
                                setError("failure");
                            }

                        }
                    }

                }

            }
        }
    }
}
