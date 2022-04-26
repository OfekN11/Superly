package Backend.BusinessLayer.Controllers;

import Backend.BusinessLayer.Objects.DestinationDocument;
import Backend.BusinessLayer.Objects.Document;
import Backend.BusinessLayer.Objects.TransportDocument;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DocumentControllerTest {
    private DocumentController controller;
    private DestinationDocument dDoc;
    private TransportDocument tDoc;
    @Before
    public void setUp() throws Exception {
        controller = new DocumentController();
        dDoc  =  new DestinationDocument(1, new ArrayList<>());
        tDoc  =  new TransportDocument();
    }

    @Test
    public void getDestinationDocument() throws Exception {
        controller.getDestinationDocuments().put(1, dDoc);
        assertEquals(dDoc, controller.getDestinationDocument(1));
    }

    @Test
    public void getTransportDocument() throws Exception {
        controller.getTransportDocuments().put(0, tDoc);
        assertEquals(tDoc, controller.getTransportDocument(0));
    }
}