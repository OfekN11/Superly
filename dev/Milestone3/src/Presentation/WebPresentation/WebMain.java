package Presentation.WebPresentation;

import Presentation.WebPresentation.Screens.ViewModels.HR.EmployeeServlet;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.Servlet;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WebMain {
    private static int PORT = 8080;

    public static final Map<Class<? extends Servlet>, String> servletToPath= Stream.of(
            /***
             * All screens in the system.
             * For each new screen add another entry and make sure it doesn't share a path with another screen
             */
            new AbstractMap.SimpleEntry<>(Login.class, "/"),
            new AbstractMap.SimpleEntry<>(EmployeeServlet.class, "/home")
    ).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            try {
                PORT = Integer.parseInt(args[0]);
                if (PORT <8000 || PORT > 9000) {
                    System.out.println("Please enter port between 8000 and 9000");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Illegal input for port");
                return;
            }
        }

        Server server = new Server(PORT);
        ServletContextHandler handler = new ServletContextHandler(server, "/");
        for (Class<? extends Servlet> servlet : servletToPath.keySet())
            handler.addServlet(servlet, servletToPath.get(servlet));
        server.start();
        server.join();
    }
}
