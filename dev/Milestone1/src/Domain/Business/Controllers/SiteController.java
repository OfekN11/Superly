package Backend.BusinessLayer.Controllers;

import Backend.BusinessLayer.Objects.Destination;
import Backend.BusinessLayer.Objects.Driver;
import Backend.BusinessLayer.Objects.Site;
import Backend.BusinessLayer.Objects.Source;
import Globals.Enums.LicenseTypes;
import sun.security.krb5.internal.crypto.Des;

import java.util.HashMap;

public class SiteController {
    //TODO: visitor pattren
    private HashMap<Integer, Source> sources;
    private HashMap<Integer, Destination> destinations;
    private static SiteController instance = null;
    private SiteController() {
        sources = new HashMap<>();
        destinations = new HashMap<>();
    }

    public static SiteController getInstance(){
        if (instance == null) {
            instance = new SiteController();
        }
        return instance;
    }

    public Source getSource(int id) throws Exception {
        if(sources.containsKey(id))
        {
            return sources.get(id);
        }
        throw new Exception("The source doesn't exist!");
    }

    public Destination getDestination(int id) throws Exception {
        if(destinations.containsKey(id))
        {
            return destinations.get(id);
        }
        throw new Exception("The destination doesn't exist!");
    }

}
