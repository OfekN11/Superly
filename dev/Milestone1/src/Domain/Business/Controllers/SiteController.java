package Domain.Business.Controllers;

import Domain.Business.Objects.Site.Address;
import Domain.Business.Objects.Site.Destination;
import Domain.Business.Objects.Site.Source;
import Domain.DAL.Controllers.TransportMudel.DestinationsDAO;
import Domain.DAL.Controllers.TransportMudel.SourcesDAO;
import Globals.Enums.ShippingAreas;

import java.util.HashMap;
//TODO not finished methods (GET) for each site
public class SiteController {
    private final SourcesDAO sourcesDataMapper = new SourcesDAO();
    private final DestinationsDAO destinationsDataMapper = new DestinationsDAO();
    public SiteController() {
        //TODO: In Milestone 3 connect the data to DB
        //sources.put(1, new Source(new Address(ShippingAreas.North, "Tiberia Shlomo Hamelech 136"), "Shalom", "050"));
    }

    public Source getSource(int id) throws Exception {
        Source src = sourcesDataMapper.get(id);
        if(src == null){
            throw new Exception("Source not found");
        }
        return src;
    }

    public Destination getDestination(int id) throws Exception {
        Destination dst = destinationsDataMapper.get(id);
        if(dst == null){
            throw new Exception("Source not found");
        }
        return dst;
    }

}
