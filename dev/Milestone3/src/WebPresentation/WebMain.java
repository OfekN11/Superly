package WebPresentation;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import WebPresentation.Screens.*;

public class WebMain {
    private static int PORT = 8080;

    public static void main(String[] args) throws Exception {
        Server server = new Server(PORT);
        ServletContextHandler handler = new ServletContextHandler(server, "/");
        handler.addServlet(Login.class, "/");
        server.start();
        server.join();
    }
}
