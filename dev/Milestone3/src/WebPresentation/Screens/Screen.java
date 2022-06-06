package WebPresentation.Screens;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class Screen extends HttpServlet {

    private final String greeting;
    private final String[] menuOptions;

    protected String error = null;

    protected Screen(String greeting, String[] menuOptions) {
        this.greeting = greeting;
        this.menuOptions = menuOptions;
    }

    protected void greet(PrintWriter out) {
        out.println(String.format("<h1>%s</h1>", greeting));
    }

    protected void printMenu(PrintWriter out){
        out.println("<form method=\"post\">\n");
        for (int i = 0 ; i < menuOptions.length; i++)
            out.println(String.format("<input type=\"submit\" name=\"%s\" value=\"%s\"><br><br>", i+1 , menuOptions[i]));
        out.println("</form>");
    }

    protected static int getIndexOfButtonPressed(HttpServletRequest req) throws ServletException, IOException {
        int i=0;
        while (i++ >= 0)
            if (req.getParameter(String.valueOf(i)) != null)
                return i;
        return -1;
    }

    protected void printError(PrintWriter out){
        out.println(String.format("<p style=\"color:red\">%s</p>\n",error));
    }

    protected static void refresh(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getHeader("referer"));
    }
}
