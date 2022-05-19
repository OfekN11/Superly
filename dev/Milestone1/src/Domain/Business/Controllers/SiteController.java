package Domain.Business.Controllers;

import Domain.Business.Objects.Site.Address;
import Domain.Business.Objects.Site.Destination;
import Domain.Business.Objects.Site.Source;
import Globals.Enums.ShippingAreas;

import java.util.HashMap;
//TODO not finished methods (GET) for each site
public class SiteController {
    private HashMap<Integer, Source> sources;
    private HashMap<Integer, Destination> destinations;
    public SiteController() {
        sources = new HashMap<>();
        destinations = new HashMap<>();
        //TODO: In Milestone 3 connect the data to DB
        sources.put(1, new Source(new Address(ShippingAreas.North, "Tiberia Shlomo Hamelech 136"), "Shalom", "050"));
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
