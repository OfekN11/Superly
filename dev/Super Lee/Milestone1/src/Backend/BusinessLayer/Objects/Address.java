package Backend.BusinessLayer.Objects;

import Backend.Globals.Enums.ShippingAreas;

public class Address {
    private ShippingAreas shippingArea;
    private String exactAddress;

    public Address(ShippingAreas shippingArea, String exactAddress) {
        this.shippingArea = shippingArea;
        this.exactAddress = exactAddress;
    }

    public ShippingAreas getShippingArea() {
        return shippingArea;
    }

    public void setShippingArea(ShippingAreas shippingArea) {
        this.shippingArea = shippingArea;
    }

    public String getExactAddress() {
        return exactAddress;
    }

    public void setExactAddress(String exactAddress) {
        this.exactAddress = exactAddress;
    }
}
