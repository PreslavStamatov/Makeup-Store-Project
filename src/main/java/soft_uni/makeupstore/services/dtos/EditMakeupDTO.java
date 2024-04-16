package soft_uni.makeupstore.services.dtos;

import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public class EditMakeupDTO {

    @Min(value = 1, message = "Id must be a positive number")
    private int id;

    @Min(value = 1, message = "Price must be a positive number")
    private BigDecimal price;
    @Min(value = 1, message = "Quantity must be a positive number")
    private int quantity;

    public EditMakeupDTO(int id, BigDecimal price, int quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    public EditMakeupDTO() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
