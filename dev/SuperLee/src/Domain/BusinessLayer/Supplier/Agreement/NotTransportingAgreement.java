package Domain.BusinessLayer.Supplier.Agreement;

public class NotTransportingAgreement extends Agreement {

    public NotTransportingAgreement(){
        super();
    }

    @Override
    public boolean isTransporting() {
        return false;
    }

    @Override
    public int daysToDelivery() {
        return -1; // means not delivering
    }
}
