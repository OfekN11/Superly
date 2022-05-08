package Backend.ServiceLayer;

import Backend.BusinessLayer.Controllers.DocumentController;

public class DocumentService {
    private DocumentController controller;

    public DocumentService() {
        controller = new DocumentController();
    }

    public Result getDestinationDocument(int destinationDocumentSN)
    {
        try {
            return Result.makeOk(controller.getDestinationDocument(destinationDocumentSN));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result getTransportDocument(int transportDocumentSN)
    {
        try {
            return Result.makeOk(controller.getTransportDocument(transportDocumentSN));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }
}
