package Presentation.WebPresentation.Screens.ViewModels.Transport.Transport;

import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.Transport.TransportMainMenu;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateTransport extends Screen {
    private static final String greet = "Transport Management Menu";
    public CreateTransport() {
        super(greet);
    }
}
