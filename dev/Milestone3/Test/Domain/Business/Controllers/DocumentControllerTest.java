package Domain.Business.Controllers;

import Domain.Business.Controllers.Transport.DocumentController;
import Domain.Business.Objects.Document.DestinationDocument;
import Domain.Business.Objects.Document.TransportDocument;
import Domain.DAL.Abstract.DAO;
import Domain.DAL.Controllers.TransportMudel.DestinationDocumentDAO;
import Domain.DAL.Controllers.TransportMudel.TransportDocumentDataMapper;
import InventoryTests.CategoryTests;
import junit.framework.TestCase;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentControllerTest {
    static DocumentController controller;
    static DestinationDocumentDAO destinationDocumentDAO = new DestinationDocumentDAO();
    static TransportDocumentDataMapper transportDocument = new TransportDocumentDataMapper();
    private DestinationDocument dDoc;
    private TransportDocument tDoc;

    @BeforeAll
    public static synchronized void setData() {
        DAO.setDBForTests(DocumentControllerTest.class);
    }

    @AfterAll
    public static void removeData() {
        DAO.deleteTestDB(DocumentControllerTest.class);
    }

    @BeforeEach
    public void setUp() throws Exception {
        controller = new DocumentController();
        dDoc  =  new DestinationDocument(303, 1, new ArrayList<>());
        tDoc  =  new TransportDocument(202, "18/01/2002", 123, "Chai");

    }

    @AfterEach
    public void tearDown() throws Exception {
        destinationDocumentDAO.remove(303);
        transportDocument.remove(202);

    }
    @Test
    public void testUploadDestinationDocument() {
        try {
            controller.uploadDestinationDocument(dDoc);
            dDoc = controller.getDestinationDocument(303);
            assertEquals(303, dDoc.getID());
        } catch (Exception e) {

        }
    }
    @Test
    public void testGetDestinationDocument() {
        try {
            dDoc = controller.getDestinationDocument(303);
            assertEquals(303, dDoc.getID());
        } catch (Exception e) {

        }

    }
    @Test
    public void testUploadTransportDocument() {
        try {
            controller.uploadTransportDocument(tDoc);
            tDoc = controller.getTransportDocument(202);
            assertEquals(202, tDoc.getTransportID());
        } catch (Exception e) {

        }

    }
    @Test
    public void testGetTransportDocument() {
        try {
            tDoc = controller.getTransportDocument(202);
            assertEquals(202, tDoc.getTransportID());
        } catch (Exception e) {

        }

    }
}