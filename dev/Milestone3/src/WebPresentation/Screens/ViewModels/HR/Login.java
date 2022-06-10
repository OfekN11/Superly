package WebPresentation.Screens.ViewModels.HR;

import WebPresentation.Screens.Models.HR.Employee;
import WebPresentation.Screens.Models.HR.EmployeeFactory;
import WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Login extends Screen {

    private static final String greet = "Login";
    private static Map<String, Employee> loggedUser = new HashMap<>();

    private final EmployeeFactory factory = new EmployeeFactory();

    public Login() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isLoggedIn(req)) {
            redirect(resp, EmployeeServlet.class);
        }
        header(resp);
        greet(resp);
        printForm(resp, new String[]{"ID"}, new String[]{"Employee ID"}, new String[]{"Sign in!"});
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        if (isButtonPressed(req, "Sign in!")){
            String id = req.getParameter("ID");
            try {
                Employee emp = factory.createEmployee(controller.getEmployee(id));
                String hash = hash(id);
                loggedUser.put(hash, emp);
                Cookie c = new Cookie("superly_user", hash);
                c.setMaxAge((int)TimeUnit.MINUTES.toSeconds(2));
                resp.addCookie(c);
                redirect(resp, EmployeeServlet.class);
            } catch (Exception e) {
                setError(e.getMessage());
                refresh(req, resp);
            }
        }
    }

    public static boolean isLoggedIn(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
            for (Cookie c : cookies)
                if (c.getName().equals("superly_user") && loggedUser.containsKey(c.getValue()))
                    return true;
        return false;
    }

    public static Employee getLoggedUser(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        for (Cookie c : cookies)
            if (c.getName().equals("superly_user"))
                return loggedUser.get(c.getValue());
        return null;
    }

    public static void logout(HttpServletRequest req, HttpServletResponse resp){
        Cookie[] cookies = req.getCookies();
        for (Cookie c : cookies)
            if (c.getName().equals("superly_user")) {
                loggedUser.remove(c.getValue());
                c.setMaxAge(0);
                resp.addCookie(c);
            }
    }

    private static String hash(String toHash) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                toHash.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
