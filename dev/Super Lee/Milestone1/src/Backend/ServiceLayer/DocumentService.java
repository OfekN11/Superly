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
            //controller.getDestinationDocument(destinationDocumentSN);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result getTransportDocument(int transportDocumentSN)
    {
        //TODO: implement
        return null;
    }
}
