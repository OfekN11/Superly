package WebPresentation.Screens;

import Globals.Enums.ShiftTypes;
import WebPresentation.WebMain;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class Screen extends HttpServlet {

    /***
     * greeting message, used in greet method
     */
    private final String greeting;

    private String error = null;

    public static BackendController controller = new BackendController();

    public Screen(String greeting) {
        this.greeting = greeting;
    }

    /***
     * Prints greeting as a heading
     * @param resp the response to print to
     * @throws IOException
     */
    protected void greet(HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println(String.format("<h1>%s</h1>", greeting));
    }

    protected void setError(String error) {
        this.error = error;
    }

    protected String getError() {
        return error;
    }

    protected void cleanError(){
        error = null;
    }

    protected boolean isError(){
        return error != null;
    }

    /***
     * Prints error as red text if screen has an error logged
     * @param resp the response to print to
     * @throws IOException
     */
    protected void handleError(HttpServletResponse resp) throws IOException {
        if (!isError())
            return;
        PrintWriter out = resp.getWriter();
        out.println(String.format("<p style=\"color:red\">%s</p><br><br>", getError()));
        cleanError();
    }

    /***
     * prints a form of submit buttons.
     * buttons names are the index of their value in menuOptions
     * @param resp the response to print to
     * @param menuOptions the values of the buttons in the menu
     * @throws IOException
     */
    public static void printMenu(HttpServletResponse resp, String[] menuOptions) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<form method=\"post\">\n");
        for (int i = 0; i < menuOptions.length; i++)
            out.println(String.format("<input type=\"submit\" name=\"%s\" value=\"%s\"><br><br>", i, menuOptions[i]));
        out.println("</form>");
    }

    /***
     * prints a form to fill.
     * buttons names are their values.
     * placeholders' length is expected to match textBoxes' length
     * @param resp the response to print to
     * @param textBoxes the names of the text boxes
     * @param placeholders the hints for the text boxes
     * @param buttons buttons of the form
     * @throws IOException
     */
    protected static void printForm(HttpServletResponse resp, String[] textBoxes, String[] placeholders, String[] buttons) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<form method=\"post\">\n");
        for (int i = 0; i < textBoxes.length; i++)
            out.println(String.format("<input type=\"text\" name=\"%s\" placeholder=\"%s\"><br><br>", textBoxes[i], placeholders[i]));
        for (String button : buttons)
            out.println(String.format("<input type=\"submit\" name=\"%s\" value=\"%s\"><br><br>", button, button));
        out.println("</form>");
    }

    /***
     * checks if any button whose name is a positive integer was pressed.
     * @param req the sent request
     * @return the name of the pressed button if pressed, else returns -1
     * @throws ServletException
     * @throws IOException
     */
    protected static int getIndexOfButtonPressed(HttpServletRequest req) throws ServletException, IOException {
        int i = 0;
        while (i++ >= 0)
            if (req.getParameter(String.valueOf(i)) != null)
                return i;
        return -1;
    }

    /***
     * checks if a button of name pressedButton was pressed
     * @param req the sent request
     * @param pressedButton the name of the button
     * @return true if the button with the given name was pressed, else returns false
     */
    protected static boolean isButtonPressed(HttpServletRequest req, String pressedButton) {
        return req.getParameter(pressedButton) != null;
    }

    /***
     * sends the response to the page from the request
     * @param req the sent request
     * @param resp the response to return
     * @throws IOException
     */
    protected static void refresh(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getHeader("referer"));
    }

    /***
     * redirects the response to the path of the given servlet
     * assumes the given servlet is registered at WebMain.servletToPath
     * @param resp the response to redirect
     * @param redirectTo the servlet we want to serve
     * @throws IOException
     */
    protected static void redirect(HttpServletResponse resp, Class<? extends Servlet> redirectTo) throws IOException {
        resp.sendRedirect(WebMain.servletToPath.get(redirectTo));
    }
}