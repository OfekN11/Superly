package SuperLee.BusinessLayer.Agreement;

import SuperLee.BusinessLayer.Item;

import java.util.List;

public class NotTransportingAgreement extends Agreement {

    public NotTransportingAgreement(List<Item> _items){
        super(_items);
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
