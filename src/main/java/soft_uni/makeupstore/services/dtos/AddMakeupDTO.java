package soft_uni.makeupstore.services.dtos;

import soft_uni.makeupstore.entities.MakeupType;

import java.math.BigDecimal;

public class AddMakeupDTO {
    private String brand;
    private MakeupType makeupType;
    private String imageURL;
    private int quantity;
    private BigDecimal price;
    private String description;

    public AddMakeupDTO() {
    }

    public AddMakeupDTO(String brand, String makeupType ,String imageURL, int quantity, BigDecimal price, String description) {
        this.brand = brand;
        this.makeupType = MakeupType.valueOf(makeupType.toUpperCase());
        this.imageURL = imageURL;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }

    public MakeupType getMakeupType() {
        return makeupType;
    }

    public void setMakeupType(MakeupType makeupType) {
        this.makeupType = makeupType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
