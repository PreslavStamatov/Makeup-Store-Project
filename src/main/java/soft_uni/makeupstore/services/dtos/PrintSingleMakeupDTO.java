package soft_uni.makeupstore.services.dtos;

import soft_uni.makeupstore.entities.MakeupType;

import java.math.BigDecimal;

public class PrintSingleMakeupDTO {
    private String brand;
    private MakeupType makeupType;
    private BigDecimal price;

    public PrintSingleMakeupDTO(String brand, MakeupType makeupType, BigDecimal price) {
        this.brand = brand;
        this.makeupType = makeupType;
        this.price = price;
    }

    public PrintSingleMakeupDTO() {
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setMakeupType(MakeupType makeupType) {
        this.makeupType = makeupType;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public MakeupType getMakeupType() {
        return makeupType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("Brand - %s%nType - %s%nPrice - %.2f",brand, makeupType, price);
    }
}
