package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Supplier.AgreementItem;
import Domain.BusinessLayer.Supplier.Agreement.Agreement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AgreementController {


    private final RoutineDAO routineDAO;
    private final ByOrderDAO byOrderDAO;
    private final SelfTransportingDAO selfTransportDAO;
    private final AgreementItemDAO agreementItemDAO;


    private final int ROUTINE  = 1;
    private final int BY_ORDER  = 2;
    private final int NOT_TRANSPORTING  = 3;


    public AgreementController(){
        routineDAO = new RoutineDAO();
        byOrderDAO = new ByOrderDAO();
        selfTransportDAO = new SelfTransportingDAO();
        agreementItemDAO = new AgreementItemDAO();

    }




    public void updateAgreementDays(int supplierid, List<Integer> days, int agreementType) {
        switch(agreementType){
            case ROUTINE :
                routineDAO.addDaysOfDelivery(supplierid, days);
                break;
            case BY_ORDER :
                byOrderDAO.addTime(supplierid, days);
                break;
            case NOT_TRANSPORTING :
                selfTransportDAO.updateSupplier(supplierid);
                break;
        }
    }


    public void removeSupplier(int id) throws SQLException {
        routineDAO.remove(id);
        byOrderDAO.remove(id);
        selfTransportDAO.remove(id);
        agreementItemDAO.removeSupplier();
    }


    public void addAgreementItems(List<AgreementItem> items, int supplierId) throws Exception {
        agreementItemDAO.addItemstoAgreement(supplierId, items);
    }

    public void updateBulkPriceForItem(int itemID, Map<Integer, Integer> newBulkPrices) throws SQLException {
        agreementItemDAO.updateBulkPrice(itemID, newBulkPrices);
    }

    public AgreementItemDAO getAgreementItemDAO() {
        return  agreementItemDAO;
    }

    public void setDaysOfDelivery(int supplierId, List<Integer> list) throws SQLException {
        routineDAO.remove(supplierId);
        routineDAO.addDaysOfDelivery(supplierId, list);
    }

    public void setDeliveryDays(int supplierId, List<Integer> list) throws SQLException {
        byOrderDAO.remove(supplierId);
        byOrderDAO.addTime(supplierId, list);
    }

    public Agreement loadAgreementAndItems(int supplierId, int agreementType) throws SQLException {
        Agreement agreement;
        /*
        ROUTINE  = 1;
    private final int BY_ORDER  = 2;
    private final int NOT_TRANSPORTING
         */
        switch(agreementType){
            case ROUTINE :
                agreement = routineDAO.loadAgreement(supplierId);
                break;
            case BY_ORDER :
                agreement = byOrderDAO.loadAgreement(supplierId);
                break;
            case NOT_TRANSPORTING :
                agreement = selfTransportDAO.loadAgreement(supplierId);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + agreementType);
        }

        Map<Integer, AgreementItem> items = agreementItemDAO.getAllAgreementItemFromSupplier(supplierId);
        agreement.addAgreementItems(items);
        return agreement;
    }

    public void setLastOrderId(int supplierId, int orderId) throws SQLException {
        routineDAO.setLastOrderId(supplierId, orderId);

    }
}
