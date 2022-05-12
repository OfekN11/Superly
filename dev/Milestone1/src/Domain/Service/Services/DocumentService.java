package Domain.Service.Services;
import Domain.Business.Controllers.TruckController;
import Domain.Service.Objects.*;
import Domain.Business.Controllers.DocumentController;
import Domain.Service.ServiceDocumentFactory;
import Domain.Service.ServiceShiftFactory;

public class DocumentService {
    private final DocumentController controller = new DocumentController();
    private final ServiceDocumentFactory factory = new ServiceDocumentFactory();
    public Result getDestinationDocument(int destinationDocumentSN)
    {
        try {
            return Result.makeOk(factory.createServiceDocument(controller.getDestinationDocument(destinationDocumentSN)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result getTransportDocument(int transportDocumentSN)
    {
        try {
            return Result.makeOk(factory.createServiceDocument(controller.getTransportDocument(transportDocumentSN)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }
}
