package Domain.BusinessLayer.Supplier.Agreement;

import Domain.PersistenceLayer.Controllers.AgreementController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ByOrderAgreement extends Agreement{

    private int deliveryDays;

    public ByOrderAgreement( int _delivery){
        super();
        deliveryDays = _delivery;
    }

    @Override
    public boolean isTransporting() {
        return true;
    }

    @Override
    public int daysToDelivery() {
        return deliveryDays;
    }

    @Override
    public List<Integer> getDays() {
        List<Integer> result = new ArrayList<>();
        result.add(deliveryDays);
        return result;
    }

    public int getDeliveryDays(){
        return deliveryDays;
    }

    public void setDeliveryDays(int days, int supplierId, AgreementController agreementController) throws SQLException {
        List<Integer> daysList = new ArrayList<>();
        daysList.add(days);
        agreementController.setDeliveryDays(supplierId, daysList);
        deliveryDays = days;
    }
}
