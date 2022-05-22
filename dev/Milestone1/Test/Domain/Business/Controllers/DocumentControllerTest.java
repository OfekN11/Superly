package Domain.Business.Controllers;

import Domain.Business.Objects.Document.DestinationDocument;
import Domain.Business.Objects.Document.TransportDocument;
import Domain.DAL.Controllers.TransportMudel.DestinationDocumentDAO;
import Domain.DAL.Controllers.TransportMudel.TransportDocumentDataMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DocumentControllerTest {
    static DocumentController controller;
    static DestinationDocumentDAO destinationDocumentDAO = new DestinationDocumentDAO();
    static TransportDocumentDataMapper transportDocument = new TransportDocumentDataMapper();
    private DestinationDocument dDoc;
    private TransportDocument tDoc;
    @Before
    public void setUp() throws Exception {
        controller = new DocumentController();
        dDoc  =  new DestinationDocument(303, 1, new ArrayList<>());
        tDoc  =  new TransportDocument(202, "18/01/2002", 123, "Chai");
    }

    @After
    public void tearDown() throws Exception {
        destinationDocumentDAO.remove(303);
        transportDocument.remove(202);
    }

    @Test
    public void getDestinationDocument() {
        try {
            dDoc = controller.getDestinationDocument(303);
            assertEquals(303, dDoc.getID());
        } catch (Exception e) {

        }
    }

    @Test
    public void getTransportDocument() {
        try {
            tDoc = controller.getTransportDocument(202);
            assertEquals(202, dDoc.getID());
        } catch (Exception e) {

        }
    }
}